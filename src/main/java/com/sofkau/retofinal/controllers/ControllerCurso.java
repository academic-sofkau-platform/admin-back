package com.sofkau.retofinal.controllers;
import com.sofkau.retofinal.dto.CursoDto;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.services.CursoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@CrossOrigin
@RestController
@RequestMapping("/curso")
public class ControllerCurso {
    @Autowired
    private CursoServiceImpl service;

    @GetMapping
    public Flux<CursoDto> findAll() {
        return service.findAll()
                .flatMap(cursoDto -> Mono.just((cursoDto))
                .switchIfEmpty(Mono.empty()));
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<CursoDto>> register(@RequestBody Curso curso) {
        return service.save(curso)
                .flatMap(cursoDto -> Mono.just(ResponseEntity.ok(cursoDto)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/find/{id}")
    Mono<ResponseEntity<CursoDto>> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .flatMap(cursoDto -> Mono.just(ResponseEntity.ok(cursoDto)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/update/{id}")
    Mono<ResponseEntity<CursoDto>> update(@RequestBody Curso curso, @PathVariable("id") String id) {
        return service.update(curso,id)
                .flatMap(cursoDto -> Mono.just(ResponseEntity.ok(cursoDto)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id) {
        return  service.deleteById(id);
    }

}
