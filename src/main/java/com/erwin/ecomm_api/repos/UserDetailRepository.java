package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {
}
