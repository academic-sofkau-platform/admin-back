package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DiagnosticoRendimientoServiceImpl {   //   Se debe ejecutar cuando se realice extracción de notas
    @Autowired
    TrainingServicesImpl trainingServices;
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

    public void ponerMejora() {}

    public Aprendiz getAprendizById(List<Aprendiz> aprendices, String aprendizId){
        return aprendices.stream()
                .filter(aprendiz1 -> aprendiz1.getId().equals(aprendizId))
                .collect(Collectors.toList())
                .get(0);
    }

    public AccionDeMejora getAccionDeMejoraByCursoId(ArrayList<AccionDeMejora> accionDeMejoras, String cursoId){
        return accionDeMejoras.stream()
                .filter(accionDeMejora -> accionDeMejora.getCursoId().equals(cursoId))
                .collect(Collectors.toList())
                .get(0);
    }

    public void diagnosticar(Flux<Notas> notas){

        // Todo  No puedo enviar una accion de mejora repetida, es decir debo CONSULTAR si el aprendiz no tiene la accion de mejora correspodiente
        // Todo  DETERMINAR si un aprendiz esta en bajo rendimiento <75% y poner una accion de mejora cuando se tenga un bajo rendimieto
        // Todo  Si se tiene una accion de mejora se debe ENVIAR un correo con la accion de mejora correspondiente

        notas.toStream()
                .forEach(notas1 -> {

                    var aprendices = trainingServices.getAllAprendicesByTrainingId(notas1.getTrainingId()).collectList().block();
                    var aprendiz = getAprendizById(aprendices, notas1.getAprendizId());
                    System.out.println(aprendiz);

                    notas1.getActividadList()
                        .stream()
                        .forEach(actividad -> {
                            // si la actividad tiene menos de 75% de puntaje
                            if(actividad.getPuntaje() < 75){
                                var accion = getAccionDeMejoraByCursoId(accionDeMejoras, actividad.getCursoId());
                                System.out.println(accion);
                                var accionesDelAprendiz = aprendiz.getAccionDeMejora();

                                // si el aprendiz no tiene ya asignada esa accion de mejora, se le asigna
                                if(!accionesDelAprendiz.contains(accion.getAccion())){
                                    aprendiz.getAccionDeMejora().add(accion.getAccion());
                                }

                                //Mandar un correo
                            }
                        });
                });
    }

}
