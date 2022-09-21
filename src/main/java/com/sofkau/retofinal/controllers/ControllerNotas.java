package com.sofkau.retofinal.controllers;

import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.services.DiagnosticoRendimientoServiceImpl;
import com.sofkau.retofinal.services.NotasServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


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
//       diagnosticoRendimientoService.diagnosticar(extraerNotas());

   }

    @Scheduled(cron = "0 12 * * * *")
    public void extraerMedioDia() {
        extraerNotas().subscribe();
//        diagnosticoRendimientoService.diagnosticar(extraerNotas());
    }

    @GetMapping
    @PostMapping
    @PutMapping
    public Flux<Notas> extraerNotas() {
        System.out.println("se esta ejecutando media noche");
        return training.findAllTrainingActivos()
                .flatMap(training1 -> Flux.fromIterable(training1.getApprentices())
                    .flatMap(aprendiz -> service.save(new Notas(aprendiz.getEmail(), training1.getTrainingId(), aprendiz.getTareas()))
                    )
                );
    }

    @PostMapping("/diagnosticar")
    public void diagnosticar(){
       diagnosticoRendimientoService.diagnosticar(service.findAll());
    }
    @GetMapping("/accionesMejora/{aprendizId}")
    public ResponseEntity<List<String>> accionesMejoraDeAlumno(@PathVariable("aprendizId") String aprendizId){
       var res = service.getAccionMejora(aprendizId).collectList().block();
        return ResponseEntity.ok()
                .body(res);
    }
}
