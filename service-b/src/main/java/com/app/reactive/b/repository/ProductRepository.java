package com.app.reactive.b.repository;

import com.app.reactive.b.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author Anish Panthi
 */
@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {

  Mono<Product> findByName(String name);
}
