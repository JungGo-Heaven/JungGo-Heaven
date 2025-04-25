package com.example.junggoheaven.domain.product.service.component;

import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.exception.ProductNotFoundException;
import com.example.junggoheaven.domain.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductFinder {

	private final ProductRepository productRepository;

	// Opt 제거
	public Page<Product> findAllProduct(Pageable pageable) {
		return productRepository.findAll(pageable);
	}


	public Product findProductById(Long productId) {
		return productRepository.findByIdAndStatusIsNotDeleted(productId)
			.orElseThrow(ProductNotFoundException::new);
	}

	public List<Product> findLikeTop5Products(List<Long> ids){
		return productRepository.findByIdIn(ids);
	}

	public Page<Product> findNearbyProductsByLocation(Point location, double radius, Pageable pageable) {
		return productRepository.findNearbyProductsByLocation(location, radius, pageable);
	}

}
