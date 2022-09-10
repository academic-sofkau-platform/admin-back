package com.sofkau.retofinal.controllers;


import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.services.NotasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class ControllerNotas {

    @Autowired
    private NotasServices service;

    @Autowired
    private ControllerTraining training;

    @Scheduled(cron = "0 0 * * *")
    public void extraerMediaNoche() {
        Flux<Training> trainings = training.trainingActivos();
        trainings.map(training1 -> {
            training1.getAprendices().forEach(aprendiz -> {
                Notas a = new Notas(aprendiz.getId(), training1.getTrainingId());
                Notas b = new Notas();
                b = service.findByAprendizId(aprendiz.getId());
                if (b.getAprendizId() != a.getAprendizId()){
                    service.save(a);
                }else {
                    service.update(a.getId(), a);
                }

            });
            return null;
        });
    }

    @Scheduled(cron = "0 12 * * *")
    public Flux<Notas> extraerMedioDia() {
        return null ;
    }


}
