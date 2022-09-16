package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingAuxiliar {
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private String coach;
    private String apprentices;
    private String rutaId;
}
