package com.app.reactive.a.service;

import com.app.reactive.a.domain.Product;
import reactor.core.publisher.Mono;

/**
 * @author Anish Panthi
 */
public interface ProductService extends BaseService<Product, String> {

  Mono<Product> findByName(String name);

}
