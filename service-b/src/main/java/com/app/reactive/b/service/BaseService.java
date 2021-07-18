package com.app.reactive.b.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Anish Panthi
 */
public interface BaseService<R, T> {

  void create(R r);

  Mono<R> findById(T id);

  Flux<R> findAll();

  Mono<R> update(R r);

  Mono<Void> delete(R r);

}
