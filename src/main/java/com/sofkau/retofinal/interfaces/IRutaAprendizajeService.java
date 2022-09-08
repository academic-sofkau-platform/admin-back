package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.models.Ruta;
import com.sofkau.retofinal.models.RutaAprendizaje;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IRutaAprendizajeService {
    Mono<RutaAprendizaje> save(RutaAprendizaje rutaAprendizaje);
    Flux<RutaAprendizaje> findAll();
    Mono<RutaAprendizaje> findById(String rutaAprendizajeId);
    Mono<RutaAprendizaje> update(RutaAprendizaje rutaAprendizaje, String rutaAprendizajeId);
    Mono<Void> deleteById(String rutaAprendizajeId);
    Mono<RutaAprendizaje> addRoute(Ruta ruta, String rutaAprendizajeId);
}
