package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaAprendiz {

    private String rutaId;
    private String cursoId;
    private String nombreCurso;
    private Integer nivel;
    private List<String> prerrequisitos;
    private Tarea tarea;
}