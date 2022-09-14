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

    @Autowired
    DiagnosticoRendimientoServiceImpl diagnosticoRendimientoService;

    @Override
    public Mono<Notas> save(Notas notas) {
        return repository.save(notas);
    }

    @Override
    public Flux<Notas> findAll() {
        return repository.findAll();
    }

    @Override
    public Flux<Notas> findByAprendizId(String aprendizId) {
        return repository.findAll().filter(notas1 -> notas1.getAprendizId().equals(aprendizId));

    }

    @Override
    public Mono<Notas> findByAprendizIdAndTrainingId(String aprendizId, String trainingId) {
        return Mono.just(Objects.requireNonNull(repository.findAll()
                .filter(notas1 -> notas1.getAprendizId().equals(aprendizId))
                .filter(notas1 -> notas1.getTrainingId().equals(trainingId))
                .blockFirst()));

    }


    @Override
    public Mono<Notas> findById(String notasId) {
        return repository.findById(notasId);
    }

    public void diagnosticar(Flux<Notas> notas){
        diagnosticoRendimientoService.diagnosticar(notas);
    }
}
