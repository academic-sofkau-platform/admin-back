package com.sofkau.retofinal.services;


import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.repositories.TrainingRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrainingServicesImplTest {
    @Autowired
    private TrainingServicesImpl service;

    @MockBean
    private TrainingRepository repository;

    @Test
    void save() {
        Training training = new Training();
        training.setTrainingId("1");
        training.setName("Prueba");

        when(repository.save(training))
                .thenReturn(Mono.just(training));

        StepVerifier
                .create(service.save(training))
                .expectNextMatches(trainingDto -> trainingDto.getName().equals(training.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    void asignarCoach() {

    }

    @Test
    void cargarListaAprendiz() {

    }

    @Test
    void findAll() {

    }

    @Test
    void findById() {
        Training training = new Training();
        training.setTrainingId("1");
        training.setName("Prueba");

        when(repository.save(training))
                .thenReturn(Mono.just(training));

        when(repository.findById(training.getTrainingId()))
                .thenReturn(Mono.just(training));

        StepVerifier
                .create(service.findById(training.getTrainingId()))
                .expectNextMatches(trainingDto -> trainingDto.getName().equals(training.getName()))
                .expectComplete()
                .verify();
    }

    @Test
    void update() {

    }

    @Test
    void deleteById() {
        Training training = new Training();
        training.setTrainingId("1");
        training.setName("Prueba");

        when(repository.deleteById(training.getTrainingId()))
                .thenReturn(Mono.empty());

        StepVerifier.create(repository.deleteById(training.getTrainingId()))
                .expectNext()
                .expectComplete()
                .verify();

    }

    @Test
    void deleteAprendizByEmail() {
    }

    @Test
    void getActiveTrainings() {
    }

    @Test
    void getAllAprendicesDeLosTrainingActivos() {
    }

    @Test
    void getAprendicesByTrainingId() {
    }
}