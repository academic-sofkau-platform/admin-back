package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.TrainingDto;
import com.sofkau.retofinal.models.*;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;


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

    //public ArrayList<Aprendiz> aprendicesConAccionesDeMejora = new ArrayList<>();

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
        ArrayList<Aprendiz> aprendicesConAccionesDeMejora2 = new ArrayList<>();

        // Recorre todas las notas
        notas.toStream().forEach(nota -> {

            // se obtiene el objeto aprendiz
            Aprendiz aprendiz = trainingServices.getAprendizByTrainingIdAndEmail(nota.getTrainingId(), nota.getAprendizEmail()).block();
            if(aprendiz == null) return;
            aprendiz.setAccionDeMejoras(new ArrayList<>());

            // se obtienen las tareas de la nota
            nota.getTareasList().forEach(tarea -> {
                if(tarea == null || tarea.getNota() == null) return;

                AgregarAccionesMejora(aprendicesConAccionesDeMejora2, tarea, aprendiz);
            });

        });
        persistirAccionesMejora(notas, aprendicesConAccionesDeMejora2);
        //enviarCorreosToAprendicesConAccionesDeMejora(aprendicesConAccionesDeMejora2);
    }

    // todo: actualizar aprendices con acciones de mejora
    private void persistirAccionesMejora(Flux<Notas> notas, ArrayList<Aprendiz> aprendicesConAccionesDeMejora2){
        // recorre todos los aprendices con acciones de mejora
        aprendicesConAccionesDeMejora2.forEach(aprendiz -> {
            // se obtiene el training donde está el aprendiz
            String trainingId = notas.filter(nota1 -> nota1.getAprendizEmail().equals(aprendiz.getEmail()))
                    .next()
                    .map(Notas::getTrainingId)
                    .block();
            Training training = trainingServices.findById(trainingId)
                    .map(AppUtils::dtoToTraining)
                    .block();
            if(training == null) return;

            // se crea una nueva lista de aprendices basada en la del training
            training.getApprentices().forEach(aprendiz1 -> {
                // busca el aprendiz con acciones de mejora y se las setea
                if(aprendiz1.getEmail().equals(aprendiz.getEmail())){
                    aprendiz1.setAccionDeMejoras(aprendiz.getAccionDeMejoras());
                }
            });

            //training.setApprentices(newAprendices);
            Mono<TrainingDto> trainingDtoMono = trainingServices.update(training, trainingId);
            System.out.println(trainingDtoMono);
        });
    }

    private void agregarAprendizALista(ArrayList<Aprendiz> aprendicesConAccionesDeMejora2, Aprendiz aprendiz){

        boolean está = aprendicesConAccionesDeMejora2.stream().anyMatch(aprendiz1 -> aprendiz1.getEmail().equals(aprendiz.getEmail()));
        if(!está){
            aprendicesConAccionesDeMejora2.add(aprendiz);
        }
    }

    private void enviarCorreosToAprendicesConAccionesDeMejora(ArrayList<Aprendiz> aprendicesConAccionesDeMejora2){
        aprendicesConAccionesDeMejora2
                .forEach(aprendiz -> {
                    // enviar correo
                    DetallesDeCorreo detallesDeCorreo = new DetallesDeCorreo();
                    detallesDeCorreo.setSubject("Acciones de Mejora");
                    detallesDeCorreo.setRecipient(aprendiz.getEmail());

                    detallesDeCorreo.setMsgBody(buildBody(aprendiz));
                    envioDeCorreoService.sendSimpleMail(envioDeCorreoService.TemplateFeedback(detallesDeCorreo));
                    //System.out.println(BuildBody(aprendiz));
                });
    }

    private void AgregarAccionesMejora(ArrayList<Aprendiz> aprendicesConAccionesDeMejora2, Tarea tarea, Aprendiz aprendiz){
        // se obtiene el curso de la nota
        Curso curso = cursoService.findCursoById(tarea.getCursoId()).block();
        if(curso == null || curso.getAccionMejora()== null) return;

        var promedio = (tarea.getNota()*100)/curso.getAprobacion();
        if(promedio < 75){

            // agrega la accion de mejora al aprendiz
            if(!aprendiz.getAccionDeMejoras().contains(curso.getAccionMejora())){
                aprendiz.getAccionDeMejoras().add(curso.getAccionMejora());
            }

            // Agrega al aprendiz a la lista para enviar correo
            agregarAprendizALista(aprendicesConAccionesDeMejora2, aprendiz);
        }
    }

    private String buildBody(Aprendiz aprendiz){
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
