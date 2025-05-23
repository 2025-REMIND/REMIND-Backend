package com.remind.memorylog.domain.SuggestionImage.web.controller;

import com.remind.memorylog.domain.SuggestionImage.service.SuggestionImageService;
import com.remind.memorylog.domain.SuggestionImage.web.dto.SuggestionImageUploadResponse;
import com.remind.memorylog.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion/{suggestionId}/image")
public class SuggestionImageController {

    private final SuggestionImageService suggestionImageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SuccessResponse<?>> uploadImages (@PathVariable Long suggestionId, @RequestPart("images") List<MultipartFile> images) throws IOException {

        SuggestionImageUploadResponse imageResponse = suggestionImageService.uploadImages(suggestionId, images);
        return ResponseEntity.status(HttpStatus.OK).body(SuccessResponse.ok(imageResponse));
    }
}
