package com.sofkau.retofinal.repositories;

import com.sofkau.retofinal.models.RutaAprendizaje;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RutaAprendizajeRepository extends ReactiveMongoRepository<RutaAprendizaje, String> {
}
