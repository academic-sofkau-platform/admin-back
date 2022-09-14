package com.sofkau.retofinal.utils;

import com.sofkau.retofinal.dto.*;
import com.sofkau.retofinal.models.*;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Flux;

public class AppUtils {
    public static RutaAprendizajeDto rutaAprendizajeToDto(RutaAprendizaje rutaAprendizaje){
        RutaAprendizajeDto rutaAprendizajeDto = new RutaAprendizajeDto();
        BeanUtils.copyProperties(rutaAprendizaje, rutaAprendizajeDto);
        return rutaAprendizajeDto;
    }

    public static RutaAprendizaje dtoToRutaAprendizaje(RutaAprendizajeDto rutaAprendizajeDto){
        RutaAprendizaje rutaAprendizaje = new RutaAprendizaje();
        BeanUtils.copyProperties(rutaAprendizajeDto, rutaAprendizaje);
        return rutaAprendizaje;
    }

    public static RutaDto rutaToDto(Ruta ruta){
        RutaDto rutaDto = new RutaDto();
        BeanUtils.copyProperties(ruta, rutaDto);
        return rutaDto;
    }

    public static Ruta dtoToRuta(RutaDto rutaDto){
        Ruta ruta = new Ruta();
        BeanUtils.copyProperties(rutaDto, ruta);
        return ruta;
    }

    public static ActividadDto actividadToDto(Actividad actividad){
        ActividadDto actividadDto = new ActividadDto();
        BeanUtils.copyProperties(actividad, actividadDto);
        actividadDto.setFecha(actividad.getFecha().toString());
        return actividadDto;
    }

    public static Flux<ActividadDto> actividadListToDto(Flux<Actividad> actividadFlux){
        Flux<ActividadDto> actividadListDto = actividadFlux.map(AppUtils::actividadToDto);
        return actividadListDto;
    }

    //Curso
    public static CursoDto cursoToDto(Curso curso){
        CursoDto cursoDto = new CursoDto();
        BeanUtils.copyProperties(curso, cursoDto);
        return cursoDto;
    }

    public static Flux<CursoDto> cursoListToDto(Flux<Curso> cursoFlux){
        Flux<CursoDto> cursoListDto = cursoFlux.map(AppUtils::cursoToDto);
        return cursoListDto;
    }

    //Trainings
    public static TrainingDto trainingToDto(Training training){
        TrainingDto trainingDto = new TrainingDto();
        BeanUtils.copyProperties(training, trainingDto);
        return trainingDto;
    }

    public static Training dtoToTraining(TrainingDto trainingDto){
        Training training = new Training();
        BeanUtils.copyProperties(trainingDto, training);
        return training;
    }
}
