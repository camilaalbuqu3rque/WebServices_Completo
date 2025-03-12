package br.edu.ifgoias.academico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.repositories.CursoRepository;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRep;

    public List<Curso> findAll() {
        return cursoRep.findAll(); 
    }

    public Curso findById(Integer id) {
        return cursoRep.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
    }

    public Curso insert(Curso obj) {
        if (obj == null || obj.getNomecurso() == null || obj.getNomecurso().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos para inserção");
        }
        return cursoRep.save(obj);
    }

    public void delete(Integer id) {
        if (!cursoRep.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado para exclusão");
        }
        cursoRep.deleteById(id);
    }

    public Curso update(Integer id, Curso objAlterado) {
        if (objAlterado == null || objAlterado.getNomecurso() == null || objAlterado.getNomecurso().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados inválidos para atualização");
        }

        return cursoRep.findById(id)
                .map(cursoDB -> {
                    cursoDB.setNomecurso(objAlterado.getNomecurso());
                    return cursoRep.save(cursoDB);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));
    }

    public Curso buscarCursoPorId(Integer id) {
        return findById(id);
    }
}
