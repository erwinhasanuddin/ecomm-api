package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer> {
}
