package com.example.junggoheaven.domain.image.util;

import java.util.UUID;

public class FIleUtils {

    private static final String FILE_EXTENSION_SEPARATOR = ".";

    public static String buildUniqueFilename(String originalFilename) {
        // 확장자 추출 및 확장자 소문자로 변경
        int fileExtensionIndex = originalFilename.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFilename.substring(fileExtensionIndex).toLowerCase();
        // 순수 파일이름 추출(확장자 앞의 '.'까지 잘라내기)
        String filename = originalFilename.substring(0, fileExtensionIndex);
        // 현재 시간 문자열로 변환
        String now = String.valueOf(System.currentTimeMillis());

        return UUID.randomUUID() + "/" + now + "_" + filename + fileExtension;
    }
}
