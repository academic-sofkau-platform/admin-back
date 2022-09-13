package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.models.Notas;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface INotasService {

    Mono<Notas> save(Notas notas);
    Flux<Notas> findAll();
    Mono<Notas> findById(String notasId);
    Mono<Notas> update(String notasId, Notas notas);
    Mono<Notas> findByAprendizId(String aprendizId);

}
