package com.sofkau.retofinal.controllers;

import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.services.TrainingServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/trainings")
public class ControllerTraining {
    @Autowired
    TrainingServicesImpl service;
    @PostMapping("/save")
    public Mono<Training> save(Training training) {
        return service.save(training);
    }
    @GetMapping("/findAll")
    public Flux<Training> findAll() {
        return service.findAll();
    }
    @GetMapping("/findById/{id}")
    public Mono<Training> findById(@PathVariable("id") String trainingId) {
        return service.findById(trainingId);
    }
    @PutMapping("/update/{id}")
    public Mono<Training> update(@RequestBody Training training,
                                 @PathVariable("id") String trainingId) {
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
    //Todo cargarListaAprendiz
}
