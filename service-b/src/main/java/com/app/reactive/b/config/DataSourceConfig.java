package com.app.reactive.b.config;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * @author Anish Panthi
 */
@Configuration
@EntityScan(basePackages = {"com.app.reactive.b.domain"})
@EnableR2dbcRepositories(basePackages = {"com.app.reactive.b.repository"})
public class DataSourceConfig {

  @Bean("pgConnectionFactory")
  public ConnectionFactory pgConnectionFactory() {
    return new PostgresqlConnectionFactory(
        PostgresqlConnectionConfiguration.builder()
            .host("localhost")
            .port(5432)
            .database("reactive_app")
            .username("postgres")
            .password("postgres")
            .build()
    );
  }
}
