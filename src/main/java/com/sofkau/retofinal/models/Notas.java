package com.sofkau.retofinal.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;
@Document(collection = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notas {

    @Id
    private String aprendizId;
    private String trainingI;
    private List<Actividad> actividadList;

}


