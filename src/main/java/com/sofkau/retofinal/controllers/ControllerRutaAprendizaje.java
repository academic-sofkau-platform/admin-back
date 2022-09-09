package com.sofkau.retofinal.controllers;

import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.dto.RutaDto;
import com.sofkau.retofinal.models.Ruta;
import com.sofkau.retofinal.models.RutaAprendizaje;
import com.sofkau.retofinal.services.RutaAprendizajeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rutaAprendizaje")
public class ControllerRutaAprendizaje {
    @Autowired
    RutaAprendizajeServiceImpl service;

    @GetMapping("/findAll")
    public Flux<RutaAprendizajeDto> findAll(){
        return service.findAll();
    }

    @GetMapping("/findById/{id}")
    public Mono<RutaAprendizajeDto> findById(@PathVariable("id") String rutaAprendizajeId){
        return service.findById(rutaAprendizajeId);
    }

    @PostMapping("/save")
    public Mono<RutaAprendizajeDto> save(@RequestBody RutaAprendizajeDto rutaAprendizaje){
        System.out.println(rutaAprendizaje);
        return service.save(rutaAprendizaje);
    }

    @PutMapping("/update/{id}")
    public Mono<RutaAprendizajeDto> update(@RequestBody RutaAprendizajeDto rutaAprendizaje, @PathVariable("id") String rutaAprendizajeId){
        return service.update(rutaAprendizaje, rutaAprendizajeId);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteMovieById(@PathVariable("id") String rutaAprendizajeId){
        return service
                .deleteById(rutaAprendizajeId);
    }

    //RUTAS
    @PatchMapping("/add/route/{id}")
    public Mono<RutaAprendizajeDto> agregarRuta(@RequestBody RutaDto ruta, @PathVariable("id") String rutaAprendizajeId){
        return service.addRoute(ruta, rutaAprendizajeId);
    }

}
