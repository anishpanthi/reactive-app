package com.app.reactive.a.controller;

import com.app.reactive.a.domain.Product;
import com.app.reactive.a.dto.ProductDto;
import com.app.reactive.a.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
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
  public ResponseEntity<Mono<ProductDto>> findById(@PathVariable("id") Long id) {
    // Load 1 User From DB
    Mono<Product> productMono = productService.findById(id);

    // Get Same User By Calling Service B
    WebClient webClient = WebClient.create();
    Mono<Product> productFromServiceB = webClient.get()
        .uri("http://localhost:9091/products/" + id)
        .retrieve()
        .bodyToMono(Product.class);

    // Lets Combine both Products
    Mono<ProductDto> combinedProduct = productMono.zipWith(productFromServiceB)
        .map(product -> new ProductDto(product.getT1(), product.getT2()));

    return new ResponseEntity<>(combinedProduct, HttpStatus.OK);
  }
}
