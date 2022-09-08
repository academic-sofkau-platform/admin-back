package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.models.Curso;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICursoService {
    Mono<Curso> save(Curso curso);
    Flux<Curso> findAll();
    Mono<Curso> findById(String cursoId);
    Mono<Curso> update(Curso curso, String cursoId);
    Mono<Void> deleteById(String cursoId);
}
