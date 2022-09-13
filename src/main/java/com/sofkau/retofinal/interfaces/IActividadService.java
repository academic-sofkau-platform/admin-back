package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.dto.ActividadDto;
import com.sofkau.retofinal.models.Actividad;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IActividadService {
    Flux<ActividadDto> findAll();
    Mono<ActividadDto> addOrUpdate(Integer puntaje, String cursoId, String aprendizId, String fecha);
    Flux<Actividad> findByAprendizId(String aprendizId);
}
