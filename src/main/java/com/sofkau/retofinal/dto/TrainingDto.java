package com.sofkau.retofinal.dto;

import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.RutaAprendizaje;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDto {
    private String trainingId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String coach;
    private List<Aprendiz> apprentices;
    private RutaAprendizaje rutaAprendizaje;
}

