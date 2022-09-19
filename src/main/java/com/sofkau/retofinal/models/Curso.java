package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
    private String id = UUID.randomUUID().toString();
    private String nombre;
    private String descripcion;
    private Integer aprobacion;
    private String enlace;
    private String consigna;
}
