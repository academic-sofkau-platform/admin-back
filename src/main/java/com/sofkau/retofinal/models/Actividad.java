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

    private String cursoId;
    private String aprendizId;
    private LocalDate fecha;
    private Integer puntaje;
    private String tipo;
    private Integer nota;

}
