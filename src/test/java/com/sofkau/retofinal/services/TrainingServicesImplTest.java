package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.*;
import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.repositories.TrainingRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import reactor.core.publisher.Mono;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrainingServicesImplTest {
    @Autowired
    private TrainingServicesImpl service;

    @MockBean
    private TrainingRepository repository;

    @Test
    void save() throws ParseException {
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        List<Aprendiz> listAprendices = new ArrayList<>();

        Training training = new Training();
        training.setTrainingId("1");
        training.setName("Prueba");
        training.setDescription("descripción de prueba");
        training.setStartDate(DateFor.parse("2022-09-15"));
        training.setEndDate(DateFor.parse("2022-12-15"));
        training.setCoach("Eddi amigo del Joaco");
        training.setApprentices(listAprendices);
        training.setRutaAprendizajeId("ruta1");

        when(repository.save(training))
                .thenReturn(Mono.just(training));

        StepVerifier
                .create(service.save(training))
                .expectNextMatches(trainingDto ->
                        {
                            try {
                                return trainingDto.getTrainingId().equals("1") &&
                                        trainingDto.getName().equals("Prueba") &&
                                        trainingDto.getDescription().equals("descripción de prueba") &&
                                        trainingDto.getStartDate().equals(DateFor.parse("2022-09-15")) &&
                                        trainingDto.getEndDate().equals(DateFor.parse("2022-12-15")) &&
                                        trainingDto.getCoach().equals("Eddi amigo del Joaco") &&
                                        trainingDto.getApprentices().equals(listAprendices) &&
                                        trainingDto.getRutaAprendizajeId().equals("ruta1");
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .expectComplete()
                .verify();
    }

    @Test
    void asignarCoach() {
        Training training = new Training();
        training.setTrainingId("1");
        training.setName("Prueba");

        when(repository.save(training))
                .thenReturn(Mono.just(training));

        when(repository.findById(training.getTrainingId()))
                .thenReturn(Mono.just(training));

        StepVerifier
                .create(service.asignarCoach("Raul", training.getTrainingId()))
                .expectNextMatches(trainingDto -> trainingDto.getCoach().equals("Raul"))
                .expectComplete()
                .verify();
    }


    @Test
    void findAll() throws ParseException {

        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = DateFor.parse("2022-09-15");
        Date date2 = DateFor.parse("2022-09-30");
        Date date3 = DateFor.parse("2022-08-15");
        Date date4 = DateFor.parse("2022-10-30");
        List<Aprendiz> aprendices1 = new ArrayList<>();
        List<Aprendiz> aprendices2 = new ArrayList<>();

        Training training1 = new Training();
        training1.setCoach("Eddie");
        training1.setDescription("Doman Driven Design");
        training1.setStartDate(date1);
        training1.setEndDate(date2);
        training1.setName("DDD");
        training1.setRutaAprendizajeId("ruta1");
        training1.setTrainingId("123");
        training1.setApprentices(aprendices1);

        Training training2 = new Training();
        training2.setCoach("Raul");
        training2.setDescription("Introducción a la programación");
        training2.setStartDate(date3);
        training2.setEndDate(date4);
        training2.setName("IalP");
        training2.setRutaAprendizajeId("ruta2");
        training2.setTrainingId("321");
        training2.setApprentices(aprendices2);

        when(repository.findAll())
                .thenReturn(Flux.just(training1, training2));

        StepVerifier
                .create(service.findAll())
                .expectNextMatches(trainingDto ->
                        trainingDto.getName().equals("DDD"))
                .expectNextMatches(trainingDto ->
                        trainingDto.getName().equals("IalP"))
                .expectComplete()
                .verify();
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
    void update() throws ParseException {
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = DateFor.parse("2022-09-15");
        Date date2 = DateFor.parse("2022-09-30");
        Date date3 = DateFor.parse("2022-08-15");
        Date date4 = DateFor.parse("2022-10-30");
        List<Aprendiz> aprendices1 = new ArrayList<>();
        List<Aprendiz> aprendices2 = new ArrayList<>();

        Training training1 = new Training();
        training1.setCoach("Eddie");
        training1.setDescription("Doman Driven Design");
        training1.setStartDate(date1);
        training1.setEndDate(date2);
        training1.setName("DDD");
        training1.setRutaAprendizajeId("ruta1");
        training1.setTrainingId("123");
        training1.setApprentices(aprendices1);

        Training training2 = new Training();
        training2.setCoach("Raul");
        training2.setDescription("Introducción a la programación");
        training2.setStartDate(date3);
        training2.setEndDate(date4);
        training2.setName("IalP");
        training2.setRutaAprendizajeId("ruta2");
        training2.setTrainingId("321");
        training2.setApprentices(aprendices2);

        when(repository.save(training1))
                .thenReturn(Mono.just(training1));

        when(repository.findById(training1.getTrainingId()))
                .thenReturn(Mono.just(training1));

        when(repository.save(training2))
                .thenReturn(Mono.just(training2));

        StepVerifier
                .create(service.update(training2, training1.getTrainingId()))
                .expectNextMatches(trainingDto ->
                        trainingDto.getName().equals("IalP") &&
                                trainingDto.getCoach().equals("Raul"))
                .expectComplete()
                .verify();
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
    void deleteAprendizByEmail() throws ParseException {
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = DateFor.parse("2022-09-15");
        Date date2 = DateFor.parse("2022-09-30");
        List<Aprendiz> aprendices1 = new ArrayList<>();
        aprendices1.add(new Aprendiz("fabri@gmail.com",
                "Fabri",
                "Rancio",
                "Las Vegas",
                "Femenino",
                "1234",
                "xd.png",
                null,
                true,
                null));

        aprendices1.add(new Aprendiz("queinteresante@gmail.com",
                "Matias",
                "Souza",
                "Ranciolandia",
                "Masculino",
                "12345",
                "elmati.png",
                null,
                false,
                null));

        Training training1 = new Training();
        training1.setCoach("Eddie");
        training1.setDescription("Doman Driven Design");
        training1.setStartDate(date1);
        training1.setEndDate(date2);
        training1.setName("DDD");
        training1.setRutaAprendizajeId("ruta1");
        training1.setTrainingId("123");
        training1.setApprentices(aprendices1);

        when(repository.save(training1))
                .thenReturn(Mono.just(training1));

        when(repository.findById(training1.getTrainingId()))
                .thenReturn(Mono.just(training1));

        StepVerifier
                .create(service.deleteAprendizByEmail(training1.getTrainingId(), "fabri@gmail.com"))
                .expectNextMatches(trainingDto -> trainingDto.getTrainingId().equals(training1.getTrainingId()))
                .expectComplete()
                .verify();
    }

    @Test
    void getActiveTrainings() throws ParseException {
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = DateFor.parse("2022-09-15");
        Date date2 = DateFor.parse("2022-09-30");
        Date date3 = DateFor.parse("2021-08-15");
        Date date4 = DateFor.parse("2021-10-30");
        List<Aprendiz> aprendices1 = new ArrayList<>();
        List<Aprendiz> aprendices2 = new ArrayList<>();

        Training training1 = new Training();
        training1.setCoach("Eddie");
        training1.setDescription("Doman Driven Design");
        training1.setStartDate(date1);
        training1.setEndDate(date2);
        training1.setName("DDD");
        training1.setRutaAprendizajeId("ruta1");
        training1.setTrainingId("123");
        training1.setApprentices(aprendices1);

        Training training2 = new Training();
        training2.setCoach("Raul");
        training2.setDescription("Introducción a la programación");
        training2.setStartDate(date3);
        training2.setEndDate(date4);
        training2.setName("IalP");
        training2.setRutaAprendizajeId("ruta2");
        training2.setTrainingId("321");
        training2.setApprentices(aprendices2);

        when(repository.save(training1))
                .thenReturn(Mono.just(training1));

        when(repository.findAll())
                .thenReturn(Flux.just(training1, training2));

        StepVerifier
                .create(service.getActiveTrainings())
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void getAllAprendicesDeLosTrainingActivos() throws ParseException{
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = DateFor.parse("2022-09-15");
        Date date2 = DateFor.parse("2022-09-30");
        Date date3 = DateFor.parse("2021-09-15");
        Date date4 = DateFor.parse("2021-09-30");
        List<Aprendiz> aprendices1 = new ArrayList<>();
        List<Aprendiz> aprendices2 = new ArrayList<>();
        aprendices1.add(new Aprendiz("fabri@gmail.com",
                "Fabri",
                "Rancio",
                "Las Vegas",
                "Femenino",
                "1234",
                "xd.png",
                null,
                true,
                null));

        aprendices1.add(new Aprendiz("queinteresante@gmail.com",
                "Matias",
                "Souza",
                "Ranciolandia",
                "Masculino",
                "12345",
                "elmati.png",
                null,
                false,
                null));

        aprendices2.add(new Aprendiz("nicodrilo@gmail.com",
                "Nico",
                "Drilo",
                "Las Vegas",
                "Femenino",
                "1234",
                "xd.png",
                null,
                true,
                null));

        aprendices2.add(new Aprendiz("luchoca@gmail.com",
                "Lucho",
                "Ca",
                "Ranciolandia",
                "Masculino",
                "12345",
                "ellucho.png",
                null,
                false,
                null));

        Training training1 = new Training();
        training1.setCoach("Eddie");
        training1.setDescription("Doman Driven Design");
        training1.setStartDate(date3);
        training1.setEndDate(date4);
        training1.setName("DDD");
        training1.setRutaAprendizajeId("ruta1");
        training1.setTrainingId("123");
        training1.setApprentices(aprendices1);

        Training training2 = new Training();
        training2.setCoach("Raul");
        training2.setDescription("Introducción a la programación");
        training2.setStartDate(date1);
        training2.setEndDate(date2);
        training2.setName("IalP");
        training2.setRutaAprendizajeId("ruta2");
        training2.setTrainingId("321");
        training2.setApprentices(aprendices2);

        when(repository.save(training1))
                .thenReturn(Mono.just(training1));

        when(repository.findById(training1.getTrainingId()))
                .thenReturn(Mono.just(training1));

        when(repository.findAll()).thenReturn(Flux.just(training1, training2));

        StepVerifier
                .create(service.getAllAprendicesDeLosTrainingActivos())
                .expectNextCount(2)
                .expectComplete()
                .verify();

    }

    @Test
    void getAprendicesByTrainingId() throws ParseException {
        SimpleDateFormat DateFor = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = DateFor.parse("2022-09-15");
        Date date2 = DateFor.parse("2022-09-30");
        List<Aprendiz> aprendices1 = new ArrayList<>();
        aprendices1.add(new Aprendiz("fabri@gmail.com",
                "Fabri",
                "Rancio",
                "Las Vegas",
                "Femenino",
                "1234",
                "xd.png",
                null,
                true,
                null));

        aprendices1.add(new Aprendiz("queinteresante@gmail.com",
                "Matias",
                "Souza",
                "Ranciolandia",
                "Masculino",
                "12345",
                "elmati.png",
                null,
                false,
                null));

        Training training1 = new Training();
        training1.setCoach("Eddie");
        training1.setDescription("Doman Driven Design");
        training1.setStartDate(date1);
        training1.setEndDate(date2);
        training1.setName("DDD");
        training1.setRutaAprendizajeId("ruta1");
        training1.setTrainingId("123");
        training1.setApprentices(aprendices1);

        when(repository.save(training1))
                .thenReturn(Mono.just(training1));

        when(repository.findById(training1.getTrainingId()))
                .thenReturn(Mono.just(training1));

        when(repository.findAll()).thenReturn(Flux.just(training1));

        StepVerifier
                .create(service.getAprendicesByTrainingId(training1.getTrainingId()))
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

}