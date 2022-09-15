package com.sofkau.retofinal.controllers;



import com.sofkau.retofinal.models.*;
import com.sofkau.retofinal.services.ActividadServiceImpl;
import com.sofkau.retofinal.services.NotasServices;
import com.sofkau.retofinal.services.TrainingServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/notas")
@CrossOrigin("*")
public class ControllerNotas {

    private Flux<Notas> notas;

    @Autowired
    private NotasServices service;

    @Autowired
    private ActividadServiceImpl actividadService;
    @Autowired
    private ControllerActividad controllerActividad;

    @Autowired
    private ControllerTraining training;

    @Autowired
    TrainingServicesImpl trainingServices;



   /*@Scheduled(cron = "20 * * * * *")
   public void extraerMediaNoche(){
       extraerNotas();
   }

    @Scheduled(cron = "0 12 * * *")
    public void extraerMedioDia() {
        extraerNotas();
    }*/

    @GetMapping
    @PostMapping
    @PutMapping
    public Flux<Notas> extraerNotas() {
        System.out.println("se esta ejecutando media noche");
        return training.findAllTrainingActivos()
                .flatMap(training1 -> Flux.fromIterable(training1.getApprentices())
                    .flatMap(aprendiz -> service.save(new Notas(aprendiz.getId(), training1.getTrainingId(), aprendiz.getTareas()))
                    )
                );
    }




}
