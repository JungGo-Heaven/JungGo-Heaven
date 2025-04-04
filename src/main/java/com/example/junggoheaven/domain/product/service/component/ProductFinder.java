package com.example.junggoheaven.domain.product.service.component;

import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.repository.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFinder {

	private final ProductRepository productRepository;


	public Page<Product> findAllProductOpt(Pageable pageable) {
		return productRepository.findByDeletedAtNull(pageable);
	}


	public Product findProductById(Long id) {
		return productRepository.findById(id)
			.orElseThrow();
	}


}
