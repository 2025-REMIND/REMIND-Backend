
package com.remind.memorylog.domain.diary.web.controller;

import com.remind.memorylog.domain.diary.service.DiaryService;
import com.remind.memorylog.domain.diary.service.S3Uploader;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordRequest;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordResponse;
import com.remind.memorylog.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
        import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam Long memberId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String song,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        // DiaryRequest 객체 생성해서 넘기기
        DiaryRecordRequest diaryRequest = new DiaryRecordRequest(memberId, content, song);

        String imageUrl = null;
        try {
            if (image != null && !image.isEmpty()) {
                imageUrl = s3Uploader.upload(image, "diary");
            }
        } catch (IOException e) {
            log.warn("이미지 업로드 실패: {}", e.getMessage());
        }

        DiaryRecordResponse diaryResponse = diaryService.recordMemory(diaryRequest, imageUrl);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(diaryResponse));
    }
}
