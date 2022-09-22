package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.dto.ActividadDto;
import com.sofkau.retofinal.models.Actividad;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface IActividadService {

    Mono<ActividadDto> save(Actividad actividad);
    Flux<ActividadDto> findAll();
    Mono<ActividadDto> findById(String Id);
    Mono<ActividadDto> updatePuntaje(Actividad actividad, Integer puntaje);
    Flux<Actividad> findActivityByAprendizId(String aprendizId);
}
