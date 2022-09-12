package com.sofkau.retofinal.controllers;


import com.sofkau.retofinal.dto.NotasDto;
import com.sofkau.retofinal.dto.RutaAprendizajeDto;
import com.sofkau.retofinal.services.NotasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class ControllerNotas {
    @Autowired
    private NotasServices service;

}
