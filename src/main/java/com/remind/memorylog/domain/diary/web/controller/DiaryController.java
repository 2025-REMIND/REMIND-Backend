
package com.remind.memorylog.domain.diary.web.controller;

import com.remind.memorylog.domain.diary.service.DiaryService;
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
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    // 의존성 부여
    private final DiaryService diaryService;

    // 기억 기록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<?>> recordDiary(@RequestPart("data") @Valid DiaryRecordRequest diaryRecordRequest, @RequestPart(value="image") MultipartFile image) throws IOException {


        DiaryRecordResponse diaryResponse = diaryService.recordMemory(diaryRecordRequest, image);
        return ResponseEntity.status(HttpStatus.OK)
                .body(SuccessResponse.ok(diaryResponse));
    }
}
