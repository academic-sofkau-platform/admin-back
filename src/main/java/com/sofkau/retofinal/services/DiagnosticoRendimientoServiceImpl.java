package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class DiagnosticoRendimientoServiceImpl {   //   Se debe ejecutar cuando se realice extracción de notas

    public ArrayList<AccionDeMejora> accionDeMejoras = new ArrayList<AccionDeMejora>();

    public DiagnosticoRendimientoServiceImpl() {
        this.accionDeMejoras.add(new AccionDeMejora("07343d77-02c7-466a-8cbc-fbbaeb3643a8", "Test 1", "Repaso de conceptos de fundamentos de DDD (link de documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("07343d77-02c7-466a-8cbc-fbbaeb3643a8", "Test 2", "Repaso de conceptos de fundamentos de DDD (link de documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("7979bb47-d347-4a42-a648-75abdf637886", "Test 1", "Repaso Reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("7979bb47-d347-4a42-a648-75abdf637886", "Quiz 3", "Repaso Reactiva  (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("990cfbf4-8022-4609-a21d-d25c2072f555", "Quiz 1", "Repaso Funcional y reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("990cfbf4-8022-4609-a21d-d25c2072f555", "Test 2", "Repaso Funcional y reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("022686ac-9f63-4636-8ac4-37c2129cba51", "Quiz 3", "Repaso Introduccion al Desarrollo (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("022686ac-9f63-4636-8ac4-37c2129cba51", "Test 2", "Repaso Introduccion al Desarrollo (link documentación)"));
    }

    public void ponerMejora() {

    }


    //    public void diagnosticar(Notas[] notas){
//        Arrays.stream(notas).forEach(notas ->{
//            if (notas < 75){
//                Aprendiz aprendiz = Aprendiz.of
//                actividad.getCursoId()
//            }
//        });
//    }
//    public void diagnosticar(Notas[] notas) {
//        Arrays.stream(notas).forEach(nota ->{
//        if(nota < 75){
//            accionDeMejoras.add(new AccionDeMejora());
//        }
//
//    });
//    }

}


//Todo  DETERMINAR si un aprendiz esta en bajo rendimiento <75%

//Todo  PONER una accion de mejora cuando se tenga un bajo rendimieto


//Todo  Si se tiene una accion de mejora se debe ENVIAR un correo con la accion de mejora correspondiente


//Todo  No puedo enviar una accion de mejora repetida, es decir debo CONSULTAR si el aprendiz no tiene la accion de mejora correspodiente
