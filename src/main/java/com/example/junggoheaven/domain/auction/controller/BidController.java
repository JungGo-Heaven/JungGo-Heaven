package com.example.junggoheaven.domain.auction.controller;

import com.example.junggoheaven.domain.auction.service.bidService.BidService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BidController {
    private final BidService bidService;
}
