package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Integer> {
}
