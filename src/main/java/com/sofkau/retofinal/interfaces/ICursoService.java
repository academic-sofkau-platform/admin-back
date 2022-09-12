package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.dto.CursoDto;
import com.sofkau.retofinal.models.Curso;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICursoService {
    Mono<CursoDto> save(Curso curso);
    Flux<CursoDto> findAll();
    Mono<CursoDto> findById(String cursoId);
    Mono<CursoDto> update(Curso curso, String cursoId);
    Mono<Void> deleteById(String cursoId);
}
