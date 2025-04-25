package com.example.junggoheaven.domain.payments.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.payments.dto.request.CreateOrderRequestDto;
import com.example.junggoheaven.domain.payments.dto.response.OrderResponseDto;
import com.example.junggoheaven.domain.payments.entity.Order;
import com.example.junggoheaven.domain.payments.enums.PaymentMethod;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.service.component.ProductFinder;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;
	@Mock
	private UserFinder userFinder;
	@Mock
	private ProductFinder productFinder;
	@Mock
	private OrderWriter orderWriter;
	@Mock
	private OrderFinder orderFinder;

	User buyer;
	User seller;
	Product product;
	Order order;

	@BeforeEach
	void setUp() {
		buyer = User.of("buyer@email.com", "buyer", "123");
		seller = User.of("buyer@email.com", "buyer", "123");
		product = new Product(seller, "상품", "판매 상품", 500L);
		order = Order.of(product, buyer, seller, "상품 이름", 300L, PaymentMethod.VIRTUAL_ACCOUNT);

		ReflectionTestUtils.setField(order, "orderKey", "orderKey");
		ReflectionTestUtils.setField(buyer, "customerKey", "customerKey");
	}

	@Test
	void createOrder() {
		Long buyerId = 1L;
		Long sellerId = 2L;

		CreateOrderRequestDto requestDto = CreateOrderRequestDto.of(buyerId, 1L, "상품", 500L, "VIRTUAL_ACCOUNT");

		given(productFinder.findProductById(any())).willReturn(product);
		given(userFinder.findByUserId(buyerId)).willReturn(buyer);
		given(userFinder.findByUserId(sellerId)).willReturn(seller);
		given(orderWriter.saveOrder(any())).willReturn(order);

		OrderResponseDto responseDto = orderService.createOrder(sellerId, requestDto);

		assertThat(responseDto).isNotNull();
		assertThat(responseDto.getOrderId()).isEqualTo(order.getOrderKey());
		assertThat(responseDto.getCustomerKey()).isEqualTo(order.getBuyer().getCustomerKey());
	}

	@Test
	void cancelOrder() {
		given(orderFinder.findByOrderId(any())).willReturn(order);
		orderService.cancelOrder(1L);
		assertThat(order).isNotNull();
	}
}