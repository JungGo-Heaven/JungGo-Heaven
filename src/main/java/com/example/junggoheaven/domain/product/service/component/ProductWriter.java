package com.example.junggoheaven.domain.product.service.component;

import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductWriter {

	private final ProductRepository productRepository;

	public void saveProduct(Product product){
		productRepository.save(product);

	}


}
