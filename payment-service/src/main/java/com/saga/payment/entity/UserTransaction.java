package com.saga.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	private int userId;
	private int amount;
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public UserTransaction(Integer orderId, int userId, int amount) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.amount = amount;
	}
	
	

}
