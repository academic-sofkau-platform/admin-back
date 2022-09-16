package com.sofkau.retofinal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaDto {
    private Integer nivel;
    private String cursoId;
    private List<String> prerrequisitos;
}
