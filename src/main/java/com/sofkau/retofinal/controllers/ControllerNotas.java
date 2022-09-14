package com.sofkau.retofinal.controllers;

import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.services.ActividadServiceImpl;
import com.sofkau.retofinal.services.DiagnosticoRendimientoServiceImpl;
import com.sofkau.retofinal.services.NotasServices;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

    @Autowired
    private DiagnosticoRendimientoServiceImpl diagnosticoRendimientoService;

    //@Scheduled(cron = "0 0 * * *")
    @PostMapping
    public Flux<Notas> extraerMediaNoche() {

        return training.findAllTrainingActivos()
                .flatMap(training1 -> Flux.fromIterable(training1.getApprentices())
                        .map(aprendiz -> {
                            Notas nota= new Notas();
                            nota.setAprendizId(aprendiz.getId());
                            nota.setTrainingId(training1.getTrainingId());
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
                                nota.setTrainingId(training1.getTrainingId());
                                controllerActividad.findByAprendiz(aprendiz.getId()).collectList().block()
                                        .forEach(actividad -> {
                                            nota.getActividadList().add(AppUtils.dtoToActividad(actividad));
                                        });
                                service.save(nota);
                            });
                    return null;


                });
    }

    //FUNCION DE PRUEBA, BORRAR ANTES DE SUBIR
    @PostMapping("/diagnosticar")
    public void sendSimpleCorreo(){
        diagnosticoRendimientoService.diagnosticar(Flux.fromIterable(service.notas));
    }
}
