package br.edu.ifgoias.academico.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifgoias.academico.entities.Curso;
import br.edu.ifgoias.academico.repositories.CursoRepository;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {

    @Mock
    private CursoRepository cursoRep;

    @InjectMocks
    private CursoService cursoService;

    private Curso curso1, curso2;

    @BeforeEach
    void setUp() {
        curso1 = new Curso(1, "Matemática");
        curso2 = new Curso(2, "História");
    }

    @Test
    void testFindAll() {
        when(cursoRep.findAll()).thenReturn(Arrays.asList(curso1, curso2));

        List<Curso> cursos = cursoService.findAll();
        
        assertNotNull(cursos);
        assertEquals(2, cursos.size());
        verify(cursoRep, times(1)).findAll();
    }

    @Test
    void testFindById_Success() {
        when(cursoRep.findById(1)).thenReturn(Optional.of(curso1));

        Curso curso = cursoService.findById(1);

        assertNotNull(curso);
        assertEquals("Matemática", curso.getNomecurso());
        verify(cursoRep, times(1)).findById(1);
    }

    @Test
    void testFindById_NotFound() {
        when(cursoRep.findById(3)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cursoService.findById(3));

        assertTrue(exception.getMessage().contains("Curso não encontrado"));
        verify(cursoRep, times(1)).findById(3);
    }

    @Test
    void testInsert_Success() {
        when(cursoRep.save(any(Curso.class))).thenReturn(curso1);

        Curso savedCurso = cursoService.insert(curso1);

        assertNotNull(savedCurso);
        assertEquals("Matemática", savedCurso.getNomecurso());
        verify(cursoRep, times(1)).save(curso1);
    }

    @Test
    void testInsert_InvalidData() {
        Curso cursoInvalido = new Curso(null, "");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cursoService.insert(cursoInvalido));

        assertTrue(exception.getMessage().contains("Dados inválidos para inserção"));
    }

    @Test
    void testDelete_Success() {
        when(cursoRep.existsById(1)).thenReturn(true);
        doNothing().when(cursoRep).deleteById(1);

        assertDoesNotThrow(() -> cursoService.delete(1));
        verify(cursoRep, times(1)).existsById(1);
        verify(cursoRep, times(1)).deleteById(1);
    }

    @Test
    void testDelete_NotFound() {
        when(cursoRep.existsById(3)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cursoService.delete(3));

        assertTrue(exception.getMessage().contains("Curso não encontrado para exclusão"));
        verify(cursoRep, times(1)).existsById(3);
    }

    @Test
    void testUpdate_Success() {
        Curso cursoAtualizado = new Curso(1, "Matemática Avançada");
        when(cursoRep.findById(1)).thenReturn(Optional.of(curso1));
        when(cursoRep.save(any(Curso.class))).thenReturn(cursoAtualizado);

        Curso updatedCurso = cursoService.update(1, cursoAtualizado);

        assertNotNull(updatedCurso);
        assertEquals("Matemática Avançada", updatedCurso.getNomecurso());
        verify(cursoRep, times(1)).findById(1);
        verify(cursoRep, times(1)).save(any(Curso.class));
    }

    @Test
    void testUpdate_InvalidData() {
        Curso cursoInvalido = new Curso(1, "");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cursoService.update(1, cursoInvalido));

        assertTrue(exception.getMessage().contains("Dados inválidos para atualização"));
    }

    @Test
    void testUpdate_NotFound() {
        Curso cursoAtualizado = new Curso(3, "Física");
        when(cursoRep.findById(3)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cursoService.update(3, cursoAtualizado));

        assertTrue(exception.getMessage().contains("Curso não encontrado"));
    }

    @Test
    void testBuscarCursoPorId() {
        when(cursoRep.findById(2)).thenReturn(Optional.of(curso2));

        Curso curso = cursoService.buscarCursoPorId(2);

        assertNotNull(curso);
        assertEquals("História", curso.getNomecurso());
        verify(cursoRep, times(1)).findById(2);
    }

    @Test
    void testBuscarCursoPorId_NotFound() {
        when(cursoRep.findById(4)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> cursoService.buscarCursoPorId(4));

        assertTrue(exception.getMessage().contains("Curso não encontrado"));
    }
}
