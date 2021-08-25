package com.app.reactive.b;

import com.app.reactive.b.domain.Product;
import com.app.reactive.b.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ServiceBApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceBApplication.class, args);
  }

  @Bean
  CommandLineRunner loader(ProductRepository productRepository) {
    return args -> {
      Flux<Product> productFlux = Flux.just(new Product(null, "Pixel", 100.0, "loader"),
          new Product(null, "Pixel 2", 200.0, "loader"),
          new Product(null, "Pixel 3", 300.0, "loader"),
          new Product(null, "Pixel 4", 400.0, "loader"));
      productRepository.deleteAll().subscribe(null, null,
          () -> productRepository.saveAll(productFlux).subscribe(System.out::println));
    };
  }
}
