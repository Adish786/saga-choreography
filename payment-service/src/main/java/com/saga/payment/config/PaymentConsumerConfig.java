package com.saga.payment.config;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.commons.event.OrderEvent;
import com.commons.event.OrderStatus;
import com.commons.event.PaymentEvent;
import com.saga.payment.service.PaymentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class PaymentConsumerConfig {

	private PaymentService paymentService;

	@Bean
	public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor() {
		return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
	}

	private Mono<PaymentEvent> processPayment(OrderEvent orderEvent) {
		// get the user Id and check the balance avaialble
		// if balance sufficient -> Payment completed and deduct amount from DB
		// if payment not sufficient ->cancel order event and update the amount in DB
		if (OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus())) {
			return Mono.fromSupplier(() -> this.paymentService.newOrderEvent(orderEvent));
		} else {
			return Mono.fromRunnable(() -> this.paymentService.cancelOrderEvent(orderEvent));
		}

	}
}
