package br.edu.ifgoias.academico.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.services.AlunoService;

@ExtendWith(MockitoExtension.class) // Adicionando extensão do Mockito
class AlunoResourceTest {

    @InjectMocks
    private AlunoResource resource; // Agora injetamos o mock manualmente

    @Mock
    private AlunoService servico; // Criamos um mock do serviço

    @Test
    void deveRetornarListaDeAlunos() {
        // Criando alunos fictícios
        List<Aluno> alunos = Arrays.asList(
            new Aluno(1, "João Silva", "M", Date.valueOf(LocalDate.of(2000, 5, 15))),
            new Aluno(2, "Maria Souza", "F", Date.valueOf(LocalDate.of(1998, 8, 20)))
        );

        // Simulando comportamento do serviço
        when(servico.findAll()).thenReturn(alunos);

        // Chamando o método do recurso
        ResponseEntity<List<Aluno>> response = resource.findAll();

        // Verificando se a resposta está correta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getNome()).isEqualTo("João Silva");
    }

    @Test
    void deveRetornarAlunoPorId() {
        // Criando aluno fictício
        Aluno aluno = new Aluno(1, "João Silva", "M", Date.valueOf(LocalDate.of(2000, 5, 15)));

        // Simulando comportamento do serviço
        when(servico.findById(1)).thenReturn(aluno);

        // Chamando o método do recurso
        ResponseEntity<Aluno> response = resource.findById(1);

        // Verificando se a resposta está correta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNome()).isEqualTo("João Silva");
    }
}
