package com.sofkau.retofinal.controllers;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.services.CursoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/curso")
@CrossOrigin("*")
public class ControllerCurso {
    @Autowired
    private CursoServiceImpl service;

    @GetMapping
    public Flux<Curso> findAll() {
        return service.findAll();
    }

    @PostMapping("/save")
    public Mono<Curso> register(@RequestBody Curso curso) {
        return service.save(curso);
    }

    @GetMapping("/find/{id}")
    Mono<Curso> findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @PutMapping("/update/{id}")
    Mono<Curso> update(@RequestBody Curso curso, @PathVariable("id") String id) {
        return service.update(curso,id);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return  service.deleteById(id);
    }


}
