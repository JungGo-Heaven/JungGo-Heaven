package com.example.junggoheaven.domain.product.service.component;


import com.example.junggoheaven.domain.product.dto.request.ProductSellStatusRequestDto;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.enums.SellStatus;
import com.example.junggoheaven.domain.product.repository.ProductRepository;
import com.example.junggoheaven.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductChecker {

	private final ProductRepository productRepository;

	public boolean isMyProduct(User user, Product product) {

		User productUser = product.getUser();

		if (user.getId() == productUser.getId()) {
			return true;
		} else {
			return false;
		}

	}


	public boolean isSameSellStatus(Product product, SellStatus requestSellStatus) {

		if (product.getSellStatus() == requestSellStatus) {
			return true;
		} else {
			return false;
		}

	}


}
