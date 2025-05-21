package com.remind.memorylog.domain.diary.web.controller;

import com.remind.memorylog.domain.diary.service.DiaryService;
import com.remind.memorylog.domain.diary.service.S3Uploader;
import com.remind.memorylog.domain.diary.web.dto.DiaryRequest;
import com.remind.memorylog.domain.diary.web.dto.DiaryResponse;
import com.remind.memorylog.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.remind.memorylog.domain.member.exception.UserNotFoundException;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class DiaryController {
    // 의존성 부여
    private final DiaryService diaryService;
    private final S3Uploader s3Uploader;

    // 기억 기록
    @PostMapping(value = "/diary", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<?>> recordDiary(
            @RequestParam(required = false) Long memberId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String song,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        if (memberId == null) {
            throw new UserNotFoundException(); // 클라이언트가 memerId를 안넘겼을 때 에러처리
        }

        // DiaryRequest 객체 생성해서 넘기기
        DiaryRequest diaryRequest = new DiaryRequest(memberId, content, song);

        String imageUrl = null;
        try {
            if (image != null && !image.isEmpty()) {
                imageUrl = s3Uploader.upload(image, "diary");
            }
        } catch (IOException e) {
            log.warn("이미지 업로드 실패: {}", e.getMessage());
        }


        DiaryResponse diaryResponse = diaryService.record(diaryRequest, imageUrl);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(diaryResponse));
    }
}

