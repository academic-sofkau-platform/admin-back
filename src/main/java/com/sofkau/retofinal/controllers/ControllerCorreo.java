package com.sofkau.retofinal.controllers;

import com.sofkau.retofinal.models.DetallesDeCorreo;
import com.sofkau.retofinal.services.EnvioDeCorreoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ControllerCorreo {
    @Autowired
    private EnvioDeCorreoServiceImpl service;

    @PostMapping("/sendMail")
    public void sendCorreo(@RequestBody DetallesDeCorreo details){
        service.sendSimpleMail(details);
    }
}
