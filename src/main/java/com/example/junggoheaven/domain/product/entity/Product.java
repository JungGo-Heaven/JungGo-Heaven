package com.example.junggoheaven.domain.product.entity;

import com.example.junggoheaven.domain.image.entity.ProductImage;
import com.example.junggoheaven.domain.product.dto.request.ProductRequestDto;
import com.example.junggoheaven.domain.product.dto.request.ProductSellStatusRequestDto;
import com.example.junggoheaven.domain.product.enums.ProductStatus;
import com.example.junggoheaven.domain.product.enums.SellStatus;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;


@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE product SET status = 'DELETED' WHERE id = ?")
@Filter(name = "deletedFilter", condition = "status <> 'DELETED'")
public class Product extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "users_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "product_images_id")
	private ProductImage productImage;

	@Column(nullable = false)
	private String name;

	private String information;

	@Column(nullable = false)
	private Long price;

	@Enumerated(EnumType.STRING)
	private SellStatus sellStatus;

	@Column
	@ColumnDefault("null")
	private LocalDateTime deletedAt;

	@Enumerated(EnumType.STRING)
	private ProductStatus status;

	@CreatedDate // 최초 생성 시각 스탬프
	private LocalDateTime pullAt;

	@Column
	private Long productCategory;

	// productCategory 추가
	public Product(
		User user,
		String name,
		String information,
		Long price,
		ProductImage productImage,
		Long productCategory

	) {
		this.user = user;
		this.name = name;
		this.information = information;
		this.price = price;
		this.sellStatus = SellStatus.ONSALE;
		this.productImage = productImage;
		this.status = ProductStatus.NORMAL;
		this.productCategory = productCategory;
	}


	public Product(
		User user,
		String name,
		String information,
		Long price,
		ProductImage productImage
	) {
		this.user = user;
		this.name = name;
		this.information = information;
		this.price = price;
		this.sellStatus = SellStatus.ONSALE;
		this.productImage = productImage;
		//this.deletedAt = null;
		this.status = ProductStatus.NORMAL;
	}

	// 테스트 꼬임 방지용 생성자
	public Product(
		User user,
		String name,
		String information,
		Long price
	) {
		this.user = user;
		this.name = name;
		this.information = information;
		this.price = price;
		this.sellStatus = SellStatus.ONSALE;
		//this.deletedAt = null;
	}


	public void softDeleteSetDateTime() {
		this.deletedAt = LocalDateTime.now();
	}

	public void updateProduct(ProductRequestDto productRequestDto) {
		this.name = productRequestDto.getName();
		this.information = productRequestDto.getInformation();
		this.price = productRequestDto.getPrice();
	}

	public void updateSellStatus(SellStatus sellStatus) {
		this.sellStatus = sellStatus;
	}

	public void pullProduct() {
		this.pullAt = LocalDateTime.now();
	}


}
