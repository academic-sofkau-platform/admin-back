package com.sofkau.retofinal.services;

import com.sofkau.retofinal.interfaces.INotasService;
import com.sofkau.retofinal.models.Actividad;
import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.models.Tareas;
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

    @Autowired
    private ActividadServiceImpl actividadService;

    public ArrayList<Notas> notas = new ArrayList<>();
    public NotasServices() {
        List<Tareas> actividades1 = new ArrayList<>();

        this.notas.add(new Notas("1555de", "7595fb82-db54-490b-91fc-ec0c8e7daaa1", actividades1));
        this.notas.add(new Notas("1231231311354456", "4149bdc6-f0b4-4f94-a030-385c695a88a7", actividades1));
        this.notas.add(new Notas("1555de12", "7595fb82-db54-490b-91fc-ec0c8e7daaa1", actividades1));

    }

    @Override
    public Mono<Notas> save(Notas notas) {
        return repository.save(notas);
    }

    @Override
    public Flux<Notas> findAll() {
        return /*repository.findAll();*/ Flux.fromIterable(this.notas);
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
}
