package com.sofkau.retofinal.repositories;

import com.sofkau.retofinal.models.Actividad;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActividadRepository extends ReactiveMongoRepository<Actividad, String> {
}
