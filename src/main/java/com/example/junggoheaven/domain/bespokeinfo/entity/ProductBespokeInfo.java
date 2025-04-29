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
	private int productCategory;

	@Column(nullable = false)
	private int gender;

	@Column(nullable = false)
	private int location;

	@Column(nullable = false)
	private int ageGroup;

}
