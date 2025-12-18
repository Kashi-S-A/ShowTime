package com.showtime.payment_service.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.showtime.payment_service.entity.UserOrder;
import com.showtime.payment_service.service.UserOrderService;

@RestController
@RequestMapping("/payments")
public class UserOrderController {
	@Autowired
	private UserOrderService orderService;
	
    @PostMapping("/create-order")
    public ResponseEntity<UserOrder> createOrder(@RequestBody UserOrder userOrder) throws Exception {

        UserOrder createdOrder = orderService.createOrder(userOrder);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }
    
    @PostMapping("/payment-callback")
    public ResponseEntity<UserOrder> handlePaymentCallback(
            @RequestParam Map<String, String> responsePayload) {
        UserOrder updatedOrder = orderService.updatePaymentStatus(responsePayload);
        return ResponseEntity.ok(updatedOrder);
    }
}
