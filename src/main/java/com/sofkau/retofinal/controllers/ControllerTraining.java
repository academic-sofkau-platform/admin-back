package com.sofkau.retofinal.controllers;
import com.sofkau.retofinal.dto.TrainingDto;
import com.sofkau.retofinal.models.*;
import com.sofkau.retofinal.services.TrainingServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.ArrayList;
import java.util.List;

import static com.sofkau.retofinal.utils.AppUtils.*;

@RestController
@RequestMapping("/trainings")
@CrossOrigin("*")
public class ControllerTraining {

    @Autowired
    TrainingServicesImpl service;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<TrainingDto>> save(@RequestBody TrainingAuxiliar trainingAuxiliar) {
        return service.save(armarTraining(trainingAuxiliar))
                 .flatMap(trainingDto -> Mono.just(ResponseEntity.ok(trainingDto)))
                 .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
    @GetMapping("/findAllTrainings")
    @ResponseStatus(HttpStatus.OK)
    public Flux<TrainingDto> findAllTrainings() {
        return service.findAll();
    }
    @GetMapping("/findById/{id}")
    public Mono<TrainingDto> findById(@PathVariable("id") String trainingId) {
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

    @PutMapping("/addtarea/{trainingId}/{email}")
    public Mono<TrainingDto> addTarea(@RequestBody Tarea tarea,
                                 @PathVariable("trainingId") String trainingId, @PathVariable("aprendizId") String aprendizId){
        return service.addtarea( trainingId, aprendizId,tarea);
    }
    @PostMapping("/updateTarea/{trainingId}/{email}")
    public Mono<TrainingDto> updateTarea(@RequestBody Tarea tarea,
                                         @PathVariable("trainingId") String trainingId,
                                         @PathVariable("email") String email){
        return service.updateTarea(tarea,trainingId,email);
    }
    @PostMapping("/updateNotaTarea/{trainingId}/{email}")
    public Mono<TrainingDto> updateNotaTarea(@RequestBody Tarea tarea,
                                         @PathVariable("trainingId") String trainingId,
                                         @PathVariable("email") String email){
        return service.updateNotaTarea(tarea,trainingId,email);
    }

    @PutMapping("/update/{id}")
    public Mono<TrainingDto> update(@RequestBody Training training,
                                 @PathVariable("id") String trainingId){
        return service.update(training, trainingId);
    }
    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteTrainingById(@PathVariable("id") String trainingId) {
        return service.deleteById(trainingId);
    }
    @PutMapping("/update/coach/{id}")
    public Mono<TrainingDto> asignarCoach(@RequestBody String name,
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
        //return service.getActiveTrainings();
        return service.getActiveTrainingComplete();
    }


    @GetMapping("/aprendices/{trainingId}/{email}")
    public Mono<Aprendiz> getAllAprendicesByTrainingIdAndEmail(@PathVariable("trainingId") String trainingId,
                                                               @PathVariable("email") String emailId) {
        return service.getAprendicesByTrainingId(trainingId)
                .filter(aprendiz -> aprendiz.getEmail().equals(emailId))
                .next();
    }

    @GetMapping("/getAprendicesByTrainingId/{trainingId}")
    public Flux<Aprendiz> getAprendicesByTrainingId(@PathVariable("trainingId") String trainingId){
        return service.getAprendicesByTrainingId(trainingId);
    }

    @PostMapping("/deleteAprendiz/{trainingId}")
    public Mono<TrainingDto> delete(@PathVariable("trainingId") String trainingId, @RequestBody String email){
        return service.deleteAprendizByEmail(trainingId,email);
    }


    //Agregar aprendiz a un training activo
    @PutMapping("/agregarAprendices/{trainingId}")
    public Mono<TrainingDto> agregarAprendiz(@PathVariable("trainingId") String trainingId,
                                             @RequestBody List<Aprendiz> aprendices){
        return service.agregarAprendices(trainingId, aprendices);
    }

    @GetMapping("/getResultadoCursos")
    public Flux<ResultadoCursoList> getResultadoCursos(){
        return service.getResultadoCursos();
    }

}
