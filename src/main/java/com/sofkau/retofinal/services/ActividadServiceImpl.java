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
    public Mono<ActividadDto> updatePuntaje(Actividad actividadx, Integer puntaje) {
        return  repository.findById(actividadx.getActividadId())
                .map(actividad -> {
                    actividad.setPuntaje(actividad.getPuntaje() + puntaje);
                    return AppUtils.actividadToDto(repository.save(actividad).block());
                })
                .switchIfEmpty(Mono.empty());
    }


    public Flux<Actividad> findActivityByAprendizId(String aprendizId) {
        return repository.findAll()
                .filter(actividad -> actividad.getAprendizId().equals(aprendizId))
                .switchIfEmpty(Mono.empty());
    }
}
