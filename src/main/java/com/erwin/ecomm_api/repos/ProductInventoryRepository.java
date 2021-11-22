package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Integer> {
    ProductInventory findByproductId(Integer productId);
}
