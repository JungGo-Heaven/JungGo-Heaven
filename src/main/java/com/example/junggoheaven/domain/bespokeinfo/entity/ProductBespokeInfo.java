package com.example.junggoheaven.domain.bespokeinfo.entity;

import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "product_bespoke_info")
@Getter
@NoArgsConstructor
public class ProductBespokeInfo extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false)
	private Long productCategory;

	@Column(nullable = false)
	private Long gender;

	@Column(nullable = false)
	private Long location;

	@Column(nullable = false)
	private Long ageGroup;

	private ProductBespokeInfo(UserBespokeInfo userBespokeInfo, Product product) {
		this.product =product;
		this.productCategory = product.getProductCategory();
		this.gender = userBespokeInfo.getGender();
		this.location = userBespokeInfo.getLocation();
		this.ageGroup = userBespokeInfo.getAgeGroup();
	}


	public static ProductBespokeInfo of(UserBespokeInfo userBespokeInfo, Product product) {
		return new ProductBespokeInfo(userBespokeInfo, product);
	}

}
