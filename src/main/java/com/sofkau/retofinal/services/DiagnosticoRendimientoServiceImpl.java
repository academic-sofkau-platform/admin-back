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

        public class AccionDeMejora{
            public AccionDeMejora(String cursos, String test, String accion) {
            }

            public void main(String args[]){
                AccionDeMejora accionDeMejora[]=new AccionDeMejora[0];
                accionDeMejora[0]=new AccionDeMejora("DDD", "Test 1", "Repaso de conceptos de fundamentos de DDD (link de documentación)");
                accionDeMejora[1]=new AccionDeMejora("DDD", "Test 2", "Repaso de conceptos de fundamentos de DDD (link de documentación)");
                accionDeMejora[2]=new AccionDeMejora("Reactividad", "Test 1","Repaso Reactiva (link documentación)");
                accionDeMejora[2]=new AccionDeMejora("Reactividad", "Quiz 3","Repaso Reactiva  (link documentación)");
                accionDeMejora[3]=new AccionDeMejora("Funcional", "Quiz 1", "Repaso Funcional y reactiva (link documentación)");
                accionDeMejora[4]=new AccionDeMejora("Funcional", "Test 2", "Repaso Funcional y reactiva (link documentación)");
                accionDeMejora[5]=new AccionDeMejora("Introduccion Desarrollo", "Quiz 3", "Repaso Introduccion al Desarrollo (link documentación)");
                accionDeMejora[6]=new AccionDeMejora("Introduccion Desarrollo", "Test 2", "Repaso Introduccion al Desarrollo (link documentación)");




            }
        }


    }



//
//        CURSO: DDD, Test 1, ACCION:Repaso de conceptos de fundamentos de DDD (link de documentación)
//        CRUSO: DDD, Test 2, ACCION:Repaso de componente estrategico de DDD (link de documentación)
//        CURSO: Reactividad: ACCION:Test 1: Funcional y reactiva (link documentación)
//        seria  bajo al 75%


//todo filtar por nota <75%

// todo recorrer List de accion de mejoras para ver si esta repetida


//Todo  DETERMINAR si un aprendiz esta en bajo rendimiento


//Todo  PONER una accion de mejora cuando se tenga un bajo rendimieto


//Todo  Si se tiene una accion de mejora se debe ENVIAR un correo con la accion de mejora correspondiente


//Todo  No puedo enviar una accion de mejora repetida, es decir debo CONSULTAR si el aprendiz no tiene la accion de mejora correspodiente
