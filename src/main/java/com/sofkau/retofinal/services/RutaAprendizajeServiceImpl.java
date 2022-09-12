package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.dto.RutaDto;
import com.sofkau.retofinal.interfaces.IRutaAprendizajeService;
import com.sofkau.retofinal.models.Ruta;
import com.sofkau.retofinal.models.RutaAprendizaje;
import com.sofkau.retofinal.repositories.RutaAprendizajeRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RutaAprendizajeServiceImpl implements IRutaAprendizajeService {
    @Autowired
    RutaAprendizajeRepository repository;

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

}
