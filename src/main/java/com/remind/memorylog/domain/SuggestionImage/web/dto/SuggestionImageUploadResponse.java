package com.remind.memorylog.domain.SuggestionImage.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SuggestionImageUploadResponse {
    private List<String> imageUrls;
}
