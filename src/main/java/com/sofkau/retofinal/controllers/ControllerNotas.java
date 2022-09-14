package com.sofkau.retofinal.controllers;


import com.sofkau.retofinal.dto.ActividadDto;
import com.sofkau.retofinal.models.Actividad;
import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.services.NotasServices;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


@RestController

@RequestMapping("/notas")
@CrossOrigin("*")
public class ControllerNotas {

    private Flux<Notas> notas;

    @Autowired
    private NotasServices service;

    @Autowired
    private ControllerActividad controllerActividad;

    @Autowired
    private ControllerTraining training;


    //@Scheduled(cron = "0 0 * * *")
    @PostMapping
    public Flux<Notas> extraerMediaNoche() {

        return training.findAllTrainingActivos()
                .flatMap(training1 -> Flux.fromIterable(training1.getApprentices())
                        .map(aprendiz -> {
                            Notas nota= new Notas();
                            nota.setAprendizId(aprendiz.getId());
                            nota.setTrainingI(training1.getTrainingId());
                            controllerActividad.findByAprendiz(aprendiz.getId())
                                    .map(actividadDto -> nota.getActividadList().add(AppUtils.dtoToActividad(actividadDto)));
                            service.save(nota);
                          return nota;
                        })
                );

    }


    @Scheduled(cron = "0 12 * * *")
    public void extraerMedioDia() {
        training.findAllTrainingActivos()
                .map(training1 -> {
                    training1.getApprentices()
                            .forEach(aprendiz -> {
                                Notas nota= new Notas();
                                nota.setAprendizId(aprendiz.getId());
                                nota.setTrainingI(training1.getTrainingId());
                                controllerActividad.findByAprendiz(aprendiz.getId()).collectList().block()
                                        .forEach(actividad -> {

                                            nota.getActividadList().add(AppUtils.dtoToActividad(actividad));
                                        });
                                service.save(nota);
                            });
                    return null;


                });
    }

}
