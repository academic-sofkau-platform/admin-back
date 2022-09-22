package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoCursoList {
    private List<Aprendiz> aprendizList;
    private String trainingName;
    private List<Curso> curso;
    private String rutaAprendizajeId;
}
