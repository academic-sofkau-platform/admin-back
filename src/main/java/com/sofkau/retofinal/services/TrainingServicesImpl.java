package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.dto.TrainingDto;
import com.sofkau.retofinal.interfaces.ITrainingService;
import com.sofkau.retofinal.models.*;
import com.sofkau.retofinal.repositories.TrainingRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TrainingServicesImpl implements ITrainingService {
    @Autowired
    TrainingRepository repository;

    private final RutaAprendizajeServiceImpl rutaAprendizajeService;

    @Autowired
    public TrainingServicesImpl(@Lazy RutaAprendizajeServiceImpl rutaAprendizajeService) {
        this.rutaAprendizajeService = rutaAprendizajeService;
    }

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

    public Mono<TrainingDto> actualizarAprendices(List<Aprendiz> aprendizs, String trainingId) {
        return repository
                .findById(trainingId)
                .flatMap(training -> {
                    training.setTrainingId(trainingId);
                    training.setApprentices(aprendizs);
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
    public Mono<TrainingDto> updateTarea(Tarea tarea, String trainingId, String email) {
        return repository.findById(trainingId)
                .flatMap(training -> {
                    training.getApprentices()
                            .stream()
                            .filter(aprendiz -> aprendiz.getEmail().equals(email))
                            .forEach(aprendiz -> aprendiz.getTareas()
                                    .forEach(tarea1 -> {
                                        tarea1.setEntregado(tarea.getEntregado());
                                        tarea1.setContenido(tarea.getContenido());
                                    }));
                    return save(training);
                });
    }
    @Override
    public Mono<TrainingDto> updateNotaTarea(Tarea tarea, String trainingId, String email, String cursoId) {
        return repository.findById(trainingId)
                .flatMap(training -> {
                    training.getApprentices()
                            .stream()
                            .filter(aprendiz -> aprendiz.getEmail().equals(email))
                            .forEach(aprendiz -> aprendiz.getTareas()
                                    .stream().filter(tarea1 -> tarea1.getCursoId().equals(cursoId))
                                    .forEach(tarea1 -> {
                                        tarea1.setNota(tarea.getNota());
                                    }));
                    return save(training);
                });
    }

    @Override
    public Mono<TrainingDto> addtarea(String trainingId, String aprendizId, Tarea tarea) {
        return repository.findById(trainingId)
                .flatMap(training -> {
                    training.getApprentices()
                            .forEach(aprendiz -> {
                                if (aprendiz.getEmail().equals(aprendizId))
                                    aprendiz.getTareas().add(tarea);

                            });
                    return save(training);
                });
    }
    @Override
    public Mono<TrainingDto> addTareasOfTrainingToApprentices(String trainingId) {

        return this.findById(trainingId)
                .flatMap(trainingDto -> {
                    return this.rutaAprendizajeService.findById(trainingDto.getRutaAprendizajeId())
                            .map(RutaAprendizajeDto::getRutas)
                            .flatMap(ruta -> {
                                trainingDto.getApprentices().forEach(aprendiz -> aprendiz.getTareas().add(new Tarea(ruta.get(0).getCursoId(), "A", "B", "C")));
                                return this.save(AppUtils.dtoToTraining(trainingDto));
                            });
                });
    }




    @Override
    public Flux<TrainingDto> getActiveTrainings() {
        Date today = new Date();
        return repository.findAll()
                .filter(training -> today.after(training.getStartDate()))
                .filter(training -> today.before(training.getEndDate()))
                .map(training -> AppUtils.trainingToDto(training));
    }

    //Método para devolver la vista training activos
    @Override
    public Flux<TrainingDto> getActiveTrainingComplete(){
        String strDateFormat ="MMM";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        return getActiveTrainings()
                .map(trainingDto -> {
                    trainingDto.setApprenticesCount(trainingDto.getApprentices().size());
                    trainingDto.setPeriod(sdf.format(trainingDto.getStartDate()) + " - " + sdf.format(trainingDto.getEndDate()));
                    repository.save((AppUtils.dtoToTraining(trainingDto)));
                    return trainingDto;
                });
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

    @Override
    public Mono<Aprendiz> getAprendizByTrainingIdAndEmail(String trainingId, String emailId) {
        return this.getActiveTrainings()
                .map(AppUtils::dtoToTraining)
                .filter(training -> training.getTrainingId().equals(trainingId))
                .flatMapIterable(Training::getApprentices)
                .filter(aprendiz -> aprendiz.getEmail().equals(emailId))
                .next();
    }

    /*
    @Override
    public Flux<Aprendiz> getAllAprendicesByTrainingId(String trainingId) {
        return this.getActiveTrainings().filter(training -> training.getTrainingId().equals(trainingId))
                .flatMapIterable(TrainingDto::getApprentices);
    }
    */

    @Override
    public Flux<ResultadoCursoList> getResultadoCursos() {
        return this.getActiveTrainings()
                .flatMapIterable(trainingDto -> trainingDto
                        .getApprentices()
                        .stream()
                        .map(aprendiz -> new ResultadoCursoList(aprendiz,
                                trainingDto.getName(),
                                null,
                                trainingDto.getRutaAprendizajeId()))
                        .collect(Collectors.toUnmodifiableList())
                ).flatMap(resultadoCursoList -> {
                    return this.rutaAprendizajeService
                            .findCursosByRutaAprendizajeId(resultadoCursoList.getRutaAprendizajeId())
                            .map(curso -> {
                                return new ResultadoCursoList(resultadoCursoList.getAprendiz(), resultadoCursoList.getTrainingName(), curso, resultadoCursoList.getRutaAprendizajeId());
                            });
                });

    }
    @Override
    public Mono<TrainingDto> agregarAprendices(String trainingId, List<Aprendiz> aprendizList) {
        List<Aprendiz> concatenated_list = new ArrayList<>();
        return this.findById(trainingId)
                .map(AppUtils::dtoToTraining)
                .flatMap(training -> {
                    var newList = aprendizList.stream()
                            .filter(aprendiz -> !aprendiz.getName().equals(""))
                            .filter(aprendiz -> !aprendiz.getEmail().equals(""))
                            .collect(Collectors.toList());

                    concatenated_list.addAll(training.getApprentices());
                    concatenated_list.addAll(newList);
                    training.setApprentices(concatenated_list);
                    return repository.save(training)
                            .thenReturn(AppUtils.trainingToDto(training));
                });
    }


}

