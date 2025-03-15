package br.edu.ifgoias.academico.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.services.CursoService;

@ExtendWith(MockitoExtension.class) // Ativa o Mockito para injeção de dependências
class CursoResourceTest {

    @InjectMocks
    private CursoResource cursoResource;

    @Mock
    private CursoService cursoService;

    @Test
    void deveRetornarListaDeCursos() {
        // Simulando cursos fictícios
        List<Curso> cursos = Arrays.asList(new Curso(1L, "Sistemas de Informação"), new Curso(2L, "Engenharia de Software"));
        
        // Definindo o comportamento do mock
        when(cursoService.findAll()).thenReturn(cursos);

        // Chamando o método da API
        ResponseEntity<List<Curso>> response = cursoResource.getAllCursos();

        // Validando a resposta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0).getNomeCurso()).isEqualTo("Sistemas de Informação");
    }

    @Test
    void deveRetornarCursoPorId() {
        // Criando um curso fictício
        Curso curso = new Curso(1L, "Sistemas de Informação");

        // Simulando comportamento do serviço
        when(cursoService.findById(1L)).thenReturn(curso);

        // Chamando o método da API
        ResponseEntity<Curso> response = cursoResource.getCursoById(1L);

        // Verificando a resposta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNomeCurso()).isEqualTo("Sistemas de Informação");
    }

    @Test
    void deveCriarCurso() {
        // Criando um curso fictício
        Curso curso = new Curso(3L, "Ciência da Computação");

        // Simulando a inserção
        when(cursoService.insert(curso)).thenReturn(curso);

        // Chamando o método da API
        ResponseEntity<Curso> response = cursoResource.createCurso(curso);

        // Verificando a resposta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody().getNomeCurso()).isEqualTo("Ciência da Computação");
    }

    @Test
    void deveAtualizarCurso() {
        // Criando um curso fictício atualizado
        Curso curso = new Curso(1L, "Engenharia de Software");

        // Simulando a atualização
        when(cursoService.update(1L, curso)).thenReturn(curso);

        // Chamando o método da API
        ResponseEntity<Curso> response = cursoResource.updateCurso(1L, curso);

        // Verificando a resposta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNomeCurso()).isEqualTo("Engenharia de Software");
    }

    @Test
    void deveDeletarCurso() {
        // Simulando a exclusão
        doNothing().when(cursoService).delete(1L);

        // Chamando o método da API
        ResponseEntity<Void> response = cursoResource.deleteCurso(1L);

        // Verificando a resposta
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
