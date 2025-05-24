package com.remind.memorylog.domain.diary.service;

import com.remind.memorylog.domain.diary.entity.Diary;
import com.remind.memorylog.domain.diary.exception.ImageUploadFailedException;
import com.remind.memorylog.domain.diary.repository.DiaryRepository;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordRequest;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.exception.MemberNotFoundException;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordResponse;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    @Override
    public DiaryRecordResponse recordMemory(DiaryRecordRequest diaryRequest, MultipartFile image) throws IOException {

        // 회원 존재 확인
        Member member = memberRepository.findById(diaryRequest.getMemberId())
                .orElseThrow(MemberNotFoundException::new);

        String imageUrl = null;
        try {
            if(image != null && !image.isEmpty()) {
                imageUrl = s3Uploader.upload(image, "diary");
            }
        }catch (IOException e) {
            log.warn("이미지 업로드 실패: {}", e.getMessage());
            // 여기서 에러 throw 하기 (ImageUploadFailed 이런 식의 에러)
            throw new ImageUploadFailedException();
        }

        // Diary Entity 생성
        Diary diary = Diary.builder()
                .member(member)
                .imageUrl(imageUrl)
                .content(diaryRequest.getContent())
                .song(diaryRequest.getSong())
                .build();

        // 기억기록 저장
        diaryRepository.save(diary);


        return new DiaryRecordResponse(
                diary.getDiaryId(),
                member.getMemberId(),
                diary.getContent(),
                diary.getSong(),
                diary.getImageUrl()
        );


    }

    @Transactional
    @Override
    public List<DiaryRecordResponse> getRecentDiaries(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);

        List<Diary> diaries = diaryRepository.findTop10ByMemberMemberIdOrderByCreatedAtDesc(memberId);

        List<DiaryRecordResponse> responses = new ArrayList<>();

        for (Diary diary : diaries) {
            DiaryRecordResponse response = new DiaryRecordResponse(
                    diary.getDiaryId(),
                    diary.getMember().getMemberId(),
                    diary.getContent(),
                    diary.getSong(),
                    diary.getImageUrl()
            );
            responses.add(response);
        }
        return responses;
    }
}
