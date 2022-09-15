package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.RutaAprendizaje;
import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.repositories.RutaAprendizajeRepository;
import com.sofkau.retofinal.repositories.TrainingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TrainingServicesImplTest {
    @Autowired
    private TrainingServicesImpl service;

    @MockBean
    private TrainingRepository repository;
    String DEFAULT_PATTERN = "dd-MM-yyyy";
    @Test
    void save() {
        String fechaInicio = "2022-09-15";
        String fechaFin = "2022-09-30";
        Date dateFechaInicio;
        Date dateFechaFin;

        DateFormat formatter = new SimpleDateFormat(DEFAULT_PATTERN);

        try {
            dateFechaInicio = formatter.parse(fechaInicio);
            dateFechaFin = formatter.parse(fechaFin);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<Aprendiz> aprendizs = new ArrayList<>();
        aprendizs.add(
                new Aprendiz(
                        "1",
                "Joaquin",
                "Fumeaux",
                "Paysandú",
                "Masculino",
                "joacofum@gmail.com",
                3123,
                "yo.jpg",
                true,
                null
                )
        );

        Training training = new Training();
        training.setTrainingId("1");
        training.setName("Training de prueba");
        training.setDescription("Descripción del training");
        training.setStartDate(dateFechaInicio);
        training.setEndDate(dateFechaFin);
        training.setCoach("Rauuul");
        training.setApprentices(aprendizs);
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
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
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