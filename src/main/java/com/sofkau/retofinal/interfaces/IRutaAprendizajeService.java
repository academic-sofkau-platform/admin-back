package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.dto.RutaDto;
import com.sofkau.retofinal.models.Ruta;
import com.sofkau.retofinal.models.RutaAprendizaje;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRutaAprendizajeService {
    Mono<RutaAprendizajeDto> save(RutaAprendizajeDto rutaAprendizajeDto);
    Flux<RutaAprendizajeDto> findAll();
    Mono<RutaAprendizajeDto> findById(String rutaAprendizajeId);
    Mono<RutaAprendizajeDto> update(RutaAprendizajeDto rutaAprendizajeDto, String rutaAprendizajeId);
    Mono<Void> deleteById(String rutaAprendizajeId);
    Mono<RutaAprendizajeDto> addRoute(RutaDto rutaDto, String rutaAprendizajeId);
    Mono<Void> removeRoute(String rutaId, String rutaAprendizajeId);
    Mono<Boolean> controlCursoEnRutaAprendizaje(String cursoId);
}
