package com.app.reactive.b.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * @author Anish Panthi
 */
@Configuration
@EntityScan(basePackages = {"com.app.reactive.b.domain"})
@EnableR2dbcRepositories(basePackages = {"com.app.reactive.b.repository"})
public class DataSourceConfig {

}
