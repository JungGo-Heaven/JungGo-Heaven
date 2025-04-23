package com.example.junggoheaven.domain.payments.service.order;

import org.springframework.stereotype.Service;

import com.example.junggoheaven.domain.payments.dto.request.CreateOrderRequestDto;
import com.example.junggoheaven.domain.payments.dto.response.OrderResponseDto;
import com.example.junggoheaven.domain.payments.entity.Order;
import com.example.junggoheaven.domain.payments.enums.PaymentMethod;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.service.component.ProductFinder;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	
	private final UserFinder userFinder;
	private final ProductFinder productFinder;
	private final OrderFinder orderFinder;
	private final OrderWriter orderWriter;

	public OrderResponseDto createOrder(Long userId, CreateOrderRequestDto createOrderRequestDto) {
		Long buyerId = createOrderRequestDto.getBuyerId();			// 구매자 ID
		Long productId = createOrderRequestDto.getProductId();		// 판매 상품 ID
		String detail = createOrderRequestDto.getDetail();			// 판매 상품 내용
		Long amount = createOrderRequestDto.getAmount();			// 판매 가격
		String method = createOrderRequestDto.getMethod();			// 결제 수단

		Product product = productFinder.findProductById(productId);
		User buyer = userFinder.findByUserId(buyerId);
		User seller = userFinder.findByUserId(userId);

		Order order = Order.of(product, buyer, seller, detail, amount, PaymentMethod.of(method));
		return OrderResponseDto.from(orderWriter.saveOrder(order));
	}
}