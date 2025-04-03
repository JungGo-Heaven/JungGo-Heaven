package com.example.junggoheaven.domain.image.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ChatRoomUploadResponse {

    private final List<String> uploadUrls;

    public static ChatRoomUploadResponse of(List<String> uploadUrls) {
        return new ChatRoomUploadResponse(uploadUrls);
    }
}
