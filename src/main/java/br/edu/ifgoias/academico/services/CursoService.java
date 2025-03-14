package br.edu.ifgoias.academico.services;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.repositories.CursoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRep;

    public CursoService(CursoRepository cursoRep) {
        this.cursoRep = cursoRep; 
    } 

    public List<Curso> findAll() {
        return cursoRep.findAll();
    }

    public Curso findById(Long id) {
        return cursoRep.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
    }

    public Curso insert(Curso curso) {
        return cursoRep.save(curso);
    }

    public Curso update(Long id, Curso curso) {
        return cursoRep.findById(id)
                .map(existingCurso -> {
                    existingCurso.setNomeCurso(curso.getNomeCurso()); 
                    return cursoRep.save(existingCurso);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
    }

    public void delete(Long id) {
        Curso curso = findById(id);
        cursoRep.delete(curso);
    }
}
