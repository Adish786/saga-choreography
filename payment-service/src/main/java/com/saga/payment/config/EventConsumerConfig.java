package com.saga.payment.config;

import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.commons.event.PaymentEvent;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class EventConsumerConfig {

	private OrderStatusUpdateHandler handler;

	@Bean
	public Consumer<PaymentEvent> paymentEventConsumer() {

		// Listen payment-event-topic
		// will check payemnt status
		// if payment status completed -> complete the order
		// if payment status failed -> cancel the order
		return payment -> this.handler.updateOrder(payment.getPaymentRequestDto().getOrderId(), po -> {
			po.setPaymentStatus(payment.getPaymentStatus());
		});
	}

}
