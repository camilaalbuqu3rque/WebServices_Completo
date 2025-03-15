package br.edu.ifgoias.academico.repositories;

import br.edu.ifgoias.academico.entities.Curso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CursoRepositoryTest {

    @Autowired
    private CursoRepository cursoRepository;

    private Curso curso;

    @BeforeEach
    void setup() {
        curso = new Curso();
        curso.setNomeCurso("Sistemas de Informação");
        curso = cursoRepository.save(curso);
    }

    @Test
    void deveSalvarCurso() {
        Curso novoCurso = new Curso();
        novoCurso.setNomeCurso("Engenharia de Software");
        novoCurso.setId(null);

        Curso salvo = cursoRepository.save(novoCurso);

        assertThat(salvo).isNotNull();
        assertThat(salvo.getId()).isNotNull();
    }

    @Test
    void deveBuscarCursoPorId() {
        Optional<Curso> encontrado = cursoRepository.findById(curso.getId());

        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNomeCurso()).isEqualTo("Sistemas de Informação");
    }

    @Test
    void deveDeletarCurso() {
        cursoRepository.delete(curso);
        Optional<Curso> deletado = cursoRepository.findById(curso.getId());

        assertThat(deletado).isEmpty();
    }
}
