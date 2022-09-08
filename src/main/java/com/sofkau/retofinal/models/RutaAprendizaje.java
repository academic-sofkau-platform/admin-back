package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "learning-route")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaAprendizaje {
    private String id = UUID.randomUUID().toString();
    private String nombre;
    private String descripcion;
    private List<Ruta> rutas;
}