package com.example.junggoheaven.domain.image.dto;

import com.example.junggoheaven.domain.image.enums.UploadType;
import com.example.junggoheaven.domain.image.exception.InvalidUploadTypeException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UploadContext {

    private final Long productId;
    private final Long chatRoomId;

    public Long getResourceId(UploadType uploadType) {
        return switch (uploadType) {
            case PRODUCTS -> productId;
            case CHAT_ROOMS -> chatRoomId;
            default -> throw new InvalidUploadTypeException();
        };
    }
}
