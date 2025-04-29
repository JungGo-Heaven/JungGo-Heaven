package com.example.junggoheaven.domain.product.dto.response;

import com.example.junggoheaven.domain.image.entity.ProductImage;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.enums.SellStatus;
import com.example.junggoheaven.domain.user.dto.user.UserInfoResponseDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ProductResponseDto {

	private final Long id;

	private final UserInfoResponseDto user;

	private final String name;

	private final String information;

	private final Long price;

	private final ProductImage productImage;

	private final SellStatus sellStatus;

	private final LocalDateTime deletedAt;

	private final LocalDateTime pullAt;

	private final String address;

	private final Double longitude;

	private final Double latitude;


	// Product 를 받는 생성자
	public ProductResponseDto(Product product) {
		this.id = product.getId();
		this.user = UserInfoResponseDto.fromProduct(product); // Refactor 완료
		this.name = product.getName();
		this.information = product.getInformation();
		this.price = product.getPrice();
		this.sellStatus = product.getSellStatus();
		this.deletedAt = product.getDeletedAt(); // 반드시 null
		this.pullAt = product.getPullAt();
		this.productImage = product.getProductImage();
		this.address = product.getAddress();
		this.longitude = product.getLongitude();;
		this.latitude = product.getLatitude();
	}


	// 페이지네이션 다건조회를 toDto 메서드
	public static ProductResponseDto toDto(Product product) {
		return new ProductResponseDto(
			product.getId(),
			UserInfoResponseDto.fromProduct(product),
			product.getName(),
			product.getInformation(),
			product.getPrice(),
			product.getProductImage(),
			product.getSellStatus(),
			product.getDeletedAt(),
			product.getPullAt(),
				product.getAddress(),
				product.getLongitude(),
				product.getLatitude()
		);
	}


}
