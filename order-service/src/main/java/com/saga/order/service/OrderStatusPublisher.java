package com.saga.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.commons.dto.OrderRequestDto;
import com.commons.event.OrderEvent;
import com.commons.event.OrderStatus;

import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

	@Autowired
	private Sinks.Many<OrderEvent> orderSinks;
	
	public void publisheOrderEvent(OrderRequestDto orderRequestDto,OrderStatus orderStatus) {
		OrderEvent orderEvent = new OrderEvent(orderRequestDto, orderStatus);
		this.orderSinks.tryEmitNext(orderEvent);
	}
}
