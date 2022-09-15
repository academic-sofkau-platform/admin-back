package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.ActividadDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
class ActividadServiceImplTest {

    @Autowired
    ActividadServiceImpl service;

    @Test
    void findAll(){
        ActividadDto actividadDto = new ActividadDto("TRAININGID", "APRENDIZID", "2022-09-15", 10);
        Flux<ActividadDto> actividadDtoFlux = service.findAll();
        StepVerifier.create(actividadDtoFlux).expectComplete();
    }
    @Test
    void save(){
        ActividadDto actividadDto = new ActividadDto("TRAININGID", "APRENDIZID", "2022-09-15", 10);
        Flux<ActividadDto> actividadDtoFlux = service.findAll();
        StepVerifier.create(actividadDtoFlux).expectComplete();
    }
    @Test
    void addOrUpdate(){
        ActividadDto actividadDto = new ActividadDto("TRAININGID", "APRENDIZID", "2022-09-15", 10);
        Flux<ActividadDto> actividadDtoFlux = service.findAll();
        StepVerifier.create(actividadDtoFlux).expectComplete();
    }
}
