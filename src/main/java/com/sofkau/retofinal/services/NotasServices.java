package com.sofkau.retofinal.services;

import com.sofkau.retofinal.interfaces.INotasService;
import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.repositories.NotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
public class NotasServices implements INotasService {

    @Autowired
    NotasRepository repository;
    @Override
    public Mono<Notas> save(Notas notas) {
        return repository.save(notas);
    }

    @Override
    public Flux<Notas> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Notas> findByAprendizId(String aprendizId) {
        return Mono.just(Objects.requireNonNull(repository.findAll().filter(notas1 -> notas1.getAprendizId().equals(aprendizId)).blockFirst()));

    }

    @Override
    public Mono<Notas> update(String notasId, Notas notas) {
        return repository.findById(notasId)
                .flatMap(notas1 -> {
                    notas.setId(notasId);
                    return save(notas);
                }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Notas> findById(String notasId) {
        return repository.findById(notasId);
    }
}
