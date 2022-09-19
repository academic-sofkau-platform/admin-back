package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection="apprentices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aprendiz {
    @Id
    private String email;
    private String name;
    private String lastName;
    private String city;
    private String gender;
    private String phoneNumber;
    private String photo;
    private List <Tarea> tareas = new ArrayList<>();
    private Boolean bilingual;
    private List <String> accionDeMejoras = new ArrayList<>();
}
