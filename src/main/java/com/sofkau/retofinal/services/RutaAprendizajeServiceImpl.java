package com.sofkau.retofinal.services;

import com.sofkau.retofinal.interfaces.IRutaAprendizajeService;
import com.sofkau.retofinal.models.RutaAprendizaje;
import com.sofkau.retofinal.repositories.RutaAprendizajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class RutaAprendizajeServiceImpl implements IRutaAprendizajeService {
    @Autowired
    RutaAprendizajeRepository repository;

    @Override
    public Mono<RutaAprendizaje> save(RutaAprendizaje rutaAprendizaje) {
        return repository.save(rutaAprendizaje);
    }

    @Override
    public Flux<RutaAprendizaje> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<RutaAprendizaje> findById(String rutaAprendizajeId) {
        return repository.findById(rutaAprendizajeId);
    }

    @Override
    public Mono<RutaAprendizaje> update(RutaAprendizaje rutaAprendizaje, String rutaAprendizajeId) {
        return repository
                .findById(rutaAprendizajeId)
                .flatMap(rutaAprendizaje1 -> {
                    rutaAprendizaje1.setId(rutaAprendizajeId);
                    return save(rutaAprendizaje1);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> deleteById(String rutaAprendizajeId) {
        return repository
                .findById(rutaAprendizajeId)
                .flatMap(rutaAprendizaje -> repository.deleteById(rutaAprendizaje.getId()))
                .switchIfEmpty(Mono.empty());
    }
}
