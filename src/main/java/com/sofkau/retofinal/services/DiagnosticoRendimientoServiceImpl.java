package com.sofkau.retofinal.services;

import com.sofkau.retofinal.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;


@Service
public class DiagnosticoRendimientoServiceImpl {   //   Se debe ejecutar cuando se realice extracción de notas
    @Autowired
    TrainingServicesImpl trainingServices;
    @Autowired
    ActividadServiceImpl actividadService;
    @Autowired
    CursoServiceImpl cursoService;

    @Autowired
    private EnvioDeCorreoServiceImpl envioDeCorreoService;

    public DiagnosticoRendimientoServiceImpl() {
        /*this.accionDeMejoras.add(new AccionDeMejora("63226e34adbe15156e21d4ad","7595fb82-db54-490b-91fc-ec0c8e7daaa1",  "Repaso de conceptos de fundamentos de DDD (link de documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63226e4cadbe15156e21d4ae", "7595fb82-db54-490b-91fc-ec0c8e7daaa1",  "Repaso de conceptos de fundamentos de DDD (link de documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63226e4fadbe15156e21d4af", "4149bdc6-f0b4-4f94-a030-385c695a88a7",  "Repaso Reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63234960e11cf83d158c80c3", "7595fb82-db54-490b-91fc-ec0c8e7daaa1",  "Repaso Reactiva  (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63226e62adbe15156e21d4b1", "7595fb82-db54-490b-91fc-ec0c8e7daaa1",  "Repaso Funcional y reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63226e67adbe15156e21d4b2", "990cfbf4-8022-4609-a21d-d25c2072f555",  "Repaso Funcional y reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63234a7be11cf83d158c80dc", "022686ac-9f63-4636-8ac4-37c2129cba51",  "Repaso Introduccion al Desarrollo (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63234969e11cf83d158c80c3", "022686ac-9f63-4636-8ac4-37c2129cba51",  "Repaso Introduccion al Desarrollo (link documentación)"));*/
    }

    public void diagnosticar(Flux<Notas> notas){
        ArrayList<Aprendiz> aprendicesConAccionesDeMejora = new ArrayList<>();

        // Recorre todas las notas
        notas.toStream().forEach(nota -> {

            // se obtiene el objeto aprendiz
            Mono<Aprendiz> aprendiz = getAprendizByEmail(nota.getTrainingId(), nota.getAprendizEmail());

            // se obtienen las tareas de la nota
            nota.getTareasList()
                    .forEach(tarea -> {
                        if(tarea == null || tarea.getNota() == null) return;

                        if(tarea.getNota() < 75){
                            // se obtiene el curso de la nota
                            cursoService.findCursoById(tarea.getCursoId()).subscribe(curso -> {
                                if(curso == null || curso.getAccionMejora()== null) return;
                                // agrega la accion de mejora al aprendiz
                                aprendiz.subscribe(aprendiz1 -> {
                                    aprendiz1.getAccionDeMejoras().add(curso.getAccionMejora());
                                });
                            });
                        }
                    });

        });

        enviarCorreosToAprendicesConAccionesDeMejora(aprendicesConAccionesDeMejora);

    }

    private Mono<Aprendiz> getAprendizByEmail(String trainingId, String aprendizId){
        // se obtienen todos los aprendices de cada training de la nota
        Flux<Aprendiz> aprendices = trainingServices.getAprendicesByTrainingId(trainingId);

        return  aprendices
                .filter(aprendiz1 -> aprendiz1.getEmail().equals(aprendizId))
                .map(aprendiz -> {
                    // SE DEBE ACTUALIZAR LA BD PARA QUE ACCION DE MEJORA EN APRENDIZ NO SEA NULL SINO []
                    aprendiz.setAccionDeMejoras(new ArrayList<>());
                    return aprendiz;
                })
                .next()
                .switchIfEmpty(Mono.empty());
    }

    private void enviarCorreosToAprendicesConAccionesDeMejora(ArrayList<Aprendiz> aprendicesConAccionesDeMejora){
        aprendicesConAccionesDeMejora
                .forEach(aprendiz -> {
                    // enviar correo
                    DetallesDeCorreo detallesDeCorreo = new DetallesDeCorreo();
                    detallesDeCorreo.setSubject("Acciones de Mejora");
                    detallesDeCorreo.setRecipient(aprendiz.getEmail());

                    detallesDeCorreo.setMsgBody(BuildBody(aprendiz));

                    System.out.println(aprendiz.getEmail());
                    //envioDeCorreoService.sendSimpleMail(envioDeCorreoService.TemplateFeedback(detallesDeCorreo));
                    System.out.println(detallesDeCorreo);
                });
    }

    private String BuildBody(Aprendiz aprendiz){
        StringBuilder acciones = new StringBuilder();
        aprendiz.getAccionDeMejoras().forEach(accion -> {
            if(aprendiz.getAccionDeMejoras().size()==1 || acciones.length() == 0) {
                acciones.append(accion);
                return;
            }

            acciones.append("; " + accion );
        });

        return aprendiz.getName() + ", [" + acciones.toString() + "]";
    }

}
