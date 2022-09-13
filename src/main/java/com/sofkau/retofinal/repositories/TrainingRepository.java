package com.sofkau.retofinal.repositories;

import com.sofkau.retofinal.models.Training;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface TrainingRepository extends ReactiveMongoRepository<Training, String> {
}
