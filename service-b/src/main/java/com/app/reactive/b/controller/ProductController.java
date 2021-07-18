package com.app.reactive.b.controller;

import com.app.reactive.b.domain.Product;
import com.app.reactive.b.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Anish Panthi
 */
@RestController
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody Product p) {
    productService.create(p);
  }

  @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Mono<Product>> findById(@PathVariable("id") Long id) {
    Mono<Product> productMono = productService.findById(id);
    HttpStatus status = productMono != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(productMono, status);
  }

  @GetMapping(value = "/products/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Mono<Product>> findByName(@PathVariable("name") String name) {
    Mono<Product> productMono = productService.findByName(name);
    HttpStatus status = productMono != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(productMono, status);
  }

  @GetMapping(value = "/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<Flux<Product>> findAll() {
    return new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
  }

  @PutMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Mono<Product>> update(@RequestBody Product product) {
    return new ResponseEntity<>(productService.update(product), HttpStatus.OK);
  }

  @DeleteMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public void delete(@RequestBody Product product) {
    productService.delete(product);
  }
}
