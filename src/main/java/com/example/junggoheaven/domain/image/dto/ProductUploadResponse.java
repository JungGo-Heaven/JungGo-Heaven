package com.example.junggoheaven.domain.image.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ProductUploadResponse {

    private final List<String> uploadUrls;

    public static ProductUploadResponse of(List<String> uploadUrls) {
        return new ProductUploadResponse(uploadUrls);
    }
}
