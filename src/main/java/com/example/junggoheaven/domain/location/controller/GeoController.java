package com.example.junggoheaven.domain.location.controller;

import com.example.junggoheaven.domain.location.dto.GeoCoordinate;
import com.example.junggoheaven.domain.location.service.GeoService;
import com.example.junggoheaven.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v2/location", method = RequestMethod.GET)
public class GeoController {

    private final GeoService geoService;

    @GetMapping("/coordinates")
    public ResponseDto<GeoCoordinate> getGeoData(@RequestParam String address) {
        GeoCoordinate geoCoordinate = geoService.getGeoData(address);
        return ResponseDto.success(geoCoordinate);
    }
}
