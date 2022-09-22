package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.dto.TrainingDto;
import com.sofkau.retofinal.interfaces.ITrainingService;
import com.sofkau.retofinal.models.*;
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
import java.util.stream.Collectors;

import static com.sofkau.retofinal.utils.AppUtils.decoderBase64;


@Service
public class TrainingServicesImpl implements ITrainingService {
    @Autowired
    TrainingRepository repository;
    @Autowired
    private RutaAprendizajeServiceImpl rutaAprendizajeService;


    @Autowired
    private NotasServices notasServices;

    @Autowired
    private CursoServiceImpl cursoService;


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

    public Mono<TrainingDto> actualizarAprendices(List<Aprendiz> aprendizs, String trainingId){
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
    public Flux<TrainingDto> getActiveTrainings() {
        Date today = new Date();
        return repository.findAll()
                .filter(training -> today.after(training.getStartDate()))
                .filter(training -> today.before(training.getEndDate()))
                .map(training -> AppUtils.trainingToDto(training));
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
        List<ResultadoCursoList> resultadoCursoLists = new ArrayList<>();

        return this.getActiveTrainings()
                .flatMap(trainingDto -> {
                    var rutaAprendizaje = rutaAprendizajeService.findAll().filter(rutaAprendizajeDto -> rutaAprendizajeDto.getId().equals(trainingDto.getRutaAprendizajeId()));
                    rutaAprendizaje.map(rutaAprendizajeDto -> rutaAprendizajeDto.getRutas()
                            .stream()
                            .map(rutita ->
                                    resultadoCursoLists.add(
                                            new ResultadoCursoList(trainingDto.getApprentices(), AppUtils.dtoToTraining(trainingDto).getName(),
                                                    cursoService.findAll().filter(cursoDto -> cursoDto.getId().equals(rutita.getCursoId())).map(AppUtils::dtoToCurso).collectList().block()))));
                    return Flux.fromIterable(resultadoCursoLists);
                });


      /**  return this.getActiveTrainings()
                .flatMap(trainingDto -> {
                    System.out.println("segundo sout");
                    var rutaAprendizaje = rutaAprendizajeService.findById(trainingDto.getRutaAprendizajeId());
                    rutaAprendizaje.map(RutaAprendizajeDto::getRutas).map(rutita ->
                            trainingDto.getApprentices()
                                    .stream()
                                    .map(aprendiz -> resultadoCursoLists.add(
                                            new ResultadoCursoList(aprendiz,
                                                    AppUtils.dtoToTraining(trainingDto),
                                                    new Curso()))
                                    ));
                    return Flux.fromIterable(resultadoCursoLists);
                });*/

    }



    @Override
    public Mono<TrainingDto> agregarAprendices(String trainingId, List<Aprendiz> aprendizList){
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

