
package com.remind.memorylog.domain.diary.web.controller;

import com.remind.memorylog.domain.diary.service.DiaryService;
import com.remind.memorylog.domain.diary.service.S3Uploader;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordRequest;
import com.remind.memorylog.domain.diary.web.dto.DiaryRecordResponse;
import com.remind.memorylog.global.response.SuccessResponse;
import jakarta.validation.Valid;
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
@RequestMapping("/diary") // diary 관련 컨트롤러는 묶어주기
@RequiredArgsConstructor
public class DiaryController {
    // 의존성 부여
    private final DiaryService diaryService;

    // 기억 기록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<?>> recordDiary(
            @RequestPart("data") @Valid DiaryRecordRequest diaryRecordRequest,
            @RequestPart(value = "image") MultipartFile image) throws IOException {


        // 아래 로직은 서비스 코드에서 처리하는 게 적절
//        String imageUrl = null;
//        try {
//            if (image != null && !image.isEmpty()) {
//                imageUrl = s3Uploader.upload(image, "diary");
//            }
//        } catch (IOException e) {
//            log.warn("이미지 업로드 실패: {}", e.getMessage());
//        }

        DiaryRecordResponse diaryResponse = diaryService.recordMemory(diaryRecordRequest, image);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(diaryResponse));
    }
}
