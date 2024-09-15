package com.challenges.price_finder.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.challenges.price_finder.common.PersistenceAdapter;

@Configuration
@ComponentScan(basePackages = "com.challenges.price_finder.infrastructure.adapters.out.persistence",
               includeFilters = @ComponentScan.Filter(PersistenceAdapter.class))
public class PersistenceAdapterConfig {
}
