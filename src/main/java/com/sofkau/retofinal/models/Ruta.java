package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ruta {
    private String id = UUID.randomUUID().toString();
    private Integer nivel;
    private Curso curso;
    private List<Curso> prerrequisitos;
}
