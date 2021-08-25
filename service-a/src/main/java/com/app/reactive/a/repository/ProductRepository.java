package com.app.reactive.a.repository;

import com.app.reactive.a.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Anish Panthi
 */
@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>,
    ReactiveCrudRepository<Product, String> {

  Mono<Product> findByName(String name);

  @Tailable
  Flux<Product> findAllByName(String name);
}
