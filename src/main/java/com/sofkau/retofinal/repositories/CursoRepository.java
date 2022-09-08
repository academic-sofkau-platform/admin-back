package com.sofkau.retofinal.repositories;
import com.sofkau.retofinal.models.Curso;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository  extends ReactiveMongoRepository<Curso, String> {
}
