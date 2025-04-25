package com.example.junggoheaven.domain.payments.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.junggoheaven.domain.payments.entity.Order;
import com.example.junggoheaven.domain.payments.enums.PaymentMethod;
import com.example.junggoheaven.domain.payments.exception.OrderNotFoundException;
import com.example.junggoheaven.domain.payments.repository.OrderRepository;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;

@ExtendWith(MockitoExtension.class)
class OrderFinderTest {

	@InjectMocks
	private OrderFinder orderFinder;
	@Mock
	private OrderRepository orderRepository;

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
	void findByOrderId() {
		given(orderRepository.findById(1L)).willReturn(Optional.of(order));
		given(orderRepository.findById(2L)).willReturn(Optional.empty());

		Order result = orderFinder.findByOrderId(1L);
		assertThat(result).isNotNull();
		assertThat(result.getOrderKey()).isEqualTo(order.getOrderKey());

		assertThrows(OrderNotFoundException.class, () -> {
			orderFinder.findByOrderId(2L);
		});
	}

	@Test
	void findByOrderKey() {
		given(orderRepository.findByOrderKey("Y")).willReturn(Optional.of(order));
		given(orderRepository.findByOrderKey("N")).willReturn(Optional.empty());

		Order result = orderFinder.findByOrderKey("Y");
		assertThat(result).isNotNull();
		assertThat(result.getOrderKey()).isEqualTo(order.getOrderKey());

		assertThrows(OrderNotFoundException.class, () -> {
			orderFinder.findByOrderKey("N");
		});
	}
}