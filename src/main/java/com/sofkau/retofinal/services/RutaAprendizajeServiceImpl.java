package com.sofkau.retofinal.services;

import com.sofkau.retofinal.interfaces.IRutaAprendizajeService;
import com.sofkau.retofinal.models.RutaAprendizaje;
import com.sofkau.retofinal.repositories.RutaAprendizajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RutaAprendizajeServiceImpl implements IRutaAprendizajeService {
    @Autowired
    RutaAprendizajeRepository repository;

    @Override
    public Mono<RutaAprendizaje> save(RutaAprendizaje rutaAprendizaje) {
        return null;
    }

    @Override
    public Flux<RutaAprendizaje> findAll() {
        return null;
    }

    @Override
    public Mono<RutaAprendizaje> findById(String rutaAprendizajeId) {
        return null;
    }

    @Override
    public Mono<RutaAprendizaje> update(RutaAprendizaje rutaAprendizaje, String rutaAprendizajeId) {
        return null;
    }

    @Override
    public Mono<Void> deleteById(String rutaAprendizajeId) {
        return null;
    }
}
