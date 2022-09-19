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
    private Integer aprobacion;
    private String enlace;
    private String consigna;
}
