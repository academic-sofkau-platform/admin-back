package com.sofkau.retofinal.controllers;
import com.sofkau.retofinal.dto.TrainingDto;
import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.services.TrainingServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trainings")
@CrossOrigin("*")
public class ControllerTraining {
    //Todo control de errores del post
    //Todo Dto
    //Todo control de respuesta http
    @Autowired
    TrainingServicesImpl service;
    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Training> save(@RequestBody Training training) {
        return service.save(training);
    }
    @GetMapping("/findAllTrainings")
    public Flux<Training> findAllTrainings() {
        return service.findAll();
    }
    @GetMapping("/findById/{id}")
    public Mono<Training> findById(@PathVariable("id") String trainingId) {
        return service.findById(trainingId);
    }

    //Todo Reparar
/*    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<Training>> update(@RequestBody Training training,
                                                 @PathVariable("id") String trainingId) {
        return service.update(training, trainingId)
                .flatMap(training1 -> Mono.just(ResponseEntity.ok(training1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }*/
    @PutMapping("/update/{id}")
    public Mono<Training> update(@RequestBody Training training,
                                 @PathVariable("id") String trainingId){
        return service.update(training, trainingId);
    }
    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteTrainingById(@PathVariable("id") String trainingId) {
        return service.deleteById(trainingId);
    }
    @PutMapping("/update/coach/{id}")
    public Mono<Training> asignarCoach(@RequestBody String name,
                                       @PathVariable("id") String trainingId) {
        return service.asignarCoach(name, trainingId);
    }

    //TODO: findAll de aprendices
    @GetMapping("/findAllAprendices")
    public Flux<Aprendiz> findAllApredices() {
        List<Aprendiz> listaAprendices = new ArrayList<>();
        return null;
    }

    @GetMapping("/findAllTrainingActivos")
    public Flux<TrainingDto> findAllTrainingActivos() {

        return service.getActiveTrainings();
    }


    @GetMapping("/aprendices/{trainingId}/{email}")
    public Mono<Aprendiz> getAllAprendicesByTrainingIdAndEmail(@PathVariable("trainingId") String trainingId, @PathVariable("email") String emailId) {
        return service.getAprendicesByTrainingId(trainingId)
                .filter(aprendiz -> aprendiz.getEmail().equals(emailId))
                .next();
    }

    @GetMapping("/getAprendicesByTrainingId/{trainingId}")
    public Flux<Aprendiz> getAprendicesByTrainingId(@PathVariable("trainingId") String trainingId){
        return service.getAprendicesByTrainingId(trainingId);
    }

    //TODO delete aprendiz por id de training y email
    @PostMapping("/deleteAprendiz/{trainingId}")
    public Mono<Void> delete(@PathVariable("trainingId") String trainingId, @RequestBody String email){
        return service.deleteAprendizByEmail(trainingId,email);
    }

    //Todo cargarListaAprendiz csv base64 body con json o parametros

    //Todo update aprendiz


}
