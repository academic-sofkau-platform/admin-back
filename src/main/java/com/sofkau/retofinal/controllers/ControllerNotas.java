package com.sofkau.retofinal.controllers;


import com.sofkau.retofinal.models.Aprendiz;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.models.Notas;
import com.sofkau.retofinal.models.Training;
import com.sofkau.retofinal.services.NotasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@RestController

public class ControllerNotas {
    @Autowired
    private NotasServices service;

    @Autowired
    private ControllerTraining training;

    private HttpClient htpp= HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2).build();

    @Scheduled(cron = "0 0 * * *")
    public void extraerMediaNoche() {

                    final HttpRequest httpRequest = HttpRequest.newBuilder().GET()
                            .uri(URI.create("https://campus.sofka.com.co/api/v1/gettestanswers/test_id:,user_id:4315?apikey=ADDYetylyhWhF5nXUy3Hh1e2vRx4QY")).build();
    }

    @Scheduled(cron = "0 12 * * *")
    public Flux<Notas> extraerMedioDia() {
        return null ;
    }

}
