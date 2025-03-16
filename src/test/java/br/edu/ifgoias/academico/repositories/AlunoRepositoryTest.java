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

// Essa classe tem três testes principais que validam se as operações básicas de banco de dados (CRUD) estão funcionando corretamente.

@ExtendWith(SpringExtension.class) // Adiciona suporte do JUnit 5 para rodar os testes
@DataJpaTest //Configura o teste para usar apenas as funcionalidades do Spring Data JPA
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Diz ao Spring para NÃO substituir o banco por um em memória (usa um banco real)
class AlunoRepositoryTest {  

    @Autowired
    private AlunoRepository alunoRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    private Aluno aluno;

    @BeforeEach
    	void setup() { 
        alunoRepository.deleteAll(); // Limpa o banco antes de cada teste
        aluno = new Aluno();
        aluno.setNome("João Silva");
        aluno.setSexo("M");
        aluno.setDtNasc(Date.valueOf(LocalDate.of(2000, 5, 15))); // 15 de maio de 2000
    }

    @Test
    void deveSalvarAluno() {
        Aluno novoAluno = new Aluno();
        novoAluno.setNome("Teste"); 
        novoAluno.setSexo("M");
        novoAluno.setDtNasc(Date.valueOf(LocalDate.of(1995, 10, 20)));

        Aluno alunoSalvo = alunoRepository.save(novoAluno);
        
        assertThat(alunoSalvo).isNotNull(); // Verifica se o aluno foi salvo
        assertThat(alunoSalvo.getIdaluno()).isNotNull(); // Verifica se o ID foi gerado
        assertThat(alunoSalvo.getNome()).isEqualTo("Teste"); // Verifica se o nome está correto
    }

    @Test
    void deveBuscarAlunoPorId() {
        Aluno alunoSalvo = alunoRepository.save(aluno);
        Aluno alunoEncontrado = alunoRepository.findById(alunoSalvo.getIdaluno()).orElse(null);

        assertThat(alunoEncontrado).isNotNull(); // Verifica se o aluno foi encontrado
        assertThat(alunoEncontrado.getNome()).isEqualTo(aluno.getNome()); // Compara os nomes
    }

    @Test
    @Transactional
    void deveApagarAluno() {
        Aluno alunoSalvo = alunoRepository.save(aluno);
        alunoRepository.deleteById(alunoSalvo.getIdaluno());

        // Garantir que a transação foi processada no banco
        entityManager.flush();

        boolean existe = alunoRepository.existsById(alunoSalvo.getIdaluno());
        assertThat(existe).isFalse(); // Verifica se o aluno foi removido
    }
}
