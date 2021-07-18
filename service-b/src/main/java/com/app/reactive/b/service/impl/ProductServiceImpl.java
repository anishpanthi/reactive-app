package com.app.reactive.b.service.impl;

import com.app.reactive.b.domain.Product;
import com.app.reactive.b.repository.ProductRepository;
import com.app.reactive.b.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Anish Panthi
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public void create(Product product) {
    productRepository.save(product).subscribe();
  }

  @Override
  public Mono<Product> findById(Long id) {
    return productRepository.findById(id);
  }

  @Override
  public Flux<Product> findAll() {
    return productRepository.findAll();
  }

  @Override
  public Mono<Product> update(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Mono<Void> delete(Product product) {
    return productRepository.deleteById(product.getId());
  }

  @Override
  public Mono<Product> findByName(String name) {
    return productRepository.findByName(name);
  }
}
