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

    @Autowired
    private EnvioDeCorreoServiceImpl envioDeCorreoService;
    public ArrayList<AccionDeMejora> accionDeMejoras = new ArrayList<AccionDeMejora>();
    private final ArrayList<Aprendiz> aprendicesConAccionesDeMejora = new ArrayList<>();

    public DiagnosticoRendimientoServiceImpl() {
        this.accionDeMejoras.add(new AccionDeMejora("63226e34adbe15156e21d4ad","7595fb82-db54-490b-91fc-ec0c8e7daaa1",  "Repaso de conceptos de fundamentos de DDD (link de documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63226e4cadbe15156e21d4ae", "7595fb82-db54-490b-91fc-ec0c8e7daaa1",  "Repaso de conceptos de fundamentos de DDD (link de documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63226e4fadbe15156e21d4af", "4149bdc6-f0b4-4f94-a030-385c695a88a7",  "Repaso Reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63234960e11cf83d158c80c3", "7595fb82-db54-490b-91fc-ec0c8e7daaa1",  "Repaso Reactiva  (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63226e62adbe15156e21d4b1", "7595fb82-db54-490b-91fc-ec0c8e7daaa1",  "Repaso Funcional y reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63226e67adbe15156e21d4b2", "990cfbf4-8022-4609-a21d-d25c2072f555",  "Repaso Funcional y reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63234a7be11cf83d158c80dc", "022686ac-9f63-4636-8ac4-37c2129cba51",  "Repaso Introduccion al Desarrollo (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("63234969e11cf83d158c80c3", "022686ac-9f63-4636-8ac4-37c2129cba51",  "Repaso Introduccion al Desarrollo (link documentación)"));
    }

    public void diagnosticar(Flux<Notas> notas){
        // Recorre todas las notas
        notas.toStream().forEach(notas1 -> {

            // se obtiene el objeto aprendiz
            Aprendiz aprendiz = getAprendizById(notas1.getTrainingId(), notas1.getAprendizId());

            // se obtiene las actividades del aprendiz
            List<Actividad> actividades = actividadService.findActivityByAprendizId(notas1.getAprendizId()).collectList().block();

            // si la actividad no existe salta a la siguiente iteración
            if(actividades == null) return;
            AgregarAccionesDeMejora(actividades, aprendiz);
        });

        enviarCorreosToAprendicesConAccionesDeMejora();

    }

    private Aprendiz getAprendizById(String trainingId, String aprendizId){
        // se obtienen todos los aprendices de cada training de la nota
        Flux<Aprendiz> aprendices = trainingServices.getAprendicesByTrainingId(trainingId);

        Aprendiz aprendiz = aprendices
                .filter(aprendiz1 -> aprendiz1.getId().equals(aprendizId))
                .collect(Collectors.toList())
                .block()
                .get(0);

        // SE DEBE ACTUALIZAR LA BD PARA QUE ACCION DE MEJORA EN APRENDIZ NO SEA NULL SINO []
        aprendiz.setAccionDeMejora(new ArrayList<>());

        return aprendiz;
    }

    private Mono<AccionDeMejora> getAccioneDeMejoraByActividadId(String ActividadId) {
        return Flux.fromIterable(this.accionDeMejoras)
                .filter(accionDeMejora -> accionDeMejora.getActividadId().equals(ActividadId))
                .next()
                .switchIfEmpty(Mono.empty());
    }

    private String BuildBody(Aprendiz aprendiz){
        StringBuilder acciones = new StringBuilder();
        aprendiz.getAccionDeMejora().forEach(accion -> {
            if(aprendiz.getAccionDeMejora().size()==1 || acciones.length() == 0) {
                acciones.append(accion);
                return;
            }

            acciones.append("; " + accion );
        });

        return aprendiz.getName() + ", [" + acciones.toString() + "]";
    }

    private void AgregarAccionesDeMejora(List<Actividad> actividades, Aprendiz aprendiz){
        actividades.forEach(actividad -> {

            // si el puntaje de la actividad es menor a 75%
            if(actividad.getPuntaje() < 75){
                AccionDeMejora accionDeMejora = getAccioneDeMejoraByActividadId(actividad.getActividadId()).block();
                // si no existe la accion de mejora salta a la siguiente iteración de actividad

                if(accionDeMejora == null) return;

                // le agrega al aprendiz la accion de mejora si no la tiene aun
                if(!aprendiz.getAccionDeMejora().contains(accionDeMejora.getAccion())){
                    aprendiz.getAccionDeMejora().add(accionDeMejora.getAccion());
                }

                // Agrega al aprendiz a la lista para enviar correo
                if(!this.aprendicesConAccionesDeMejora.contains(aprendiz)){
                    this.aprendicesConAccionesDeMejora.add(aprendiz);
                }

            }
        });
    }

    private void enviarCorreosToAprendicesConAccionesDeMejora(){
        this.aprendicesConAccionesDeMejora
                .forEach(aprendicesConAccionesDeMejora -> {
                    // enviar correo
                    DetallesDeCorreo detallesDeCorreo = new DetallesDeCorreo();
                    detallesDeCorreo.setSubject("Acciones de Mejora");
                    detallesDeCorreo.setRecipient(aprendicesConAccionesDeMejora.getEmail());

                    detallesDeCorreo.setMsgBody(BuildBody(aprendicesConAccionesDeMejora));

                    System.out.println(aprendicesConAccionesDeMejora.getEmail());
                    envioDeCorreoService.sendSimpleMail(envioDeCorreoService.TemplateFeedback(detallesDeCorreo));
                    //System.out.println(detallesDeCorreo);
                });
    }

}
