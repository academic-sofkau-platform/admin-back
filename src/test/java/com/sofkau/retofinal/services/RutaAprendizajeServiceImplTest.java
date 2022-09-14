package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.models.Ruta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
class RutaAprendizajeServiceImplTest {

    @Autowired
    RutaAprendizajeServiceImpl service;

    @Test
    void save() {
        List<String> prerrequisitos = List.of("Angular", "Programación funcional y reactiva");
        RutaAprendizajeDto rutaAprendizajeDto = new RutaAprendizajeDto("1",
                "Prueba",
                "Descripción de prueba",
                List.of(new Ruta("1",1, "Curso de prueba", prerrequisitos))
        );

    }

    @Test
    void findAll() {
        List<String> prerrequisitos = List.of("Angular", "Programación funcional y reactiva");
        RutaAprendizajeDto rutaAprendizajeDto = new RutaAprendizajeDto("1",
                "Prueba",
                "Descripción de prueba",
                List.of(new Ruta("1",1, "Curso de prueba", prerrequisitos))
        );
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
    void addRoute() {
    }

    @Test
    void removeRoute() {
    }
}