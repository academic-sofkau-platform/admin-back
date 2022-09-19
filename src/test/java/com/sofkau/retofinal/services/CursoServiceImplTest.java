package com.sofkau.retofinal.services;
import com.sofkau.retofinal.dto.CursoDto;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.repositories.CursoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@SpringBootTest
class CursoServiceImplTest {

    @Autowired
    private CursoServiceImpl servicio;

    @MockBean
    private CursoRepository repository;

    @Test
    void findAll() {
        Flux<Curso> simulado = Flux.just( new Curso("f5ac2983-5521-416d-b713-b74192c576cd","Angular"
                ,"Angular es un framework para aplicaciones web desarrollado en TypeScript",75,"",""));

        when(repository.findAll()).thenReturn(simulado);

        Flux<CursoDto> cursoFlux = servicio.findAll();

        StepVerifier.create(cursoFlux)
                .expectNextMatches(curso1 ->
                        curso1.getId().equals("f5ac2983-5521-416d-b713-b74192c576cd") &&
                                curso1.getNombre().equals("Angular") &&
                                curso1.getDescripcion().equals("Angular es un framework para aplicaciones web desarrollado en TypeScript") &&
                                curso1.getAprobacion().equals(75))
                .expectComplete()
                .verify();
    }

    @Test
    void save() {
        CursoDto curso = new CursoDto("f5ac2983-5521-416d-b713-b74192c576cd","Angular"
                ,"Angular es un framework para aplicaciones web desarrollado en TypeScript",75,"","");

        Curso curso1 = new Curso("f5ac2983-5521-416d-b713-b74192c576cd","Angular"
                ,"Angular es un framework para aplicaciones web desarrollado en TypeScript", 75, "","");

        when(repository.save(curso1)).thenReturn(Mono.just(curso1));

        Mono<CursoDto> saveCurso = servicio.save(curso1);

        StepVerifier.create(saveCurso)
                .expectNext(curso)
                .expectComplete()
                .verify();
    }

    @Test
    void update() {
        Curso curso1 = new Curso("f5ac2983-5521-416d-b713-b74192c576cd","Angular"
                ,"Angular es un framework para aplicaciones web desarrollado en TypeScript",75,"","");

        Curso curso2 = new Curso("f5ac2983-5521-416d-b713-b74192c576cd","React"
                ,"React es un framework",75,"","");

        when(repository.findById(curso1.getId())).thenReturn(Mono.just(curso1));
        when(repository.save(curso1)).thenReturn(Mono.just(curso1));

        StepVerifier.create(servicio.update(curso2,curso1.getId()))
                .expectNextMatches(curso3 ->
                        curso3.getId().equals("f5ac2983-5521-416d-b713-b74192c576cd") &&
                                curso3.getNombre().equals("React") &&
                                curso3.getDescripcion().equals("React es un framework") &&
                                curso3.getAprobacion().equals(75))
                .expectComplete()
                .verify();
    }

    @Test
    void delete() {
        CursoDto curso = new CursoDto("f5ac2983-5521-416d-b713-b74192c576cd","Angular"
                ,"Angular es un framework para aplicaciones web desarrollado en TypeScript",75,"","");

        Curso curso1 = new Curso("f5ac2983-5521-416d-b713-b74192c576cd","Angular"
                ,"Angular es un framework para aplicaciones web desarrollado en TypeScript",75,"","");

        when(repository.deleteById(curso1.getId())).thenReturn(Mono.empty());

        StepVerifier.create(servicio.deleteById(curso1.getId()))
                .expectNext()
                .expectComplete()
                .verify();
    }

}