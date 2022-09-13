package com.sofkau.retofinal.services;
import com.sofkau.retofinal.dto.CursoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class CursoServiceImplTest {

    @Autowired
    CursoServiceImpl servicio;

    @Test
    void findAll() {
        CursoDto curso = new CursoDto("f5ac2983-5521-416d-b713-b74192c576cd","Angular"
                ,"Angular es un framework para aplicaciones web desarrollado en TypeScript",75);
        Flux<CursoDto> cursoFlux = servicio.findAll();
        StepVerifier.create(cursoFlux).expectComplete();
    }

    @Test
    void save() {
        CursoDto curso = new CursoDto("f5ac2983-5521-416d-b713-b74192c576cd","Angular"
                ,"Angular es un framework para aplicaciones web desarrollado en TypeScript",75);
        Flux<CursoDto> cursoFlux = servicio.findAll();
        StepVerifier.create(cursoFlux).expectComplete();
    }

    @Test
    void update() {
        CursoDto curso = new CursoDto("f5ac2983-5521-416d-b713-b74192c576cd","Angular"
                ,"Angular es un framework para aplicaciones web desarrollado en TypeScript",75);
        Flux<CursoDto> cursoFlux = servicio.findAll();
        StepVerifier.create(cursoFlux).expectComplete();
    }

    @Test
    void delete() {
        CursoDto curso = new CursoDto("f5ac2983-5521-416d-b713-b74192c576cd","Angular"
                ,"Angular es un framework para aplicaciones web desarrollado en TypeScript",75);
        Flux<CursoDto> cursoFlux = servicio.findAll();
        StepVerifier.create(cursoFlux).expectComplete();
    }






}