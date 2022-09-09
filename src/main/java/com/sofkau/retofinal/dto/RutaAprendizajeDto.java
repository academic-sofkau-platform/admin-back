package com.sofkau.retofinal.dto;

import com.sofkau.retofinal.models.Ruta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaAprendizajeDto {
    private String id ;
    private String nombre;
    private String descripcion;
    private List<Ruta> rutas;
}
