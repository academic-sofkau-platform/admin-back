package com.sofkau.retofinal.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TareasAprendiz {
    private Tarea tarea;
    private String nombreCurso;
}
