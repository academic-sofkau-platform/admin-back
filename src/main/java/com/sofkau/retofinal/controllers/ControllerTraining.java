package com.sofkau.retofinal.controllers;

import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.RutaAprendizaje;
import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.services.TrainingServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/trainings")
public class ControllerTraining {
    //Todo control de errores del post
    //Todo Dto
    //Todo control de respuesta http
    @Autowired
    TrainingServicesImpl service;
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Training> save(@RequestBody Training training) {
        return service.save(training);
    }
    @GetMapping("/findAllTrainings")
    public Flux<Training> findAllTrainings() {
        return service.findAll();
    }
    @GetMapping("/findById/{id}")
    public Mono<Training> findById(@PathVariable("id") String trainingId) {
        return service.findById(trainingId);
    }

    //Todo Reparar
/*    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Training>> update(@RequestBody Training training,
                                                 @PathVariable("id") String trainingId) {
        return service.update(training, trainingId)
                .flatMap(training1 -> Mono.just(ResponseEntity.ok(training1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }*/
    @PutMapping("/update/{id}")
    public Mono<Training> update(@RequestBody Training training,
                                 @PathVariable("id") String trainingId){
        return service.update(training, trainingId);
    }
    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteTrainingById(@PathVariable("id") String trainingId) {
        return service.deleteById(trainingId);
    }
    @PutMapping("/update/coach/{id}")
    public Mono<Training> asignarCoach(@RequestBody String name,
                                       @PathVariable("id") String trainingId) {
        return service.asignarCoach(name, trainingId);
    }

    //TODO: findAll de aprendices
    @GetMapping("/findAllAprendices")
    public Flux<Aprendiz> findAllApredices() {
        List<Aprendiz> listaAprendices = new ArrayList<>();
        return null;
    }

    @GetMapping("/findAllTrainingActivos")
    public Flux<Training> findAllTrainingActivos() {
        return service.getActiveTrainings();
    }

    @GetMapping("/getAllAprendicesDeLosTrainingActivos")
    public Flux<Aprendiz> getAllAprendicesDeLosTrainingActivos() {
        return service.getAllAprendicesDeLosTrainingActivos();
    }

    //TODO: get all aprendices by trainingId
    @GetMapping("/aprendices/{trainingId}")
    public Flux<Aprendiz> getAllAprendicesByTrainingId(@PathVariable("trainingId") String trainingId) {
        return service.getAllAprendicesByTrainingId(trainingId);
    }
    //Todo cargarListaAprendiz csv base64 body con json o parametros

    //Todo update aprendiz
}
