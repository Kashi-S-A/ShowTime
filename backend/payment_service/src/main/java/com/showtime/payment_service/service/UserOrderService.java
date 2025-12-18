package com.showtime.payment_service.service;

import java.util.Map;

import com.razorpay.RazorpayException;
import com.showtime.payment_service.entity.UserOrder;

public interface UserOrderService {
	public UserOrder createOrder(UserOrder userOrder) throws RazorpayException;

	public  UserOrder updatePaymentStatus(Map<String, String> responsePayload);

}
