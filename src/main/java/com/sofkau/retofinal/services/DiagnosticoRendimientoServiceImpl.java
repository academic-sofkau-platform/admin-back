package com.sofkau.retofinal.services;
import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


    @Service
    public class DiagnosticoRendimientoServiceImpl {   //   Se debe ejecutar cuando se realice extracción de notas

//    @Autowired NotasRepository repository;
//
//
//    @Override
//    public Mono<Aprendiz> findById() {
//        return repository.findById();
//    }
//
//    @Override
//    public Flux<Aprendiz> determinarRendimiento() {
//        return repository.determinarRendimiento().map(AppUtils::Aprendiz);
//    }
//
//    @Override
//    public Mono<Aprendiz> asignarMejora() {
//        return null;
//    }
//
//        @Override
//        public Mono<Aprendiz> enviarMail() {
//            return null;
//        }

}




//Todo  DETERMINAR si un aprendiz esta en bajo rendimiento

//Todo  PONER una accion de mejora cuando se tenga un bajo rendimieto

//Todo  Si se tiene una accion de mejora se debe ENVIAR un correo con la accion de mejora correspondiente


//Todo  No puedo enviar una accion de mejora repetida, es decir debo CONSULTAR si el aprendiz no tiene la accion de mejora correspodiente


//
//
//        DoR
//
//        - Banco de acciones de mejora

//        - Los valores o promedios para determinar si un aprendiz tiene bajo rendimiento
//

//        Criterios de Aceptación:


//
//
//        ~~El aprendiz debe tener una propiedad para poder sumarle la accion de mejora~~ HECHO
//
//
//        Dependencia:
//
//        Extracción de notas y envio de correos