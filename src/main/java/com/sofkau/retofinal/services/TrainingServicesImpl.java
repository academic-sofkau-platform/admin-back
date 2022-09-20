package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.TrainingDto;
import com.sofkau.retofinal.interfaces.ITrainingService;
import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.Tarea;
import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.repositories.TrainingRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.sofkau.retofinal.utils.AppUtils.decoderBase64;


@Service
public class TrainingServicesImpl implements ITrainingService {
    @Autowired
    TrainingRepository repository;

    @Override
    public Mono<TrainingDto> save(Training training) {
        return repository.save(training).thenReturn(AppUtils.trainingToDto(training));
    }

    @Override
    public Mono<TrainingDto> asignarCoach(String coach, String trainingId) {
        return repository
                .findById(trainingId)
                .flatMap(training -> {
                    training.setTrainingId(trainingId);
                    training.setCoach(coach);
                    return save(training).thenReturn(AppUtils.trainingToDto(training));
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Flux<TrainingDto> findAll() {
        return AppUtils.trainingFluxToDto(repository.findAll());
    }

    @Override
    public Mono<TrainingDto> findById(String trainingId) {
        return repository.findById(trainingId).map(AppUtils::trainingToDto);
    }

    @Override
    public Mono<TrainingDto> update(Training training, String trainingId) {
        return repository
                .findById(trainingId)
                .flatMap(training2 -> {
                    training.setTrainingId(trainingId);
                    return save(training);
                })
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> deleteById(String trainingId) {
        return repository.deleteById(trainingId);
    }

    //  A PRUEBA
    @Override
    public Mono<Void> deleteAprendizByEmail(String trainingId, String email) {
        return repository.findById(trainingId)
                .flatMap(training -> {
                    var list = training.getApprentices().stream().filter(aprendiz -> !aprendiz.getEmail().equals(email)).collect(Collectors.toList());
                    training.setApprentices(list);
                    return repository.save(training);
                }).then();

    }
    @Override
    public Mono<TrainingDto> addtarea(String trainingId, String aprendizId, Tarea tarea){
        return  repository.findById(trainingId)
                .flatMap(training -> {training.getApprentices()
                        .forEach(aprendiz -> {
                            if (aprendiz.getId().equals(aprendizId))
                                aprendiz.getTareas().add(tarea);
                        });
                    return save(training);
                });

    }


    @Override
    public Flux<TrainingDto> getActiveTrainings() {
        Date today = new Date();
        return repository.findAll()
                .filter(training -> today.after(training.getStartDate()))
                .filter(training -> today.before(training.getEndDate()))
                .map(training -> AppUtils.trainingToDto(training));
                /*.map(trainingDto -> {
                    trainingDto.setApprenticesCount(trainingDto.getApprentices().size());
                      repository.save(AppUtils.dtoToTraining(trainingDto));
                    return trainingDto;
                });*/

    }

    @Override
    public Flux<Aprendiz> getAllAprendicesDeLosTrainingActivos() {
        return this.getActiveTrainings()
                .map(trainingDto -> AppUtils.dtoToTraining(trainingDto))
                .flatMap(training ->
                Flux.fromIterable(training.getApprentices())
        );
    }

    @Override
    public Flux<Aprendiz> getAprendicesByTrainingId(String trainingId) {
        return this.getActiveTrainings()
                .map(trainingDto -> AppUtils.dtoToTraining(trainingDto))
                .filter(training -> training.getTrainingId().equals(trainingId))
                .flatMapIterable(Training::getApprentices);
    }

    /*
    @Override
    public Flux<Aprendiz> getAllAprendicesByTrainingId(String trainingId) {
        return this.getActiveTrainings().filter(training -> training.getTrainingId().equals(trainingId))
                .flatMapIterable(TrainingDto::getApprentices);
    }
    */
    //Todo terminar
    //agregarAprendices versión 1 gian
//    @Override
//    public Mono<TrainingDto> agregarAprendices(String trainingId, List<Aprendiz> aprendizList){
//        List<Aprendiz> concatenated_list = new ArrayList<>();
//        return this.getActiveTrainings()
//                .map(trainingDto -> AppUtils.dtoToTraining(trainingDto))
//                .filter(training -> training.getTrainingId().equals(trainingId))
//                .flatMap(training -> {
//                    concatenated_list.addAll(training.getApprentices());
//                    concatenated_list.addAll(aprendizList);
//                    training.setApprentices(concatenated_list);
//                    return save(training).thenReturn(AppUtils.trainingToDto(training));
//                })
//                .next();
//    }

    //agregarAprendices versión 2
    @Override
    public Mono<TrainingDto> agregarAprendices(String trainingId, List<Aprendiz> aprendizList){
        List<Aprendiz> concatenated_list = new ArrayList<>();
        return this.findById(trainingId)
                .map(trainingDto -> AppUtils.dtoToTraining(trainingDto))
                .flatMap(training -> {
                    concatenated_list.addAll(training.getApprentices());
                    concatenated_list.addAll(aprendizList);
                    training.setApprentices(concatenated_list);
                    return repository.save(training).thenReturn(AppUtils.trainingToDto(training));
                });

    }
}

