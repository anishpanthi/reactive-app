package com.app.reactive.b.repository;

import com.app.reactive.b.domain.Product;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author Anish Panthi
 */
@Repository
public interface ProductRepository extends ReactiveSortingRepository<Product, Long> {

  Mono<Product> findByName(String name);
}
