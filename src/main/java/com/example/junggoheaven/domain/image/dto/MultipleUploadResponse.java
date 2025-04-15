package com.example.junggoheaven.domain.image.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class MultipleUploadResponse {

    private final List<String> uploadUrls;

    public static MultipleUploadResponse of(List<String> uploadUrls) {
        return new MultipleUploadResponse(uploadUrls);
    }
}
