package br.edu.ifgoias.academico.services;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.repositories.CursoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

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

        assertThrows(ResponseStatusException.class, () -> cursoService.findById(99L));
    }

    @Test
    void testInsert() {
        Curso curso = new Curso(null, "História");
        when(cursoRepository.save(curso)).thenReturn(new Curso(1L, "História"));

        Curso result = cursoService.insert(curso);
        assertEquals("História", result.getNomeCurso());
    }
}
