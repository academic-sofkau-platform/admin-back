package com.sofkau.retofinal.controllers;

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
    public Flux<RutaAprendizaje> findAll(){
        return service.findAll();
    }

    @GetMapping("/findById/{id}")
    public Mono<RutaAprendizaje> findById(@PathVariable("id") String rutaAprendizajeId){
        return service.findById(rutaAprendizajeId);
    }

    @PostMapping("/save")
    public Mono<RutaAprendizaje> save(RutaAprendizaje rutaAprendizaje){
        return service.save(rutaAprendizaje);
    }

    @PutMapping("/update/{id}")
    public Mono<RutaAprendizaje> update(@RequestBody RutaAprendizaje rutaAprendizaje, @PathVariable("id") String rutaAprendizajeId){
        return service.update(rutaAprendizaje, rutaAprendizajeId);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteMovieById(@PathVariable("id") String rutaAprendizajeId){
        return service
                .deleteById(rutaAprendizajeId);
    }

    //RUTAS
    @PatchMapping("/add/route/{id}")
    public Mono<RutaAprendizaje> agregarRuta(@RequestBody Ruta ruta, @PathVariable("id") String rutaAprendizajeId){
        return service.addRoute(ruta, rutaAprendizajeId);
    }

}
