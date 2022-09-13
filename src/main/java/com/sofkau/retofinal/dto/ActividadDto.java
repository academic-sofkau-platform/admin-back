package com.sofkau.retofinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActividadDto {

    private String actividadId;
    private String cursoId;
    private String aprendizId;
    private LocalDate fecha;
    private Integer puntaje;
    private String tipo;
    private Integer nota;
}
