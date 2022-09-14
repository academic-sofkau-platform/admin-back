package com.sofkau.retofinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActividadDto {
    private String cursoId;
    private String aprendizId;
    private String fecha;
    private Integer puntaje;
}
