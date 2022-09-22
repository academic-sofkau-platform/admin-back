package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.dto.RutaDto;
import com.sofkau.retofinal.interfaces.IRutaAprendizajeService;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.models.Ruta;
import com.sofkau.retofinal.models.RutaAprendiz;
import com.sofkau.retofinal.models.RutaAprendizaje;
import com.sofkau.retofinal.repositories.RutaAprendizajeRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RutaAprendizajeServiceImpl implements IRutaAprendizajeService {
    @Autowired
    private RutaAprendizajeRepository repository;

    @Autowired
    private TrainingServicesImpl trainingService;

    private CursoServiceImpl cursoService;

    @Autowired
    public RutaAprendizajeServiceImpl(@Lazy CursoServiceImpl cursoService) {
        this.cursoService = cursoService;
    }

    @Override
    public Mono<RutaAprendizajeDto> save(RutaAprendizajeDto rutaAprendizajeDto) {
        RutaAprendizaje rutaA = AppUtils.dtoToRutaAprendizaje(rutaAprendizajeDto);
        return repository
                .save(rutaA)
                .map(AppUtils::rutaAprendizajeToDto);
    }

    @Override
    public Flux<RutaAprendizajeDto> findAll() {
        return repository.findAll().map(AppUtils::rutaAprendizajeToDto);
    }

    @Override
    public Mono<RutaAprendizajeDto> findById(String rutaAprendizajeId) {
        return repository
                .findById(rutaAprendizajeId)
                .map(AppUtils::rutaAprendizajeToDto);
    }

    @Override
    public Mono<RutaAprendizajeDto> update(RutaAprendizajeDto rutaAprendizajeDto, String rutaAprendizajeId) {
        return repository
                .findById(rutaAprendizajeId)
                .flatMap(rutaAprendizaje -> {
                    RutaAprendizaje rutaA = AppUtils.dtoToRutaAprendizaje(rutaAprendizajeDto);
                    rutaA.setId(rutaAprendizajeId);
                    return repository.save(rutaA);
                })
                .map(AppUtils::rutaAprendizajeToDto);
    }

    @Override
    public Mono<Void> deleteById(String rutaAprendizajeId) {
        return repository
                .findById(rutaAprendizajeId)
                .flatMap(rutaAprendizaje -> repository.deleteById(rutaAprendizaje.getId()))
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<RutaAprendizajeDto> addRoute(RutaDto rutaDto, String rutaAprendizajeId) {
        return repository
                .findById(rutaAprendizajeId)
                .flatMap(rutaAprendizaje -> {
                    Ruta ruta = AppUtils.dtoToRuta(rutaDto);
                    ruta.setCursoId(rutaDto.getCursoId());
                    rutaAprendizaje.getRutas().add(ruta);
                    return repository.save(rutaAprendizaje);
                })
                .map(AppUtils::rutaAprendizajeToDto)
                .switchIfEmpty(Mono.empty());
    }


    //Acá estoy haciendo por ID pero cuando se crea la ruta de aprendizaje no se guarda el id de la ruta.
    @Override
    public Mono<Void> removeRoute(String rutaId, String rutaAprendizajeId) {
        return repository
                .findById(rutaAprendizajeId)
                .flatMap(rutaAprendizaje -> {
                    var list = rutaAprendizaje
                            .getRutas()
                            .stream()
                            .filter(ruta -> !ruta.getId().equals(rutaId)).collect(Collectors.toList());
                    rutaAprendizaje.setRutas(list);
                    return repository.save(rutaAprendizaje);
                }).then(Mono.empty());
    }

    @Override
    public Mono<Boolean> controlCursoEnRutaAprendizaje(String cursoId) {
        return repository
                .findAll()
                .flatMapIterable(RutaAprendizaje::getRutas)
                .filter(ruta -> ruta.getCursoId().equals(cursoId))
                .next()
                .hasElement();
    }

    //TODO despues del filter, necesito encontrar el primero y trabajar con el
    @Override
    public Flux<RutaAprendiz> obtenerRutasAprendizajeAprendiz(String email) {
        List<RutaAprendiz> rutaAprendizList = new ArrayList<>();
        return this.trainingService
                .getActiveTrainings()
                .filter(trainingDto -> trainingDto.getApprentices()
                        .stream()
                        .anyMatch(aprendiz -> aprendiz.getEmail().equals(email)))
                .flatMapIterable(trainingDto -> {
                    return trainingDto.getApprentices().stream().flatMap(aprendiz -> aprendiz.getTareas().stream())
                            .map(tarea -> {
                                return new RutaAprendiz(trainingDto.getRutaAprendizajeId(), tarea.getCursoId(), null, null, null, tarea);
                            }).collect(Collectors.toUnmodifiableList());


                })
                .flatMap(rutaAprendiz -> {
                    return this.repository.findById(rutaAprendiz.getRutaId())
                            .flatMapIterable(RutaAprendizaje::getRutas)
                            .map(ruta -> {
                                return new RutaAprendiz(rutaAprendiz.getRutaId(), rutaAprendiz.getCursoId(), null, ruta.getNivel(), ruta.getPrerrequisitos(), rutaAprendiz.getTarea());
                            });
                })
                .flatMap(rutaAprendiz -> {
                    return this.cursoService.findCursoById(rutaAprendiz.getCursoId()).map(curso -> {
                        return new RutaAprendiz(rutaAprendiz.getRutaId(), rutaAprendiz.getCursoId(), curso.getNombre(), rutaAprendiz.getNivel(), rutaAprendiz.getPrerrequisitos(), rutaAprendiz.getTarea());
                    });
                });
    }

    @Override
    public Flux<Curso> findCursosByRutaAprendizajeId(String rutaAprendizajeId) {
        return this.repository
                .findById(rutaAprendizajeId)
                .map(RutaAprendizaje::getRutas)
                .flatMapMany(Flux::fromIterable)
                .map(Ruta::getCursoId)
                .flatMap(cursoId -> cursoService.findCursoById(cursoId));
    }

}
