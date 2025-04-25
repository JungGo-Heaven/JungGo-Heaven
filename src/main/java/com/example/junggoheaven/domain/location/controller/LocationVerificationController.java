package com.example.junggoheaven.domain.location.controller;

import com.example.junggoheaven.domain.location.dto.LocationVerificationRequest;
import com.example.junggoheaven.domain.location.exception.LocationVerificationRequiredException;
import com.example.junggoheaven.domain.location.service.LocationVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/location/gps")
public class LocationVerificationController {

    private final LocationVerificationService locationVerificationService;

    // '내 동네 인증하기'
    @PostMapping("/verifies")
    public ResponseEntity<String> verifyUserLocation(@RequestBody LocationVerificationRequest request) {
        try {
            // 내 동네 인증을 시도
            locationVerificationService.verifyLocation(request);
            return ResponseEntity.ok("내 동네 인증 성공");
        } catch (LocationVerificationRequiredException e) {
            // 내 동네 인증 실패 시
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("내 동네 인증이 필요합니다.");
        }
    }
}
