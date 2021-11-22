package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {
}
