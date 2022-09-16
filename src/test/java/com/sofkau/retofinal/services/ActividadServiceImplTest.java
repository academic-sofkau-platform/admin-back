package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.Actividad;
import com.sofkau.retofinal.repositories.ActividadRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;

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
        Actividad actividad = new Actividad("cursoId1", "aprendizId1", date, 76);

        when(repository.save(actividad))
                .thenReturn(Mono.just(actividad));

        StepVerifier
                .create(service.save(actividad))
                .expectNextMatches(actividadDto ->
                        actividadDto.getCursoId().equals("cursoId1") &&
                                actividadDto.getAprendizId().equals("aprendizId1") &&
                                actividadDto.getFecha().equals("2022-09-22") &&
                                actividadDto.getPuntaje().equals(76)
                )
                .expectComplete()
                .verify();
    }

    @Test
    void findAll() {
        LocalDate date1 = LocalDate.of(2022, 9, 22);
        Actividad actividad1 = new Actividad("cursoId1", "aprendizId1", date1, 76);
        LocalDate date2 = LocalDate.of(2022, 8, 11);
        Actividad actividad2 = new Actividad("cursoId2", "aprendizId2", date2, 78);

        when(repository.findAll())
                .thenReturn(Flux.just(actividad1, actividad2));

        StepVerifier
                .create(service.findAll())
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    void updatePuntaje() {
        LocalDate date1 = LocalDate.of(2022, 9, 22);
        Actividad actividad1 = new Actividad("XXX-YYY","cursoId1", "aprendizId1", date1, 3);

        when(repository.findById(actividad1.getActividadId())).thenReturn(Mono.just(actividad1));
        when(repository.save(actividad1)).thenReturn(Mono.just(actividad1));

        StepVerifier.create(service.updatePuntaje(actividad1, 3))
                .expectNextMatches(newAct ->
                        newAct.getActividadId().equals("XXX-YYY") &&
                                newAct.getCursoId().equals("cursoId1") &&
                                newAct.getAprendizId().equals("aprendizId1") &&
                                newAct.getFecha().equals("2022-09-22") &&
                                newAct.getPuntaje().equals(6)
                        )
                .expectComplete()
                .verify();
    }
}
