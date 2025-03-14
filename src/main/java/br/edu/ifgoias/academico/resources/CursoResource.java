package br.edu.ifgoias.academico.resources;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.services.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoResource {

    private final CursoService cursoService; 

    public CursoResource(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public ResponseEntity<List<Curso>> getAllCursos() {
        return ResponseEntity.ok(cursoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Curso> createCurso(@RequestBody Curso curso) {
        Curso savedCurso = cursoService.insert(curso);
        return ResponseEntity.status(201).body(savedCurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> updateCurso(@PathVariable Long id, @RequestBody Curso curso) {
        return ResponseEntity.ok(cursoService.update(id, curso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
