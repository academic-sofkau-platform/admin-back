package com.sofkau.retofinal.dto;

import com.sofkau.retofinal.models.Curso;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RutaDto {
    private Integer nivel;
    private String cursoId;
    private List<String> prerrequisitosId;
}
