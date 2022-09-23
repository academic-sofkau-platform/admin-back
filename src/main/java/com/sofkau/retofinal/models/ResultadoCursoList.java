package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoCursoList {
    private Aprendiz aprendiz;
    private String trainingName;
    private Curso curso;
    private String rutaAprendizajeId;
}
