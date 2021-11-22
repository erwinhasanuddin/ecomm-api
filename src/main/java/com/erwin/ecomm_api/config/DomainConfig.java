package com.erwin.ecomm_api.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.erwin.ecomm_api.domain")
@EnableJpaRepositories("com.erwin.ecomm_api.repos")
@EnableTransactionManagement
public class DomainConfig {
}
