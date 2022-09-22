package com.sofkau.retofinal.controllers;

import com.sofkau.retofinal.dto.ActividadDto;
import com.sofkau.retofinal.models.Actividad;
import com.sofkau.retofinal.services.ActividadServiceImpl;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/activity")
@CrossOrigin("*")
public class ControllerActividad {

    @Autowired
    ActividadServiceImpl service;

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

    @GetMapping("/findByAprendiz/{aprendizId}")
    public Flux<Actividad> findByAprendizId(@PathVariable("aprendizId") String aprendizId) {
        return service.findActivityByAprendizId(aprendizId);
    }

    @GetMapping("/find-specific/{cursoId}/{aprendizId}")
    public Flux<ActividadDto> findByCursoAndAprendiz(@PathVariable("cursoId") String cursoId, @PathVariable("aprendizId") String aprendizId) {
        return service.findAll()
                .filter(actividad -> actividad.getCursoId().equals(cursoId))
                .filter(actividad -> actividad.getAprendizId().equals(aprendizId));
    }

    @GetMapping("/agarreporid/{id}")
    public Mono<ActividadDto> getPorId(@PathVariable("id") String id){
        return service.findById(id);
    }

    @PutMapping("/updatepuntaje/{id}")
    public Mono<ActividadDto> updatePuntaje(@PathVariable("id") String id) {
        return service.updatePuntaje(AppUtils.dtoToActividad(service.findById(id).block()), 3);
    }

    @GetMapping("/aprendices/{aprendizId}")
    /**
     * Busca todas las notas por su aprendiz
     */
    public ResponseEntity<Flux<Actividad>> findAll(@PathVariable("aprendizId") String aprendizId){
        return ResponseEntity.ok()
                .body(service.findActivityByAprendizId(aprendizId));


    }
}
