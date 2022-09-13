package com.sofkau.retofinal.utils;

import com.sofkau.retofinal.dto.ActividadDto;
import com.sofkau.retofinal.dto.CursoDto;
import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.dto.RutaDto;
import com.sofkau.retofinal.models.Actividad;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.models.Ruta;
import com.sofkau.retofinal.models.RutaAprendizaje;
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
}
