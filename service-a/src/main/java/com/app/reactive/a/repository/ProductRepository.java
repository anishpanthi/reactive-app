package com.app.reactive.a.repository;

import com.app.reactive.a.domain.Product;
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
