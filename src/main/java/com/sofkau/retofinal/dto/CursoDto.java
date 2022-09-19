package com.sofkau.retofinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CursoDto {
    private String id ;
    private String nombre;
    private String descripcion;
    private String consigna;
    private String enlace;
    private Integer aprobacion;
}
