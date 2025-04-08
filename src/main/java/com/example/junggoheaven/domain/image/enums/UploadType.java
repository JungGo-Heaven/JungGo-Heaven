package com.example.junggoheaven.domain.image.enums;

import static com.example.junggoheaven.domain.user.enums.UserRole.ROLE_USER;

import com.example.junggoheaven.domain.image.exception.imageException.TypeMismatchException;
import com.example.junggoheaven.domain.user.enums.UserRole;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.function.Predicate;

@Getter
@RequiredArgsConstructor
public enum UploadType {

    PROFILES("profiles", role -> role == ROLE_USER),
    PRODUCTS("products", role -> role == ROLE_USER),
    CHAT_ROOMS("chat_rooms", role -> role == ROLE_USER);

    private final String type;

    // if 분기처리 제거할 수 있고, 폴더 타입에 따른 권한을 바로 나눌 수 있다.
    private final Predicate<UserRole> roleChecker;

    // 정의되어 있는 타입인지 체크
    public static UploadType from(String type) {
        for (UploadType uploadType : values()) {
            if (uploadType.type.equalsIgnoreCase(type)) {
                return uploadType;
            }
        }
        throw new TypeMismatchException();
    }

    // url의 일부로 생성
    public String getPrefix() {
        return this.type + "/";
    }

    // Predicate 사용 메서드
    public boolean isAllowedFor(UserRole role) {
        return roleChecker.test(role);
    }

}
