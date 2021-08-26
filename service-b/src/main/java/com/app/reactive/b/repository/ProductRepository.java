package com.app.reactive.b.repository;

import com.app.reactive.b.domain.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author Anish Panthi
 */
@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

  Mono<Product> findByName(String name);
}
