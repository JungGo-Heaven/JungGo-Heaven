package com.example.junggoheaven.domain.image.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UploadResponse {

    private final String uploadUrl;

    public static UploadResponse of(String uploadUrl) {
        return new UploadResponse(uploadUrl);
    }
}
