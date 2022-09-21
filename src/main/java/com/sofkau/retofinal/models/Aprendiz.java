package com.sofkau.retofinal.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Document(collection="apprentices")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Aprendiz {
    @Id
    @Email
    private String email;
    @NotBlank(message = "Name is mandatory")
    @Size(max = 100)
    private String name;
    @NotBlank(message = "LastName is mandatory")
    @Size(max = 100)
    private String lastName;
    private String city;
    private String gender;
    private String phoneNumber;
    private String photo;
    private List <Tarea> tareas = new ArrayList<>();
    private Boolean bilingual;
    private List <String> accionDeMejoras = new ArrayList<>();
}
