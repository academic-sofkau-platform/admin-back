package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.models.Notas;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface INotasService {

    Mono<Notas> save(Notas notas);
    Flux<Notas> findAll();
    Mono<Notas> findById(String notasId);
    Mono<Notas> update(String notasId, Notas notas);
    Flux<Notas> findByAprendizId(String aprendizId);
    Mono<Notas> findByAprendizIdAndTrainingId(String aprendizId, String trainingId);

}
