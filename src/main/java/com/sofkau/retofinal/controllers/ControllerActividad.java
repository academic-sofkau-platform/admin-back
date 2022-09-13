package com.sofkau.retofinal.controllers;

import com.sofkau.retofinal.dto.ActividadDto;
import com.sofkau.retofinal.models.Actividad;
import com.sofkau.retofinal.services.ActividadServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/activity")
@CrossOrigin("*")
public class ControllerActividad {

    @Autowired
    private ActividadServiceImpl service;

    @PostMapping
    public Mono<ActividadDto> save(@RequestBody Actividad actividad){
        actividad.setFecha(LocalDate.parse(actividad.getFecha().toString()));
        return service.save(actividad);
    }

    @GetMapping("/find-apprentice/{aprendizId}")
    public Flux<ActividadDto> findByAprendiz(@PathVariable("aprendizId") String aprendizId) {
        return service.findAll()
                .filter(actividad -> actividad.getAprendizId().equals(aprendizId));
    }

    @GetMapping("/find-specific/{cursoId}/{aprendizId}")
    public Flux<ActividadDto> findByCursoAndAprendiz(@PathVariable("cursoId") String cursoId, @PathVariable("aprendizId") String aprendizId) {
        return service.findAll()
                .filter(actividad -> actividad.getCursoId().equals(cursoId))
                .filter(actividad -> actividad.getAprendizId().equals(aprendizId));
    }

    @PostMapping("/update/{cursoId}/{aprendizId}/{fecha}")
    public Mono<ActividadDto> addOrUpdate(@PathVariable("cursoId") String cursoId, @PathVariable("aprendizId") String aprendizId, @PathVariable("fecha") String fecha) {
       return service.addOrUpdate(3,cursoId, aprendizId, fecha);
    }
}
