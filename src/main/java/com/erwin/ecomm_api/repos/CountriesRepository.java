package com.erwin.ecomm_api.repos;

import com.erwin.ecomm_api.domain.Countries;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CountriesRepository extends JpaRepository<Countries, Integer> {
}
