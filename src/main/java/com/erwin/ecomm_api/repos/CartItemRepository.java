package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByproductId(Integer productId);
    List<CartItem> findByshoppingSessionId(Integer shoppingSessionId);
}
