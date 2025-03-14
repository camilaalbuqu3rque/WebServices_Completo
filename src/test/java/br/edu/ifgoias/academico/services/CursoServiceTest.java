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

class CursoServiceTest {

    @InjectMocks
    private CursoService cursoService;

    @Mock
    private CursoRepository cursoRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById_Success() { 
        Curso curso = new Curso(1L, "Matemática");
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));

        Curso result = cursoService.findById(1L);
        assertEquals("Matemática", result.getNomeCurso());
    }

    @Test
    void testFindById_NotFound() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cursoService.findById(99L));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Curso não encontrado", exception.getReason());
    }

    @Test
    void testInsert() {
        Curso curso = new Curso(null, "História");
        when(cursoRepository.save(curso)).thenReturn(new Curso(1L, "História"));

        Curso result = cursoService.insert(curso);
        assertEquals("História", result.getNomeCurso());
    }

    @Test 
    void testFindAll() {
        Curso curso1 = new Curso(1L, "Matemática");
        Curso curso2 = new Curso(2L, "História");
        when(cursoRepository.findAll()).thenReturn(Arrays.asList(curso1, curso2));

        List<Curso> cursos = cursoService.findAll();
        assertEquals(2, cursos.size());
        assertEquals("Matemática", cursos.get(0).getNomeCurso());
        assertEquals("História", cursos.get(1).getNomeCurso());
    }

    @Test
    void testUpdate_Success() {
        Curso existingCurso = new Curso(1L, "Matemática");
        Curso updatedCurso = new Curso(1L, "Matemática Avançada");

        when(cursoRepository.findById(1L)).thenReturn(Optional.of(existingCurso));
        when(cursoRepository.save(existingCurso)).thenReturn(updatedCurso);

        Curso result = cursoService.update(1L, updatedCurso);
        assertEquals("Matemática Avançada", result.getNomeCurso());
    }

    @Test
    void testUpdate_NotFound() {
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
    void testDelete_Success() {
        Curso curso = new Curso(1L, "Matemática");
        when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));
        doNothing().when(cursoRepository).delete(curso);

        cursoService.delete(1L);

        verify(cursoRepository, times(1)).findById(1L);
        verify(cursoRepository, times(1)).delete(curso);
    }

    @Test
    void testDelete_NotFound() {
        when(cursoRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            cursoService.delete(99L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Curso não encontrado", exception.getReason());
    }
}