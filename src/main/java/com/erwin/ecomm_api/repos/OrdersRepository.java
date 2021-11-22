package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
