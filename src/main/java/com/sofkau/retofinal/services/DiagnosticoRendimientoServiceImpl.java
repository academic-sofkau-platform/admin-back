package com.sofkau.retofinal.services;
import com.sofkau.retofinal.controllers.ControllerTraining;
import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.repositories.NotasRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


    @Service
    public class DiagnosticoRendimientoServiceImpl {   //   Se debe ejecutar cuando se realice extracción de notas

        public static void main(String[] args){
            List<String> accionDeMejora = new ArrayList<String>();
            accionDeMejora.add(1,"Repaso de conceptos de fundamentos de DDD (link de documentación");
            accionDeMejora.add(2,"Repaso de componente estrategico de DDD (link de documentación)");
            accionDeMejora.add(3,"Repaso de conceptos de fundamentos de Funcional y reactiva (link de documentación");
            accionDeMejora.add(4,"Repaso de conceptos de fundamentos de WebFlux (link de documentación");
            accionDeMejora.add(5,"Repaso de conceptos de Funcional y reactiva (link de documentación");

        }
    }


//
//        CURSO: DDD, Test 1, ACCION:Repaso de conceptos de fundamentos de DDD (link de documentación)
//        CRUSO: DDD, Test 2, ACCION:Repaso de componente estrategico de DDD (link de documentación)
//        CURSO: Reactividad: ACCION:Test 1: Funcional y reactiva (link documentación)
//        seria todo bajo al 75%

//    @Autowired
//    NotasRepository repository;
//    @Autowired
//    ControllerTraining training;

    //todo obtener aprendices
//        @Override
//
//
//        @Override
//        public Flux<Notas> findAllNota() {  //todo filtar por nota <75%
//            return repository.findAllNota();
//        }

        //todo recorrer List de accion de mejoras para ver si esta repetida



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