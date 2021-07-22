package com.app.reactive.b.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

/**
 * @author Anish Panthi
 */
@Configuration
@EntityScan(basePackages = {"com.app.reactive.b.domain"})
@EnableReactiveMongoRepositories(basePackages = {"com.app.reactive.b.repository"})
public class DataSourceConfig {

}
