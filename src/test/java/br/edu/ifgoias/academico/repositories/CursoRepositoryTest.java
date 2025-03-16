package br.edu.ifgoias.academico.repositories;

import br.edu.ifgoias.academico.entities.Curso;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

//Essa classe testa as operações básicas (CRUD) do CursoRepository, garantindo que os métodos de inserção, busca e remoção de cursos no banco de dados funcionem corretamente.

@DataJpaTest // Configura o Spring Boot para rodar apenas testes na camada de persistência (JPA)
class CursoRepositoryTest { 

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

        assertThat(salvo).isNotNull(); // Verifica se o curso foi salvo
        assertThat(salvo.getId()).isNotNull(); // Verifica se o ID foi gerado
    }

    @Test
    void deveBuscarCursoPorId() {
        Optional<Curso> encontrado = cursoRepository.findById(curso.getId()); 

        assertThat(encontrado).isPresent(); // Verifica se o curso foi encontrado
        assertThat(encontrado.get().getNomeCurso()).isEqualTo("Sistemas de Informação"); // Compara os nomes
    }

    @Test
    void deveDeletarCurso() {
        cursoRepository.delete(curso);
        Optional<Curso> deletado = cursoRepository.findById(curso.getId());

        assertThat(deletado).isEmpty(); // Verifica se o curso foi removido
    }
}
