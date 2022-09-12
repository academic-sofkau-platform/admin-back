package com.sofkau.retofinal.services;

import com.sofkau.retofinal.interfaces.INotasService;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.repositories.CursoRepository;
import com.sofkau.retofinal.repositories.NotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<Notas> update(String notasId) {
        return repository.findById(notasId)
                .flatMap(notas1 -> {
                    notas1.setId(notasId);
                    return save(notas1);
                }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Notas> findById(String notasId) {
        return repository.findById(notasId);
    }
}
