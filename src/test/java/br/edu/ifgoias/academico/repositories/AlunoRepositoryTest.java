package br.edu.ifgoias.academico.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.edu.ifgoias.academico.entities.Aluno;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    private Aluno aluno;

    @BeforeEach
    void setup() {
        aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setSexo("M");
        aluno.setDtNasc(Date.valueOf(LocalDate.of(2000, 5, 15))); // 15 de maio de 2000
    }

    @Test
    void deveSalvarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Teste");
        aluno.setSexo("M");
        aluno.setDtNasc(Date.valueOf(LocalDate.of(1995, 10, 20)));

        Aluno alunoSalvo = alunoRepository.save(aluno);
        
        assertThat(alunoSalvo).isNotNull();
        assertThat(alunoSalvo.getIdaluno()).isNotNull();
        assertThat(alunoSalvo.getNome()).isEqualTo("Teste");
    }

    @Test
    void deveBuscarAlunoPorId() {
        Aluno alunoSalvo = alunoRepository.save(aluno);
        Aluno alunoEncontrado = alunoRepository.findById(alunoSalvo.getIdaluno()).orElse(null);

        assertThat(alunoEncontrado).isNotNull();
        assertThat(alunoEncontrado.getNome()).isEqualTo(aluno.getNome());
    }

    @Test
    void deveApagarAluno() {
        Aluno alunoSalvo = alunoRepository.save(aluno);
        alunoRepository.deleteById(alunoSalvo.getIdaluno());

        // Garantir que a transação foi processada no banco
        entityManager.flush();

        boolean existe = alunoRepository.existsById(alunoSalvo.getIdaluno());
        assertThat(existe).isFalse();
    }
}
