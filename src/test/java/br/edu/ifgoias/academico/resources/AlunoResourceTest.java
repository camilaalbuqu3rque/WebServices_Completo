package br.edu.ifgoias.academico.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.services.AlunoService;

@ExtendWith(MockitoExtension.class)
class AlunoResourceTest {

    @InjectMocks
    private AlunoResource resource;

    @Mock
    private AlunoService servico;

    private Aluno aluno;

    @BeforeEach
    void setup() {
        aluno = new Aluno(1, "João Silva", "M", Date.valueOf(LocalDate.of(2000, 5, 15)));
    }

    @Test
    void deveRetornarListaDeAlunos() {
        List<Aluno> alunos = Arrays.asList(aluno, new Aluno(2, "Maria Souza", "F", Date.valueOf(LocalDate.of(1998, 8, 20))));

        when(servico.findAll()).thenReturn(alunos);

        ResponseEntity<List<Aluno>> response = resource.findAll();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getNome()).isEqualTo("João Silva");
    }

    @Test
    void deveRetornarAlunoPorId() {
        when(servico.findById(1)).thenReturn(aluno);

        ResponseEntity<Aluno> response = resource.findById(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNome()).isEqualTo("João Silva");
    }

    @Test
    void deveInserirAluno() {
        when(servico.insert(any(Aluno.class))).thenReturn(aluno);

        ResponseEntity<Aluno> response = resource.insert(aluno);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getId()).isEqualTo(aluno.getId());
        assertThat(response.getBody().getNome()).isEqualTo(aluno.getNome());
    }

    @Test
    void deveDeletarAluno() {
        doNothing().when(servico).delete(1);

        resource.delete(1);

        verify(servico, times(1)).delete(1);
    }

    @Test
    void deveAtualizarAluno() {
        when(servico.update(eq(1), any(Aluno.class))).thenReturn(aluno);

        ResponseEntity<Aluno> response = resource.update(1, aluno);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(aluno.getId());
        assertThat(response.getBody().getNome()).isEqualTo(aluno.getNome());
    }
}
