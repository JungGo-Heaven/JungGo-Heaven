package com.example.junggoheaven.domain.product.controller;

import com.example.junggoheaven.domain.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

}
