package com.example.junggoheaven.domain.location.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GeoCoordinate {

    private final Double longitude; // x
    private final Double latitude; // y
}
