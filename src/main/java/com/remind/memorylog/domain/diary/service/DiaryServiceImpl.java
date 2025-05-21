package com.remind.memorylog.domain.diary.service;

import com.remind.memorylog.domain.diary.entity.Diary;
import com.remind.memorylog.domain.diary.repository.DiaryRepository;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordRequest;
import com.remind.memorylog.domain.member.entity.Member;
import com.remind.memorylog.domain.member.exception.UserNotFoundException;
import com.remind.memorylog.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordResponse;


@Service
@RequiredArgsConstructor
public class DiaryServiceImpl implements DiaryService {
    private final MemberRepository memberRepository;
    private final DiaryRepository diaryRepository;

    @Transactional
    @Override
    public DiaryRecordResponse recordMemory(DiaryRecordRequest diaryRequest, String imageUrl) {

        // 회원 존재 확인
        Member member = memberRepository.findById(diaryRequest.getMemberId())
                .orElseThrow(UserNotFoundException::new);

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
}
