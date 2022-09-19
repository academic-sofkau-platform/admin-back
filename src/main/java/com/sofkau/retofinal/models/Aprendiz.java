package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection="apprentices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aprendiz {
    @Id
    private String id;
    private String name;
    private String lastName;
    private String city;
    private String gender;
    private String email;
    private String phoneNumber;
    private String photo;
    private List <Tarea> tareas = new ArrayList<>();
    private Boolean bilingual;
    private List <String> accionDeMejoras = new ArrayList<>();
}
