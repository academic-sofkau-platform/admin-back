package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.models.RutaAprendizaje;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface IRutaAprendizajeService {
    Mono<RutaAprendizaje> save(RutaAprendizaje rutaAprendizaje);
    Flux<RutaAprendizaje> findAll();
    Mono<RutaAprendizaje> findById(String rutaAprendizajeId);
    Mono<RutaAprendizaje> update(RutaAprendizaje rutaAprendizaje, String rutaAprendizajeId);
    Mono<Void> deleteById(String rutaAprendizajeId);
}
