package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.CursoDto;
import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.models.Tareas;
import com.sofkau.retofinal.repositories.NotasRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class NotasServicesTest {

    @Autowired
    private NotasServices notasServices;

    @MockBean
    private NotasRepository notasRepository;

    @Test
    void save() {
        List<Tareas> actividades1 = new ArrayList<>();
        Notas n= new Notas("1555de12", "7595fb82-db54-490b-91fc-ec0c8e7daaa1", actividades1);
        Notas n2= new Notas("1555de12", "7595fb82-db54-490b-91fc-ec0c8e7daaa1", actividades1);

        when(notasRepository.save(n2)).thenReturn(Mono.just(n2));

        Mono<Notas> notas = notasServices.save(n2);

        StepVerifier.create(notas)
                .expectNext(n)
                .expectComplete()
                .verify();
    }

    @Test
    void findAll() {
        List<Tareas> actividades1 = new ArrayList<>();
        Notas n2= new Notas("1", "7", actividades1);

        when(notasRepository.findAll()).thenReturn(Flux.just(n2));

        Flux<Notas> notas = notasServices.findAll();

        StepVerifier.create(notas)
                .expectNextMatches(notas1 -> notas1.getAprendizId().equals(n2.getAprendizId()) &&
                        notas1.getTrainingId().equals(n2.getTrainingId())&&
                        notas1.getTareasList().equals(n2.getTareasList()))
                .expectComplete()
                .verify();
    }

    @Test
    void findByAprendizId() {
        List<Tareas> actividades1 = new ArrayList<>();
        Notas n2= new Notas("1", "7", actividades1);

        when(notasRepository.findById("1")).thenReturn(Mono.just(n2));

        Mono<Notas> notas = notasServices.findByAprendizId("1");

        StepVerifier.create(notas)
                .expectNextMatches(notas1 -> notas1.getAprendizId().equals(n2.getAprendizId()) &&
                        notas1.getTrainingId().equals(n2.getTrainingId())&&
                        notas1.getTareasList().equals(n2.getTareasList()))
                .expectComplete()
                .verify();
    }

    @Test
    void findByAprendizIdAndTrainingId() {
        List<Tareas> actividades1 = new ArrayList<>();
        Notas n2= new Notas("1", "7", actividades1);

        when(notasRepository.findAll()).thenReturn(Flux.just(n2));

        Mono<Notas> notas = notasServices.findByAprendizIdAndTrainingId("1", "7");

        StepVerifier.create(notas)
                .expectNextMatches(notas1 -> notas1.getAprendizId().equals(n2.getAprendizId()) &&
                        notas1.getTrainingId().equals(n2.getTrainingId())&&
                        notas1.getTareasList().equals(n2.getTareasList()))
                .expectComplete()
                .verify();
    }
}