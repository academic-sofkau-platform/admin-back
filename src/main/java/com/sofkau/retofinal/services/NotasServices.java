package com.sofkau.retofinal.services;

import com.sofkau.retofinal.interfaces.INotasService;
import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.models.Tarea;
import com.sofkau.retofinal.repositories.NotasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class NotasServices implements INotasService {

    @Autowired
    NotasRepository repository;

    @Autowired
    DiagnosticoRendimientoServiceImpl diagnosticoRendimientoService;

    public ArrayList<Notas> notas = new ArrayList<>();

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
        return repository.findById(aprendizId);

    }

    @Override
    public Mono<Notas> findByAprendizIdAndTrainingId(String aprendizId, String trainingId) {
        return Mono.just(Objects.requireNonNull(repository.findAll()
                .filter(notas1 -> notas1.getAprendizEmail().equals(aprendizId))
                .filter(notas1 -> notas1.getTrainingId().equals(trainingId))
                .blockFirst()));

    }

}
