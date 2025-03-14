package br.edu.ifgoias.academico.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.repositories.AlunoRepository;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private Aluno aluno;

    @BeforeEach
    void setUp() {
        aluno = new Aluno(1, "Maria", "F", new Date());
    }

    @Test
    void testFindAll() {
        Aluno aluno2 = new Aluno(2, "João", "M", new Date());
        when(alunoRepository.findAll()).thenReturn(Arrays.asList(aluno, aluno2));
        
        assertEquals(2, alunoService.findAll().size());
    }
 
    @Test
    void testFindByIdExists() {
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        
        Aluno foundAluno = alunoService.findById(1);
        assertNotNull(foundAluno);
        assertEquals(1, foundAluno.getIdaluno());
    }

    @Test
    void testFindByIdNotExists() {
        when(alunoRepository.findById(1)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> alunoService.findById(1));
    }

    @Test
    void testInsert() {
        when(alunoRepository.save(aluno)).thenReturn(aluno);
        
        Aluno savedAluno = alunoService.insert(aluno);
        assertNotNull(savedAluno);
        assertEquals(aluno, savedAluno);
    }

    @Test
    void testDelete() {
        doNothing().when(alunoRepository).deleteById(1);
        
        assertDoesNotThrow(() -> alunoService.delete(1));
    }

    @Test
    void testEqualsAndHashCode() {
        Aluno aluno2 = new Aluno(1, "Maria", "F", new Date());
        assertEquals(aluno, aluno2);
        assertEquals(aluno.hashCode(), aluno2.hashCode());

        Aluno aluno3 = new Aluno(2, "João", "M", new Date());
        assertNotEquals(aluno, aluno3);
    }

    @Test
    void testToString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String expected = "Aluno [id=1, nome=Maria, sexo=F, dt_nasc=" + sdf.format(aluno.getDtNasc()) + "]";

        assertEquals(expected, aluno.toString());        
    }

    @Test
    void testAlunoNullFields() {
        Aluno alunoNulo = new Aluno();
        alunoNulo.setIdaluno(null);
        alunoNulo.setNome(null);
        alunoNulo.setSexo(null);
        alunoNulo.setDtNasc(null);
        
        assertNull(alunoNulo.getIdaluno());
        assertNull(alunoNulo.getNome());
        assertNull(alunoNulo.getSexo());
        assertNull(alunoNulo.getDtNasc());
    }

    @Test
    void testSetAndGetId() {
        aluno.setId(10);
        assertEquals(10, aluno.getId());
    }
}
