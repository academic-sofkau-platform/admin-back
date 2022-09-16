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
    public ArrayList<Aprendiz> aprendicesConAccionesDeMejora = new ArrayList<>();

    public DiagnosticoRendimientoServiceImpl() {
        this.accionDeMejoras.add(new AccionDeMejora("7595fb82-db54-490b-91fc-ec0c8e7daaa1", "Test 1", "Repaso de conceptos de fundamentos de DDD (link de documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("7595fb82-db54-490b-91fc-ec0c8e7daaa1", "Test 2", "Repaso de conceptos de fundamentos de DDD (link de documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("4149bdc6-f0b4-4f94-a030-385c695a88a7", "Test 1", "Repaso Reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("7979bb47-d347-4a42-a648-75abdf637886", "Quiz 3", "Repaso Reactiva  (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("990cfbf4-8022-4609-a21d-d25c2072f555", "Quiz 1", "Repaso Funcional y reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("990cfbf4-8022-4609-a21d-d25c2072f555", "Test 2", "Repaso Funcional y reactiva (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("022686ac-9f63-4636-8ac4-37c2129cba51", "Quiz 3", "Repaso Introduccion al Desarrollo (link documentación)"));
        this.accionDeMejoras.add(new AccionDeMejora("022686ac-9f63-4636-8ac4-37c2129cba51", "Test 2", "Repaso Introduccion al Desarrollo (link documentación)"));
    }

    public Aprendiz getAprendizById(String trainingId, String aprendizId){
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

    public String BuildBody(Aprendiz aprendiz){
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

    public void diagnosticar(Flux<Notas> notas){
        // Recorre todas las notas
        notas.toStream().forEach(notas1 -> {
            // se obtiene el objeto aprendiz
            Aprendiz aprendiz = getAprendizById(notas1.getTrainingId(), notas1.getAprendizId());
            System.out.println(aprendiz.getName());

            // se obtiene las actividades del aprendiz
            var actividades = actividadService.findActivityByAprendizId(notas1.getAprendizId()).collectList().block();
            if(actividades != null && actividades.size() > 0){
                actividades.forEach(actividad -> {
                    if(actividad.getPuntaje() < 75){
                        AccionDeMejora accionDeMejora = getAccionDeMejoraByCursoId(actividad.getCursoId()).block();
                        if(accionDeMejora != null ) {

                            // le agrega al aprendiz la accion de mejora
                            setAprendizAccionesDeMejora(aprendiz, accionDeMejora);

                            if(!this.aprendicesConAccionesDeMejora.contains(aprendiz)){
                                this.aprendicesConAccionesDeMejora.add(aprendiz);
                            }

                            // System.out.println(aprendiz);
                        }
                    }
                });
            }
        });

        this.aprendicesConAccionesDeMejora.stream()
                .forEach(acadm -> {
                    // enviar correo
                    DetallesDeCorreo detallesDeCorreo = new DetallesDeCorreo();
                    detallesDeCorreo.setSubject("Acciones de Mejora");
                    //detallesDeCorreo.setRecipient(aprendiz.getEmail());
                    detallesDeCorreo.setRecipient("bianchi.elias@gmail.com");

                    detallesDeCorreo.setMsgBody(BuildBody(acadm));

                    envioDeCorreoService.sendSimpleMail(envioDeCorreoService.TemplateFeedback(detallesDeCorreo));
                });
    }

}
