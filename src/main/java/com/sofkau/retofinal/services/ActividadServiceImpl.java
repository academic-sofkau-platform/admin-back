package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.ActividadDto;
import com.sofkau.retofinal.interfaces.IActividadService;
import com.sofkau.retofinal.models.Actividad;
import com.sofkau.retofinal.repositories.ActividadRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

@Service
public class ActividadServiceImpl implements IActividadService {

    @Autowired
    ActividadRepository repository;

    @Override
    public Mono<ActividadDto> save(Actividad actividad) {
        return  repository.save(actividad).thenReturn(AppUtils.actividadToDto(actividad));
    }

    @Override
    public Flux<ActividadDto> findAll() {
        return AppUtils.actividadListToDto(repository.findAll());
    }

    @Override
    public Mono<ActividadDto> addOrUpdate(Integer puntaje, String cursoId, String aprendizId) {
        return  findByAprendizIdAndFecha(aprendizId, cursoId, LocalDate.now())
                .map(actividad -> {
                    actividad.setPuntaje(actividad.getPuntaje() + puntaje);
                    return AppUtils.actividadToDto(repository.save(actividad).block());
                })
                .switchIfEmpty(Mono.just(AppUtils.actividadToDto(repository.save(new Actividad(cursoId, aprendizId, LocalDate.now(), puntaje)).block())));
    }

    private Mono<Actividad> findByAprendizIdAndFecha(String aprendizId,String cursoId, LocalDate fecha) {
        return repository.findAll()
                .filter(actividad -> actividad.getAprendizId().equals(aprendizId))
                .filter(actividad -> actividad.getCursoId().equals(cursoId))
                .filter(actividad -> actividad.getFecha().equals(fecha))
                .next()
                .switchIfEmpty(Mono.empty());
    }
}
