package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.CartItemProduct;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartItemProductRepository extends JpaRepository<CartItemProduct, Long> {
}
