package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserPaymentRepository extends JpaRepository<UserPayment, Integer> {
}
