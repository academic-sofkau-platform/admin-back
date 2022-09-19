package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection="task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tarea {

    @Id
    private String tareaId =  UUID.randomUUID().toString();
    private String cursoId;
    private String tipo;
    private Integer nota;
    private String resultado;
}
