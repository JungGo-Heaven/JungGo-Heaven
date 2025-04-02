package com.example.junggoheaven.domain.product.entity;

import com.example.junggoheaven.domain.product.enums.SellStatus;
import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	//@ManyToOne
	//@JoinColumn(name = "users_id")
	//private Users users;

	@Column(nullable = false)
	private String name;

	private String information;

	@Column(nullable = false)
	private Long price;

	@Enumerated(EnumType.STRING)
	//@ColumnDefault("ONSALE")
	private SellStatus sellStatus;

	@Column
	@ColumnDefault("null")
	private LocalDateTime deletedAt;


}
