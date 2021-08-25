package com.app.reactive.a.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author Anish Panthi
 */
@Configuration
@EntityScan(basePackages = {"com.app.reactive.a.domain"})
@EnableReactiveMongoRepositories(basePackages = {"com.app.reactive.a.repository"})
public class DataSourceConfig {

}
