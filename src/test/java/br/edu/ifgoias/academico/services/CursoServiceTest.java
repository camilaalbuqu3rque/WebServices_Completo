package br.edu.ifgoias.academico.services;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.repositories.CursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Essa classe contém testes unitários para a classe CursoService, garantindo que seus métodos funcionem corretamente.

class CursoServiceTest {

    @InjectMocks // Instância real do serviço com repositório mockado
    private CursoService cursoService; 

    @Mock // Instância real do serviço com repositório mockado
    private CursoRepository cursoRepository; 

    @BeforeEach // // Inicializa os mocks antes de cada teste
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() { // Verifica se findById() retorna um curso válido quando ele existe.
        Curso curso = new Curso(1L, "Matemática");
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso)); 

        Curso result = cursoService.findById(1L);
        assertEquals("Matemática", result.getNomeCurso());
    }

    @Test
    void testFindById_NotFound() { // Verifica se findById() retorna erro quando o curso não existe.
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cursoService.findById(99L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Curso não encontrado", exception.getReason());
    }

    @Test
    void testInsert() { // Verifica se insert() salva um curso corretamente.
        Curso curso = new Curso(null, "História");
        when(cursoRepository.save(curso)).thenReturn(new Curso(1L, "História"));

        Curso result = cursoService.insert(curso);
        assertEquals("História", result.getNomeCurso());
    }

    @Test 
    void testFindAll() { // Verifica se findAll() retorna corretamente a lista de cursos.
        Curso curso1 = new Curso(1L, "Matemática");
        Curso curso2 = new Curso(2L, "História");
        when(cursoRepository.findAll()).thenReturn(Arrays.asList(curso1, curso2));

        List<Curso> cursos = cursoService.findAll();
        assertEquals(2, cursos.size());
        assertEquals("Matemática", cursos.get(0).getNomeCurso());
        assertEquals("História", cursos.get(1).getNomeCurso());
    }

    @Test
    void testUpdate_Success() { // Verifica se update() altera corretamente um curso existente.
        Curso existingCurso = new Curso(1L, "Matemática");
        Curso updatedCurso = new Curso(1L, "Matemática Avançada");

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(existingCurso));
        when(cursoRepository.save(existingCurso)).thenReturn(updatedCurso);

        Curso result = cursoService.update(1L, updatedCurso);
        assertEquals("Matemática Avançada", result.getNomeCurso());
    }

    @Test
    void testUpdate_NotFound() { // Verifica se update() retorna erro quando o curso não existe.
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        // Cria o objeto Curso fora da lambda
        Curso curso = new Curso(99L, "Curso Inexistente");

        // A lambda agora contém apenas uma invocação que pode lançar uma exceção
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cursoService.update(99L, curso);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Curso não encontrado", exception.getReason());
    }

    @Test
    void testDelete_Success() { // Verifica se delete() remove um curso corretamente.
        Curso curso = new Curso(1L, "Matemática");
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        doNothing().when(cursoRepository).delete(curso);

        cursoService.delete(1L);

        verify(cursoRepository, times(1)).findById(1L);
        verify(cursoRepository, times(1)).delete(curso);
    }

    @Test
    void testDelete_NotFound() { // Verifica se delete() retorna erro quando o curso não existe.
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cursoService.delete(99L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Curso não encontrado", exception.getReason());
    }
}