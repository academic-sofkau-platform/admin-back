package com.sofkau.retofinal.repositories;

import com.sofkau.retofinal.models.Notas;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface NotasRepository extends ReactiveMongoRepository<Notas, String> {
}
