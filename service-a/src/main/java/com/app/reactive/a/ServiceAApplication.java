package com.app.reactive.a;

import com.app.reactive.a.domain.Product;
import com.app.reactive.a.repository.ProductRepository;
import com.app.reactive.a.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ServiceAApplication {

  public static void main(String[] args) {
    SpringApplication.run(ServiceAApplication.class, args);
  }

  @Bean
  RouterFunction<ServerResponse> routes(ProductService productService) {
    return RouterFunctions.route(RequestPredicates.GET("/products"),
        request -> ServerResponse.ok().body(productService.findAll(), Product.class))
        .andRoute(RequestPredicates.GET("/products/{id}"),
            request -> ServerResponse.ok()
                .body(productService.findById(request.pathVariable("id")), Product.class));
  }

  @Bean
  WebClient client() {
    return WebClient.create("http://localhost:9091");
  }

  @Bean
  CommandLineRunner runner(WebClient client, ReactiveMongoTemplate template,
      ProductRepository productRepository) {
    template.createCollection(Product.class, CollectionOptions.empty().capped().size(10_000_000)
        .maxDocuments(20)
    );

    Flux<Product> productFlux = Flux.just(new Product(null, "Lenovo", 500.0, "loader-a"),
        new Product(null, "Mac", 2200.0, "loader-a"),
        new Product(null, "Dell", 300.0, "loader-a"),
        new Product(null, "HP", 400.0, "loader-a"));
    productRepository.saveAll(productFlux).subscribe(System.out::println);

    return args -> client.get().uri("/products").exchangeToFlux(response -> {
      if (response.statusCode().equals(HttpStatus.OK)) {
        return response.bodyToFlux(Product.class);
      } else {
        return Flux.error(new CustomException("Error Occurred!"));
      }
    }).subscribe(System.out::println);
  }

  static class CustomException extends RuntimeException {

    CustomException(String message) {
      super(message);
    }
  }
}
