package com.app.reactive.a.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * @author Anish Panthi
 */
@Configuration
@EntityScan(basePackages = {"com.app.reactive.a.domain"})
@EnableR2dbcRepositories(basePackages = {"com.app.reactive.a.repository"})
public class DataSourceConfig {

}
