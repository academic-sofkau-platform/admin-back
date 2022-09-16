package com.sofkau.retofinal.dto;

import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.RutaAprendizaje;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrainingDto {
    private String trainingId;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String coach;
    private List<Aprendiz> apprentices;
    private RutaAprendizaje rutaAprendizaje;
    private long apprenticesCount;
    private String periodo;
}
