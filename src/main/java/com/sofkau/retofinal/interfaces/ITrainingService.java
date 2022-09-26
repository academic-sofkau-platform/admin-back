package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.dto.TrainingDto;
import com.sofkau.retofinal.models.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


public interface ITrainingService {
    Mono<TrainingDto> save(Training training);

    Mono<TrainingDto> asignarCoach(String coach, String trainingId);

    Flux<TrainingDto> findAll();

    Mono<TrainingDto> findById(String trainingId);

    Mono<TrainingDto> update(Training training, String trainingId);

    Mono<Void> deleteById(String trainingId);

    Mono<TrainingDto> updateNotaTarea(Tarea tarea, String trainingId, String email, String cursoId);

    Mono<TrainingDto> addtarea(String trainingId, String aprendizId, Tarea tarea);

    Flux<TrainingDto> getActiveTrainings();

    Mono<TrainingDto> deleteAprendizByEmail(String trainingId, String email);

    Flux<Aprendiz> getAllAprendicesDeLosTrainingActivos();

    Flux<Aprendiz> getAprendicesByTrainingId(String trainingId);

    Mono<Aprendiz> getAprendizByTrainingIdAndEmail(String trainingId, String emailId);

    Mono<TrainingDto> updateTarea(Tarea tarea,String trainingId,String email,String cursoId);

   public Flux<TrainingDto> getActiveTrainingComplete();


    Mono<TrainingDto> agregarAprendices(String trainingId, List<Aprendiz> aprendizList);

    Mono<TrainingDto> addTareasOfTrainingToApprentices(String trainingId);
    public Flux<ResultadoCursoList> getAprendicesParaCalificar();

    public Flux<TareasAprendiz> getAllTareasByEmail(String email, String trainingId);

}
