package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class DiagnosticoRendimientoServiceImpl {   //   Se debe ejecutar cuando se realice extracción de notas
    @Autowired
    TrainingServicesImpl trainingServices;
    @Autowired
    ActividadServiceImpl actividadService;
    public ArrayList<AccionDeMejora> accionDeMejoras = new ArrayList<AccionDeMejora>();

    public DiagnosticoRendimientoServiceImpl() {
        this.accionDeMejoras.add(new AccionDeMejora("7595fb82-db54-490b-91fc-ec0c8e7daaa1", "Test 1", "Repaso de conceptos de fundamentos de DDD (link de documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("7595fb82-db54-490b-91fc-ec0c8e7daaa1", "Test 2", "Repaso de conceptos de fundamentos de DDD (link de documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("7979bb47-d347-4a42-a648-75abdf637886", "Test 1", "Repaso Reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("7979bb47-d347-4a42-a648-75abdf637886", "Quiz 3", "Repaso Reactiva  (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("990cfbf4-8022-4609-a21d-d25c2072f555", "Quiz 1", "Repaso Funcional y reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("990cfbf4-8022-4609-a21d-d25c2072f555", "Test 2", "Repaso Funcional y reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("022686ac-9f63-4636-8ac4-37c2129cba51", "Quiz 3", "Repaso Introduccion al Desarrollo (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("022686ac-9f63-4636-8ac4-37c2129cba51", "Test 2", "Repaso Introduccion al Desarrollo (link documentación)"));
    }

    public Aprendiz getAprendizById(String trainingId, String aprendizId){
        // se obtienen todos los aprendices de cada training de la nota
        Flux<Aprendiz> aprendices = trainingServices.getAllAprendicesByTrainingId(trainingId);

        Aprendiz aprendiz = aprendices
                .filter(aprendiz1 -> aprendiz1.getId().equals(aprendizId))
                .collect(Collectors.toList())
                .block()
                .get(0);

        // SE DEBE ACTUALIZAR LA BD PARA QUE ACCION DE MEJORA EN APRENDIZ NO SEA NULL SINO []
        aprendiz.setAccionDeMejora(new ArrayList<>());

        return aprendiz;
    }

    public Mono<AccionDeMejora> getAccionDeMejoraByCursoId(String cursoId) {
        return Flux.fromIterable(this.accionDeMejoras)
                .filter(accionDeMejora -> accionDeMejora.getCursoId().equals(cursoId))
                .next()
                .switchIfEmpty(Mono.empty());

    }

    public void setAprendizAccionesDeMejora(Aprendiz aprendiz, AccionDeMejora accionDeMejoras){
        // si el aprendiz no tiene ya asignada la accion de mejora
        if(!aprendiz.getAccionDeMejora().contains(accionDeMejoras.getAccion())){
            aprendiz.getAccionDeMejora().add(accionDeMejoras.getAccion());
        }
    }

    public void diagnosticar(Flux<Notas> notas){

        // Todo  No puedo enviar una accion de mejora repetida, es decir debo CONSULTAR si el aprendiz no tiene la accion de mejora correspodiente
        // Todo  DETERMINAR si un aprendiz esta en bajo rendimiento <75% y poner una accion de mejora cuando se tenga un bajo rendimieto
        // Todo  Si se tiene una accion de mejora se debe ENVIAR un correo con la accion de mejora correspondiente

        // Recorre todas las notas
        notas.toStream().forEach(notas1 -> {

            // se obtiene las actividades del aprendiz
            var actividades = actividadService.findActivityByAprendizId(notas1.getAprendizId()).collectList().block();

            if(actividades != null && actividades.size() > 0){
                actividades.forEach(actividad -> {
                    if(actividad.getPuntaje() < 75){
                        AccionDeMejora accionDeMejora = getAccionDeMejoraByCursoId(actividad.getCursoId()).block();
                        if(accionDeMejora != null ) {

                            // se obtiene el objeto aprendiz
                            var aprendiz = getAprendizById(notas1.getTrainingId(), notas1.getAprendizId());

                            // le agrega al aprendiz la accion de mejora
                            setAprendizAccionesDeMejora(aprendiz, accionDeMejora);

                            // System.out.println(aprendiz);
                            // enviar correo
                        }
                    }
                });
            }
        });
    }

}
