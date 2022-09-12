package com.sofkau.retofinal.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.UUID;
@Document(collection = "notes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notas {
    private String id = UUID.randomUUID().toString();
    private String trainingId;
    private Aprendiz Aprendiz;
}