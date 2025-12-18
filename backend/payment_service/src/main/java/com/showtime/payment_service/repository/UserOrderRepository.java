package com.showtime.payment_service.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.showtime.payment_service.entity.UserOrder;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer>{


	 UserOrder findByRazorpayOrderId(String razorpayOrderId);

}
