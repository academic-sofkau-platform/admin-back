package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="apprentices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aprendiz {
    private String id;
    private String name;
    private String lastName;
    private String city;
    private String gender;
    private String email;
    private Integer phoneNumber;
    private String photo;
    private Boolean bilingual;
    private List <String> accionDeMejora;
}
