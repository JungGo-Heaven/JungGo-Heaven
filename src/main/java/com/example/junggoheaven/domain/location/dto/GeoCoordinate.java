package com.example.junggoheaven.domain.location.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GeoCoordinate {

    private final Double longitude; // x값(경도)
    private final Double latitude; // y값(위도)
}
