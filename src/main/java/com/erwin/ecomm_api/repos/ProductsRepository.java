package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.Products;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductsRepository extends JpaRepository<Products, Integer> {
}
