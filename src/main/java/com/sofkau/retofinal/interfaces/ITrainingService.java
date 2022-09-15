package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.dto.TrainingDto;
import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.Tareas;
import com.sofkau.retofinal.models.Training;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITrainingService {
    Mono<Training> save(Training training);
    Mono<Training> asignarCoach(String coach, String trainingId);
    Flux<Aprendiz> cargarListaAprendiz(); //csv
    Flux<Training> findAll();
    Mono<Training> findById(String trainingId);
    Mono<Training> update(Training training, String trainingId);
    Mono<Void> deleteById(String trainingId);
    Mono<Training> addtarea(String trainingId, String aprendizId, Tareas tarea);

    Flux<TrainingDto> getActiveTrainings();

    Mono<Void> deleteAprendizByEmail(String trainingId,String email);

    Flux<Aprendiz> getAllAprendicesDeLosTrainingActivos();
    Flux<Aprendiz> getAprendicesByTrainingId(String trainingId);
}
