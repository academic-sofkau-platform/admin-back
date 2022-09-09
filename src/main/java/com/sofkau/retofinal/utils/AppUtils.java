package com.sofkau.retofinal.utils;

import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.dto.RutaDto;
import com.sofkau.retofinal.models.Ruta;
import com.sofkau.retofinal.models.RutaAprendizaje;
import org.springframework.beans.BeanUtils;

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
}
