package com.saga.payment.service;

import com.commons.event.OrderEvent;
import com.commons.event.PaymentEvent;

public interface PaymentService {
	
	PaymentEvent newOrderEvent(OrderEvent orderEvent);
	
	void cancelOrderEvent(OrderEvent orderEvent);
}
