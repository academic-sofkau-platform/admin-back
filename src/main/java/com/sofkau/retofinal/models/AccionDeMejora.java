package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "accionesDeMejoras")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccionDeMejora {
    @Id
    private String id = UUID.randomUUID().toString();
    private String TareaId;
    private String accion;


}
