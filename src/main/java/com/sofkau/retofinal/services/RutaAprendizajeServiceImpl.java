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
    public Mono<RutaAprendizajeDto> save(Mono<RutaAprendizajeDto> rutaAprendizajeDto) {
        return rutaAprendizajeDto.map(AppUtils::dtoToRutaAprendizaje)
                .flatMap(repository::insert)
                .map(AppUtils::rutaAprendizajeToDto);
    }

    @Override
    public Flux<RutaAprendizajeDto> findAll() {
        return repository.findAll().map(AppUtils::rutaAprendizajeToDto);
    }

    @Override
    public Mono<RutaAprendizajeDto> findById(String rutaAprendizajeId) {
        return repository.findById(rutaAprendizajeId)
                .map(AppUtils::rutaAprendizajeToDto);
    }

    @Override
    public Mono<RutaAprendizajeDto> update(Mono<RutaAprendizajeDto> rutaAprendizajeDto, String rutaAprendizajeId) {
        return repository
                .findById(rutaAprendizajeId)
                .flatMap(rutaAprendizaje1 ->
                        rutaAprendizajeDto.map(AppUtils::dtoToRutaAprendizaje)
                                .doOnNext(e -> e.setId(rutaAprendizajeId)))
                .flatMap(repository::save)
                .map(AppUtils::rutaAprendizajeToDto)
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> deleteById(String rutaAprendizajeId) {
        return repository
                .findById(rutaAprendizajeId)
                .flatMap(rutaAprendizaje -> repository.deleteById(rutaAprendizaje.getId()))
                .switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<RutaAprendizajeDto> addRoute(Mono<RutaDto> rutaDto, String rutaAprendizajeId) {
        return repository
                .findById(rutaAprendizajeId)
                .flatMap(rutaAprendizaje -> {
                    rutaDto.map(AppUtils::dtoToRuta)
                            .doOnNext(e-> {
                                e.setId(rutaAprendizajeId);
                                rutaAprendizaje.getRutas().add(e);
                            });
                   return repository.save(rutaAprendizaje);
                }).map(AppUtils::rutaAprendizajeToDto)
                .switchIfEmpty(Mono.empty());
    }

}
