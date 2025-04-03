package com.example.junggoheaven.domain.image.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProfileUploadResponse {

    private final String uploadUrl;

    public static ProfileUploadResponse of(String uploadUrl) {
        return new ProfileUploadResponse(uploadUrl);
    }
}
