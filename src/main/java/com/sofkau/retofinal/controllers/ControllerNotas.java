package com.sofkau.retofinal.controllers;


import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.services.NotasServices;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;


@RestController

public class ControllerNotas {
    @Autowired
    private NotasServices service;

    @Autowired
    private ControllerActividad controllerActividad;

    @Autowired
    private ControllerTraining training;


    @Scheduled(cron = "0 0 * * *")
    public void extraerMediaNoche() {
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
