package com.app.reactive.a.controller;

import com.app.reactive.a.domain.Product;
import com.app.reactive.a.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * @author Anish Panthi
 */
@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductRepository productRepository;

  @GetMapping(value = "/product/all/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<Product> findAllByStreams(@PathVariable String name) {
    return productRepository.findAllByName(name).subscribeOn(Schedulers.boundedElastic());
  }
}
