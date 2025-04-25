package com.example.junggoheaven.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationVerificationRequest {
    private double currentLongitude;
    private double currentLatitude;
    private Long userId;

    public static LocationVerificationRequest of(double longitude, double latitude, Long userId) {
        return new LocationVerificationRequest(longitude, latitude, userId);
    }
}