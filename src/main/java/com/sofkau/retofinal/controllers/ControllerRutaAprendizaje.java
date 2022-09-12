package com.sofkau.retofinal.controllers;

import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.dto.RutaDto;
import com.sofkau.retofinal.services.RutaAprendizajeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/rutaAprendizaje")
@CrossOrigin("*")
public class ControllerRutaAprendizaje {
    @Autowired
    RutaAprendizajeServiceImpl service;

    @GetMapping("/findAll")
    public ResponseEntity<Flux<RutaAprendizajeDto>> findAll(){
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/findById/{id}")
    public Mono<ResponseEntity<RutaAprendizajeDto>> findById(@PathVariable("id") String rutaAprendizajeId){
        return service.findById(rutaAprendizajeId).map(ruta -> ResponseEntity.ok().body(ruta))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<Mono<RutaAprendizajeDto>> save(@RequestBody RutaAprendizajeDto rutaAprendizaje){
        System.out.println("hola: " + rutaAprendizaje);
        return ResponseEntity.ok().body(service.save(rutaAprendizaje));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Mono<RutaAprendizajeDto>> update(@RequestBody RutaAprendizajeDto rutaAprendizaje, @PathVariable("id") String rutaAprendizajeId){
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.update(rutaAprendizaje, rutaAprendizajeId));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<ResponseEntity<Void>> deleteMovieById(@PathVariable("id") String rutaAprendizajeId){
       return service
                .deleteById(rutaAprendizajeId)
               .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
               .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    //RUTAS
    @PatchMapping("/add/route/{id}")
    public ResponseEntity<Mono<RutaAprendizajeDto>> agregarRuta(@RequestBody RutaDto ruta, @PathVariable("id") String rutaAprendizajeId){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(service.addRoute(ruta, rutaAprendizajeId));
    }

}
