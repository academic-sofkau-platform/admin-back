package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoCursoList {
    private String nombreAprendiz;
    private String apellido;
    private String email;
    private String trainingName;
    private String trainingId;
}



