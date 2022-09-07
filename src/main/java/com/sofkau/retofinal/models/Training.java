package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection="trainings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    private String trainingId = UUID.randomUUID().toString();
    private RutaAprendizaje rutaAprendizaje;
    private List<Aprendiz> aprendices;
    private String coach;
    private Date startDate;
    private Date endDate;
}
