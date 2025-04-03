package com.example.junggoheaven.domain.product.service.component;


import com.example.junggoheaven.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductChecker {

	private final ProductRepository productRepository;




}
