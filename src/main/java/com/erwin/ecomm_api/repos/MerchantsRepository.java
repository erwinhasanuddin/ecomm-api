package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MerchantsRepository extends JpaRepository<Merchants, Integer> {
}
