package com.showtime.payment_service.service;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.showtime.payment_service.entity.UserOrder;
import com.showtime.payment_service.repository.UserOrderRepository;

@Service
public class UserOrderServiceImpl implements UserOrderService {

		
		@Autowired
		private UserOrderRepository orderRepository;

		@Value("${razorpay.key.id}")
		private String razorpayKey;

		@Value("${razorpay.secret.key}")
		private String razorpaySecret;

		private RazorpayClient razorpayClient;

		@Override
		public UserOrder createOrder(UserOrder userOrder) throws RazorpayException {
			JSONObject orderReq = new JSONObject();
			orderReq.put("amount", userOrder.getAmount() * 100);
			orderReq.put("currency", "INR");
			orderReq.put("receipt", userOrder.getEmail());

			this.razorpayClient = new RazorpayClient(razorpayKey, razorpaySecret);

			Order order = razorpayClient.orders.create(orderReq);

			userOrder.setRazorpayOrderId(order.get("id"));
			userOrder.setOrderStatus(order.get("status"));

			orderRepository.save(userOrder);

			return userOrder;
	}
		@Override
		public UserOrder updatePaymentStatus(Map<String, String> responsePayload) {

		    String razorpayOrderId = responsePayload.get("razorpay_order_id");
		    String razorpayPaymentId = responsePayload.get("razorpay_payment_id");
		    String razorpaySignature = responsePayload.get("razorpay_signature");

		    UserOrder order = orderRepository.findByRazorpayOrderId(razorpayOrderId);
		    if (order == null) {
		        throw new RuntimeException("Order not found");
		    }

		    boolean isValid = verifySignature(
		            razorpayOrderId,
		            razorpayPaymentId,
		            razorpaySignature
		    );

		    if (!isValid) {
		        order.setOrderStatus("FAILED");
		        return orderRepository.save(order);
		    }

		    order.setRazorpayPaymentId(razorpayPaymentId);
		    order.setRazorpaySignature(razorpaySignature);
		    order.setOrderStatus("SUCCESS");

		    return orderRepository.save(order);
		}
		
		public boolean verifySignature(
		        String orderId,
		        String paymentId,
		        String signature) {

		    try {
		        JSONObject options = new JSONObject();
		        options.put("razorpay_order_id", orderId);
		        options.put("razorpay_payment_id", paymentId);
		        options.put("razorpay_signature", signature);

		        return Utils.verifyPaymentSignature(options, razorpaySecret);
		    } catch (Exception e) {
		        return false;
		    }
		}

}
