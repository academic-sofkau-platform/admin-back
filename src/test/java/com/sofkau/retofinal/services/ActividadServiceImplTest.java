package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.Actividad;
import com.sofkau.retofinal.repositories.ActividadRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class ActividadServiceImplTest {

    @MockBean
    ActividadRepository repository;
    @Autowired
    ActividadServiceImpl service;

    @Test
    void save() {
        LocalDate date = LocalDate.of(2022, 9, 22);
        Actividad actividad = new Actividad("cursoId1", "aprendizId1", date, 76, "tipo", 75);

        when(repository.save(actividad))
                .thenReturn(Mono.just(actividad));

        StepVerifier
                .create(service.save(actividad))
                .expectNextMatches(actividadDto ->
                        actividadDto.getCursoId().equals("cursoId1") &&
                                actividadDto.getAprendizId().equals("aprendizId1") &&
                                actividadDto.getFecha().equals("2022-09-22") &&
                                actividadDto.getPuntaje().equals(76) &&
                                actividadDto.getTipo().equals("tipo") &&
                                actividadDto.getNota().equals(75)
                )
                .expectComplete()
                .verify();
    }

    @Test
    void findAll() {
    }

    @Test
    void addOrUpdate() {
    }

    @Test
    void findActivityByAprendizId() {
    }
}