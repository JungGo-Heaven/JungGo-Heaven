package com.example.junggoheaven.domain.product.dto.response;

import com.example.junggoheaven.domain.image.entity.ProductImage;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.enums.SellStatus;
import com.example.junggoheaven.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ProductResponseDto {

	private final Long id;

	private final User user;

	private final String name;

	private final String information;

	private final Long price;

	private final ProductImage productImage;

	private final SellStatus sellStatus;

	private final LocalDateTime deletedAt;

	private final LocalDateTime pullAt;


	// Product 를 받는 생성자
	public ProductResponseDto(Product product){
		this.id = product.getId();
		this.user = product.getUser(); // Refactor 요구
		this.name = product.getName();
		this.information = product.getInformation();
		this.price = product.getPrice();
		this.sellStatus = product.getSellStatus();
		this.deletedAt = product.getDeletedAt(); // 반드시 null
		this.pullAt = product.getPullAt();
		this.productImage = product.getProductImage();
	}


	// 페이지네이션 다건조회를 toDto 메서드
	public static ProductResponseDto toDto(Product product) {
		return new ProductResponseDto(
			product.getId(),
			product.getUser(),
			product.getName(),
			product.getInformation(),
			product.getPrice(),
			product.getProductImage(),
			product.getSellStatus(),
			product.getDeletedAt(),
			product.getPullAt()
		);
	}


}
