package com.example.junggoheaven.domain.product.service;


import com.example.junggoheaven.domain.product.dto.request.ProductRequestDto;
import com.example.junggoheaven.domain.product.dto.response.ProductResponseDto;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.repository.ProductRepository;
import com.example.junggoheaven.domain.product.service.component.ProductChecker;
import com.example.junggoheaven.domain.product.service.component.ProductFinder;
import com.example.junggoheaven.domain.product.service.component.ProductWriter;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductFinder productFinder;
	private final ProductWriter productWriter;
	private final ProductChecker productChecker;

	private final UserFinder userFinder;


	/*
		상품 등록 메서드
	*/
	@Transactional
	public ProductResponseDto saveProduct(AuthUser authUser, ProductRequestDto productRequestDto) {

		/*
		// 등록할 멤버(회원) 정보
		Member member = memberRepository.findById(authUser.getId())
			.orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_MEMBER));
		*/

		User user = userFinder.findByUserId(authUser.getId());

		Product product = new Product(
			user,
			productRequestDto.getName(),
			productRequestDto.getInformation(),
			productRequestDto.getPrice()
		);

		productWriter.saveProduct(product);
		//productRepository.save(product);

		return new ProductResponseDto(product);
	}


	/*
		상품 다건 페이지네이션 조회 메서드
	*/
	@Transactional(readOnly = true)
	public Page<ProductResponseDto> findAllProduct(Pageable pageable) {

		Page<Product> productPage = productFinder.findAllProductOpt(pageable);

		List<ProductResponseDto> dtoList = productPage.getContent().stream()
			.map(ProductResponseDto::toDto)
			.toList();

		return new PageImpl<>(dtoList, pageable, productPage.getTotalElements());
	}


	/*
		상품 단건 조회 메서드
	*/
	@Transactional
	public ProductResponseDto findProductById(Long id) {

		Product product = productFinder.findProductById(id);

		return new ProductResponseDto(product);
	}


}
