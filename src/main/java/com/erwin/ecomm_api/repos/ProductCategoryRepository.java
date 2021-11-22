package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
