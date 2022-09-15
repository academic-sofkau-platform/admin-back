package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.dto.TrainingDto;
import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.Tareas;
import com.sofkau.retofinal.models.Training;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ITrainingService {
    Mono<TrainingDto> save(Training training);
    Mono<TrainingDto> asignarCoach(String coach, String trainingId);
    Flux<Aprendiz> cargarListaAprendiz(); //csv
    Flux<TrainingDto> findAll();
    Mono<TrainingDto> findById(String trainingId);
    Mono<TrainingDto> update(Training training, String trainingId);
    Mono<Void> deleteById(String trainingId);
    Mono<TrainingDto> addtarea(String trainingId, String aprendizId, Tareas tarea);

    Flux<TrainingDto> getActiveTrainings();

    Mono<Void> deleteAprendizByEmail(String trainingId,String email);

    Flux<Aprendiz> getAllAprendicesDeLosTrainingActivos();
    Flux<Aprendiz> getAprendicesByTrainingId(String trainingId);

   // Flux<Aprendiz> getAllAprendicesByTrainingId(String trainingId);
}
