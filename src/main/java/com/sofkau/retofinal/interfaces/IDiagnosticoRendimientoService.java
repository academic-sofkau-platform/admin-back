package com.sofkau.retofinal.interfaces;

import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.dto.RutaDto;
import com.sofkau.retofinal.models.Notas;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IDiagnosticoRendimientoService {


    Mono<NotasDto> save(RutaAprendizajeDto rutaAprendizajeDto);
    Flux<NotasDto> findAll();
    Mono<NotasDto> findById(String rutaAprendizajeId);
    Mono<NotasDto> update(RutaAprendizajeDto rutaAprendizajeDto, String rutaAprendizajeId);
    Mono<NotasDto> addMejora(RutaDto rutaDto, String NotaId);

}
