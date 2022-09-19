package com.sofkau.retofinal.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document(collection = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notas {

    @Id
    private String aprendizId ;
    private String trainingId;
    private List<Tarea> tareasList = new ArrayList<>();

}


