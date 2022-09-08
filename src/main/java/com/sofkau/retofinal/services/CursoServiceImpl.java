package com.sofkau.retofinal.services;

import com.sofkau.retofinal.interfaces.ICursoService;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Service
public class CursoServiceImpl implements ICursoService {
    @Autowired
    CursoRepository repository;
    @Override
    public Mono<Curso> save(Curso curso) {
        return repository.save(curso);
    }

    @Override
    public Flux<Curso> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Curso> findById(String cursoId) {
        return repository.findById(cursoId);
    }

    @Override
    public Mono<Curso> update(Curso curso, String cursoId) {
        return repository.findById(cursoId)
                .flatMap(curso1 -> {
                    curso1.setId(cursoId);
                    return save(curso1);
                }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> deleteById(String cursoId) {
        return repository.deleteById(cursoId);
    }
}
