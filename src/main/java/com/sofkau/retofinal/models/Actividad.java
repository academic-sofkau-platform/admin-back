package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.UUID;

@Document(collection="activity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Actividad {

    @Id
    private String actividadId = UUID.randomUUID().toString();
    private String cursoId;
    private String aprendizId;
    private LocalDate fecha;
    private Integer puntaje;
    //TODO: PONER LA ENTIDAD NOTAS EN ACTIVIDAD


    public Actividad(String cursoId, String aprendizId, LocalDate fecha, Integer puntaje) {
        this.cursoId = cursoId;
        this.aprendizId = aprendizId;
        this.fecha = fecha;
        this.puntaje = puntaje;
    }
}
