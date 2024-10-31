package com.saga.payment.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

import com.commons.dto.OrderRequestDto;
import com.commons.dto.PaymentRequestDto;
import com.commons.event.OrderEvent;
import com.commons.event.PaymentEvent;
import com.commons.event.PaymentStatus;
import com.saga.payment.entity.UserBalance;
import com.saga.payment.entity.UserTransaction;
import com.saga.payment.repository.UserBalanceRepository;
import com.saga.payment.repository.UserTransactionRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
	private UserBalanceRepository userBalanceRepository;
	private UserTransactionRepository userTransactionRepository;

	@PostConstruct
	public void initUserBalanceInDB() {
		userBalanceRepository.saveAll(Stream.of(new UserBalance(101, 5000), new UserBalance(102, 6000),
				new UserBalance(103, 7000), new UserBalance(104, 4000), new UserBalance(105, 8000),
				new UserBalance(106, 9000), new UserBalance(107, 10000)).collect(Collectors.toList()));
	}

	/**
	 * // get the user Id and check the balance avaialble // if balance sufficient
	 * -> Payment completed and deduct amount from DB // if payment not sufficient
	 * ->cancel order event and update the amount in DB
	 * 
	 **/
	@Override
	@Transactional
	public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
		OrderRequestDto orderRequestDto = orderEvent.getOrderRequestDto();
		PaymentRequestDto paymentRequestDto = new PaymentRequestDto(orderRequestDto.getOrderId(),
				orderRequestDto.getUserId(), orderRequestDto.getAmount());
		return this.userBalanceRepository.findById(orderRequestDto.getOrderId())
				.filter(ub -> ub.getPrice() > orderRequestDto.getAmount()).map(ub -> {
					ub.setPrice(ub.getPrice() - orderRequestDto.getAmount());
					this.userTransactionRepository.save(new UserTransaction(orderRequestDto.getOrderId(),
							orderRequestDto.getUserId(), orderRequestDto.getAmount()));
					return new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_COMPLETED);
				}).orElse(new PaymentEvent(paymentRequestDto, PaymentStatus.PAYMENT_FAILED));
	}

	@Override
	@Transactional
	public void cancelOrderEvent(OrderEvent orderEvent) {
		userTransactionRepository.findById(orderEvent.getOrderRequestDto().getOrderId())
		.ifPresent(ut->{
				this.userTransactionRepository.delete(ut);
				this.userTransactionRepository.findById(ut.getOrderId())
				.ifPresent(ub->ub.setAmount(ub.getAmount()+ut.getAmount()));
		});
	}
}
