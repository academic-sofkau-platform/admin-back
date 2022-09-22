package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document(collection="trainings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    @Id
    private String trainingId = UUID.randomUUID().toString();
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String coach;
    private List<Aprendiz> apprentices;
    private String rutaAprendizajeId;
}
