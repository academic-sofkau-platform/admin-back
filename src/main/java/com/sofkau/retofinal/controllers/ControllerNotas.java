package com.sofkau.retofinal.controllers;

import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.services.DiagnosticoRendimientoServiceImpl;
import com.sofkau.retofinal.services.NotasServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/notas")
@CrossOrigin("*")
public class ControllerNotas {
    @Autowired
    private NotasServices service;


    @Autowired
    private ControllerTraining training;

    @Autowired
    private DiagnosticoRendimientoServiceImpl diagnosticoRendimientoService;



   @Scheduled(cron = "0 0 * * * *")
   public void extraerMediaNoche(){
       extraerNotas().subscribe();
       diagnosticoRendimientoService.diagnosticar(extraerNotas());

   }

    @Scheduled(cron = "0 12 * * * *")
    public void extraerMedioDia() {
        extraerNotas().subscribe();
        diagnosticoRendimientoService.diagnosticar(extraerNotas());
    }

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


    //FUNCION DE PRUEBA, BORRAR ANTES DE SUBIR
    @PostMapping("/diagnosticar")
    public void sendSimpleCorreo(){
        diagnosticoRendimientoService.diagnosticar(Flux.fromIterable(service.notas));
    }
}
