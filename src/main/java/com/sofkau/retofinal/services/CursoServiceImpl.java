package com.sofkau.retofinal.services;

import com.sofkau.retofinal.dto.CursoDto;
import com.sofkau.retofinal.interfaces.ICursoService;
import com.sofkau.retofinal.models.Curso;
import com.sofkau.retofinal.repositories.CursoRepository;
import com.sofkau.retofinal.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CursoServiceImpl implements ICursoService {
    @Autowired
    CursoRepository repository;
    @Override
    public Mono<CursoDto> save(Curso curso) {
        return repository.save(curso).thenReturn(AppUtils.cursoToDto(curso));
    }

    @Override
    public Flux<CursoDto> findAll() {
        return AppUtils.cursoListToDto(repository.findAll());
    }

    @Override
    public Mono<CursoDto> update(Curso curso, String cursoId) {
        Mono<Curso> curso1 = repository.findById(cursoId);
        return curso1.flatMap(curso2 -> {
            curso2.setNombre(curso.getNombre());
            curso2.setDescripcion(curso.getDescripcion());
            curso2.setAccionMejora(curso.getAccionMejora());
            curso2.setConsigna(curso.getConsigna());
            curso2.setEnlace(curso.getEnlace());
            curso2.setAprobacion(curso.getAprobacion());
            return repository.save(curso2).thenReturn(AppUtils.cursoToDto(curso2));
        }).switchIfEmpty(Mono.empty());
    }

    @Override
    public Mono<Void> deleteById(String cursoId) {
        return repository.deleteById(cursoId);
    }

    public Mono<Curso> findCursoById(String cursoId){
        return findAll().filter(cursoDto -> cursoDto.getId().equals(cursoId))
                .map(cursoDto -> AppUtils.dtoToCurso(cursoDto))
                .next();
    }
}
