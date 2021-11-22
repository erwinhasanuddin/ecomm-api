package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.ShoppingSession;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ShoppingSessionRepository extends JpaRepository<ShoppingSession, Integer> {
    ShoppingSession findByuserId(Integer userId);
}
