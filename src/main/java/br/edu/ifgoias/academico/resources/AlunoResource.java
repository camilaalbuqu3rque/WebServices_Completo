package br.edu.ifgoias.academico.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.services.AlunoService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/alunos")
public class AlunoResource {

    private AlunoService servico;

    @GetMapping
    public ResponseEntity<List<Aluno>> findAll() {
        List<Aluno> alunos = servico.findAll();
        return ResponseEntity.ok().body(alunos);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Aluno> findById(@PathVariable Integer id) {
        Aluno aluno = servico.findById(id);
        return ResponseEntity.ok().body(aluno);
    }

    @PostMapping
    public ResponseEntity<Aluno> insert(@RequestBody Aluno a) {
        a = servico.insert(a);
        return ResponseEntity.status(HttpStatus.CREATED).body(a); 
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        servico.delete(id);
        return ResponseEntity.noContent().build(); 
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Integer id, @RequestBody Aluno a) {
        a = servico.update(id, a);
        return ResponseEntity.ok().body(a);
    }
}
