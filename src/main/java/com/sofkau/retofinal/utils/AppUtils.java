package com.sofkau.retofinal.utils;

import com.sofkau.retofinal.dto.*;
import com.sofkau.retofinal.models.*;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

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

    public static Flux<TrainingDto> trainingFluxToDto(Flux<Training> trainingFlux){
        Flux<TrainingDto> trainingFluxDto = trainingFlux.map(AppUtils::trainingToDto);
        return trainingFluxDto;
    }

    public static Training dtoToTraining(TrainingDto trainingDto){
        Training training = new Training();
        BeanUtils.copyProperties(trainingDto, training);
        return training;
    }
    public static String decoderBase64(String encodedBytesBase64) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedBytesBase64);
        String decodedString = new String(decodedBytes);
        return decodedString;
    }
    public static List<Aprendiz> obtenerAprendices(String csvDecodificado) {
        String[] aprendices = csvDecodificado.split("\n", 0);
        String[] aprendicesLimpio = Arrays.copyOfRange(aprendices, 1, aprendices.length);
        List<Aprendiz> listAprendices = new ArrayList<>();
        Arrays.stream(aprendicesLimpio).forEach(aprendiz -> {
            var aprendizString= aprendiz.split(",", 0);
            var aprendizNombreCompleto = aprendizString[0].split(" ", 2);
            Aprendiz newAprendiz = new Aprendiz();
            newAprendiz.setName(aprendizNombreCompleto[0]);
            newAprendiz.setLastName(aprendizNombreCompleto[1]);
            newAprendiz.setEmail(aprendizString[1]);
            newAprendiz.setPhoneNumber(aprendizString[2]);
            listAprendices.add(newAprendiz);
        });
        return listAprendices;
    }

    //Todo crear training original ARMAR ESTO y luego saviarlo
    public static Training armarTraining(TrainingAuxiliar trainingAuxiliar) {
        Training training = new Training();
        training.setApprentices(obtenerAprendices(decoderBase64(trainingAuxiliar.getApprentices())));
        training.setCoach(trainingAuxiliar.getCoach());
        training.setDescription(trainingAuxiliar.getDescription());
        training.setEndDate(trainingAuxiliar.getEndDate());
        training.setName(trainingAuxiliar.getName());
        training.setRutaId(trainingAuxiliar.getRutaId());
        training.setStartDate(trainingAuxiliar.getStartDate());
        return training;
    }
}
