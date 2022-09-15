package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.Ruta;
import com.sofkau.retofinal.models.RutaAprendizaje;
import com.sofkau.retofinal.repositories.RutaAprendizajeRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;


@SpringBootTest
class RutaAprendizajeServiceImplTest {

    @Autowired
    private RutaAprendizajeServiceImpl service;

    @MockBean
    private RutaAprendizajeRepository repository;

    @Test
    void save() {
        List<String> prerrequisitos = List.of("Angular", "Programación funcional y reactiva");
        RutaAprendizaje rutaAprendizaje = new RutaAprendizaje("1",
                "Prueba",
                "Descripción de prueba",
                List.of(new Ruta("1",1, "Curso de prueba", prerrequisitos))
        );

        when(repository.save(rutaAprendizaje))
                .thenReturn(Mono.just(rutaAprendizaje));

        StepVerifier
                .create(service.save(AppUtils.rutaAprendizajeToDto(rutaAprendizaje)))
                .expectNextMatches(rutaAprendizajeDto ->
                        rutaAprendizajeDto.getId().equals("1") &&
                                rutaAprendizajeDto.getNombre().equals("Prueba") &&
                                rutaAprendizajeDto.getDescripcion().equals("Descripción de prueba") &&
                                rutaAprendizajeDto.getRutas().equals(List.of(new Ruta("1",1, "Curso de prueba", prerrequisitos)))
                )
                .expectComplete()
                .verify();
    }

    @Test
    void findAll() {
        List<String> prerrequisitos = List.of("Angular", "Programación funcional y reactiva");
        RutaAprendizaje rutaAprendizaje = new RutaAprendizaje("1",
                "Prueba",
                "Descripción de prueba",
                List.of(new Ruta("1",1, "Curso de prueba", prerrequisitos))
        );

        List<String> prerrequisitos2 = List.of("Curso de prueba");
        RutaAprendizaje rutaAprendizaje2 = new RutaAprendizaje("2",
                "Prueba 2",
                "Descripción de prueba 2",
                List.of(new Ruta("1",2, "Curso de prueba", prerrequisitos2))
        );

        when(repository.findAll())
                .thenReturn(Flux.just(rutaAprendizaje, rutaAprendizaje2));

        StepVerifier
                .create(service.findAll())
                .expectNext(AppUtils.rutaAprendizajeToDto(rutaAprendizaje))
                .expectNext(AppUtils.rutaAprendizajeToDto(rutaAprendizaje2))
                .expectComplete()
                .verify();

    }

    @Test
    void findById() {
        List<String> prerrequisitos = List.of("Angular", "Programación funcional y reactiva");
        RutaAprendizaje rutaAprendizaje = new RutaAprendizaje("1",
                "Prueba",
                "Descripción de prueba",
                List.of(new Ruta("1",1, "Curso de prueba", prerrequisitos))
        );

        when(repository.findById("1"))
                .thenReturn(Mono.just(rutaAprendizaje));

        StepVerifier
                .create(service.findById("1"))
                .expectNext(AppUtils.rutaAprendizajeToDto(rutaAprendizaje))
                .expectComplete()
                .verify();

    }

    @Test
    void update() {
        List<String> prerrequisitos = List.of("Angular", "Programación funcional y reactiva");
        RutaAprendizaje rutaAprendizaje = new RutaAprendizaje("1",
                "Prueba",
                "Descripción de prueba",
                List.of(new Ruta("1",1, "Curso de prueba", prerrequisitos))
        );

        List<String> prerrequisitos2 = List.of("Curso de prueba");
        RutaAprendizaje rutaAprendizajeModificada = new RutaAprendizaje("1",
                "Prueba 2",
                "Descripción de prueba 2",
                List.of(new Ruta("1",2, "Curso de prueba", prerrequisitos2))
        );

        when(repository.save(rutaAprendizaje))
                .thenReturn(Mono.just(rutaAprendizaje));

        when(repository.findById(rutaAprendizaje.getId()))
                .thenReturn(Mono.just(rutaAprendizaje));

        when(repository.save(rutaAprendizajeModificada))
                .thenReturn(Mono.just(rutaAprendizajeModificada));

        StepVerifier
                .create(service.update(AppUtils.rutaAprendizajeToDto(rutaAprendizajeModificada), rutaAprendizaje.getId()))
                .expectNextMatches(rutaAprendizajeDto ->
                        rutaAprendizajeDto.getId().equals("1") &&
                        rutaAprendizajeDto.getNombre().equals("Prueba 2") &&
                        rutaAprendizajeDto.getDescripcion().equals("Descripción de prueba 2") &&
                        rutaAprendizajeDto.getRutas().equals(List.of(new Ruta("1",2, "Curso de prueba", prerrequisitos2)))
                )
                .expectComplete()
                .verify();

    }

    @Test
    void deleteById() {
        List<String> prerrequisitos = List.of("Angular", "Programación funcional y reactiva");
        RutaAprendizaje rutaAprendizaje = new RutaAprendizaje("1",
                "Prueba",
                "Descripción de prueba",
                List.of(new Ruta("1",1, "Curso de prueba", prerrequisitos))
        );

        when(repository.findById("1")).thenReturn(Mono.just(rutaAprendizaje));
        when(repository.deleteById("1")).thenReturn(Mono.empty());

        StepVerifier
                .create(service.deleteById(rutaAprendizaje.getId()))
                .expectNext()
                .expectComplete()
                .verify();
    }

    @Test
    void addRoute() {
        List<String> prerrequisitos = new ArrayList<>();
        prerrequisitos.add("Angular");
        prerrequisitos.add("Programación funcional y reactiva");

        List<Ruta> rutas = new ArrayList<>();
        rutas.add(new Ruta("1",1, "Curso de prueba", prerrequisitos));

        RutaAprendizaje rutaAprendizaje = new RutaAprendizaje("1",
                "Prueba",
                "Descripción de prueba",
                rutas
        );

        Ruta ruta = new Ruta();
        ruta.setCurso("Curso de prueba 2");
        ruta.setNivel(2);
        ruta.setPrerrequisitos(prerrequisitos);

        when(repository.save(rutaAprendizaje))
                .thenReturn(Mono.just(rutaAprendizaje));

        when(repository.findById(rutaAprendizaje.getId()))
                .thenReturn(Mono.just(rutaAprendizaje));

        StepVerifier
                .create(service.addRoute(AppUtils.rutaToDto(ruta), rutaAprendizaje.getId()))
                .expectNextMatches(rutaAprendizajeDto ->
                        rutaAprendizajeDto.getId().equals("1") &&
                        rutaAprendizajeDto.getNombre().equals("Prueba") &&
                        rutaAprendizajeDto.getDescripcion().equals("Descripción de prueba") &&
                        rutaAprendizajeDto.getRutas().size() == 2
                 )
                .expectComplete()
                .verify();
    }

    @Test
    void removeRoute() {
        List<String> prerrequisitos = new ArrayList<>();
        prerrequisitos.add("Angular");
        prerrequisitos.add("Programación funcional y reactiva");

        List<Ruta> rutas = new ArrayList<>();
        rutas.add(new Ruta("1",1, "Curso de prueba", prerrequisitos));
        rutas.add(new Ruta("2",2, "Curso de prueba 2", prerrequisitos));

        RutaAprendizaje rutaAprendizaje = new RutaAprendizaje("1",
                "Prueba",
                "Descripción de prueba",
                rutas
        );

        when(repository.save(rutaAprendizaje))
                .thenReturn(Mono.just(rutaAprendizaje));

        when(repository.findById(rutaAprendizaje.getId()))
                .thenReturn(Mono.just(rutaAprendizaje));

        StepVerifier
                .create(service.removeRoute(rutaAprendizaje.getId(), rutaAprendizaje.getRutas().get(0).getId()))
                .expectNext()
                .expectComplete()
                .verify();
    }
}