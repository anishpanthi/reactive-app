package com.app.reactive.b.service;

import com.app.reactive.b.domain.Product;
import reactor.core.publisher.Mono;

/**
 * @author Anish Panthi
 */
public interface ProductService extends BaseService<Product, Long> {

  Mono<Product> findByName(String name);

}
