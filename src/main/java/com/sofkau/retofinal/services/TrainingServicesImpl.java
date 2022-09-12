package com.sofkau.retofinal.services;

import com.sofkau.retofinal.interfaces.ITrainingService;
import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.repositories.TrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingServicesImpl implements ITrainingService {
    @Autowired
    TrainingRepository repository;

    @Override
    public Mono<Training> save(Training training) {
        return repository.save(training);
    }

    @Override
    public Mono<Training> asignarCoach(String coach, String trainingId) {
        return repository
                .findById(trainingId)
                .flatMap(training -> {
                    training.setTrainingId(trainingId);
                    training.setCoach(coach);
                    return save(training);
                })
                .switchIfEmpty(Mono.empty());
    }

    //Todo preguntarle a Raul - Cómo transformar lo que me llega a un flux
    @Override
    public Flux<Aprendiz> cargarListaAprendiz() {
        return null;
    }

    @Override
    public Flux<Training> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Training> findById(String trainingId) {
        return repository.findById(trainingId);
    }

    @Override
    public Mono<Training> update(Training training, String trainingId) {
        return repository
                .findById(trainingId)
                .flatMap(training2 -> {
                    training.setTrainingId(trainingId);
                    return save(training);//preguntarle a Rauuuuuuuuuuuul
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> deleteById(String trainingId) {
        return repository.deleteById(trainingId);
    }

    @Override
    public Flux<Training> getActiveTrainings() {
        Date today = new Date();
        return repository.findAll()
                .filter(training -> today.after(training.getStartDate()))
                .filter(training -> today.before(training.getEndDate()));
    }

    @Override
    public Flux<Aprendiz> getAllAprendicesDeLosTrainingActivos() {
        return this.getActiveTrainings().flatMap(training ->
                Flux.fromIterable(training.getApprentices())
        );
    }

    @Override
    public Flux<Aprendiz> getAprendicesByTrainingId(String trainingId) {
        return this.getActiveTrainings()
                .filter(training -> training.getTrainingId().equals(trainingId))
                .flatMapIterable(Training::getApprentices);
    }


}
