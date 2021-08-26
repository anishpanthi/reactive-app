package com.app.reactive.b.controller;

import com.app.reactive.b.domain.Product;
import com.app.reactive.b.service.ProductService;
import io.r2dbc.postgresql.api.Notification;
import io.r2dbc.postgresql.api.PostgresqlConnection;
import io.r2dbc.postgresql.api.PostgresqlResult;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.time.Duration;
import java.time.LocalTime;
import java.util.function.BiFunction;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.r2dbc.core.DatabaseClient;
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
public class ProductController {

  private static final BiFunction<Row, RowMetadata, Product> CONVERT_TO_PRODUCT = (row, rowMetaData) -> Product
      .builder()
      .id(row.get("id", Long.class))
      .name(row.get("name", String.class))
      .price(row.get("price", Double.class))
      .source(row.get("source", String.class))
      .build();
  private final ProductService productService;
  private final PostgresqlConnection postgresqlConnection;
  private final DatabaseClient databaseClient;

  public ProductController(ProductService productService,
      @Qualifier("pgConnectionFactory") ConnectionFactory connectionFactory,
      DatabaseClient databaseClient) {
    this.productService = productService;
    this.postgresqlConnection = Mono.from(connectionFactory.create())
        .cast(PostgresqlConnection.class)
        .block();
    this.databaseClient = databaseClient;
  }

  @PostConstruct
  private void postConstruct() {
    this.postgresqlConnection.createStatement("LISTEN product_added_notification")
        .execute()
        .flatMap(PostgresqlResult::getRowsUpdated)
        .subscribe();
  }

  @PreDestroy
  private void preDestroy() {
    this.postgresqlConnection.close().subscribe();
  }

  @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void create(@RequestBody Product p) {
    productService.create(p);
  }

  @SneakyThrows
  @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Mono<Product>> findById(@PathVariable("id") Long id) {
    var productMono = productService.findById(id);
    var status = productMono != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(productMono, status);
  }

  @GetMapping(value = "/products/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Mono<Product>> findByName(@PathVariable("name") String name) {
    var productMono = productService.findByName(name);
    var status = productMono != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    return new ResponseEntity<>(productMono, status);
  }

  @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
  public Flux<Product> findAll() {
    return productService.findAll();
  }

  @GetMapping(value = "/stream-products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<CharSequence> findAllByStream() {
    var notificationFlux = postgresqlConnection.getNotifications();
    return notificationFlux.map(Notification::getParameter);
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

  @GetMapping(path = "/stream-flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<String> streamFlux() {
    return Flux.interval(Duration.ofSeconds(1))
        .map(sequence -> "Flux - " + LocalTime.now().toString());
  }

  @GetMapping(value = "/findAll/products")
  public Flux<Product> findAllAgain() {
    return databaseClient.sql("SELECT * FROM reactive_app.reactive.product")
        .filter((statement, executeFunction) -> statement.fetchSize(10).execute())
        .map(CONVERT_TO_PRODUCT)
        .all();
  }
}
