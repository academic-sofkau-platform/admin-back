package com.sofkau.retofinal.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
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


