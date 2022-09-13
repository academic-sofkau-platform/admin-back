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

import java.time.LocalDate;

@Service
public class ActividadServiceImpl implements IActividadService {

    @Autowired
    ActividadRepository repository;

    @Override
    public Flux<ActividadDto> findAll() {
        return AppUtils.actividadListToDto(repository.findAll());
    }

    @Override
    public Mono<ActividadDto> addOrUpdate(Integer puntaje, String cursoId, String aprendizId, String fecha) {
        return  findByAprendizIdAndFecha(aprendizId, cursoId, LocalDate.parse(fecha))
                .map(actividad -> {
                    actividad.setPuntaje(actividad.getPuntaje() + puntaje);
                    return AppUtils.actividadToDto(repository.save(actividad).block());
                })
                .switchIfEmpty(Mono.just(AppUtils.actividadToDto(repository.save(new Actividad(cursoId, aprendizId, LocalDate.parse(fecha), puntaje, "test", 78)).block())));
    }

    private Mono<Actividad> findByAprendizIdAndFecha(String aprendizId,String cursoId, LocalDate fecha) {
        return repository.findAll()
                .filter(actividad -> actividad.getAprendizId().equals(aprendizId))
                .filter(actividad -> actividad.getCursoId().equals(cursoId))
                .filter(actividad -> actividad.getFecha().equals(fecha))
                .next()
                .switchIfEmpty(Mono.empty());
    }

    public Flux<Actividad> findActivityByAprendizId(String aprendizId) {
        return repository.findAll()
                .filter(actividad -> actividad.getAprendizId().equals(aprendizId))
                .switchIfEmpty(Mono.empty());
    }
}
