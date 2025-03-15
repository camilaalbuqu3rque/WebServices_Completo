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

//Essa classe contém testes unitários para a classe AlunoService, garantindo que seus métodos funcionem corretamente.
//Ela usa Mockito para simular (mockar) a interação com AlunoRepository, evitando dependências com o banco de dados real.

@ExtendWith(MockitoExtension.class) // Habilita o Mockito para injeção de dependências
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository; // Simula a camada de repositório

    @InjectMocks
    private AlunoService alunoService; // Instância real do serviço com repositório mockado

    private Aluno aluno; // Objeto de teste

    @BeforeEach
    void setUp() {
        aluno = new Aluno(1, "Maria", "F", new Date()); // Instância de um aluno fictício
    }

    @Test
    void testFindAll() {
        Aluno aluno2 = new Aluno(2, "João", "M", new Date());
        when(alunoRepository.findAll()).thenReturn(Arrays.asList(aluno, aluno2)); // Simula retorno da busca
        
        assertEquals(2, alunoService.findAll().size()); // Verifica se a lista retornada tem 2 alunos
    }
 
    @Test
    void testFindByIdExists() {
        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno));
        
        Aluno foundAluno = alunoService.findById(1); // Simula retorno válido
        assertNotNull(foundAluno);
        assertEquals(1, foundAluno.getIdaluno());
    }

    @Test
    void testFindByIdNotExists() {
        when(alunoRepository.findById(1)).thenReturn(Optional.empty()); // Simula ID inexistente
        
        assertThrows(RuntimeException.class, () -> alunoService.findById(1)); // Deve lançar exceção
    }

    @Test
    void testInsert() {
        when(alunoRepository.save(aluno)).thenReturn(aluno); // Simula o salvamento
        
        Aluno savedAluno = alunoService.insert(aluno);
        assertNotNull(savedAluno);
        assertEquals(aluno, savedAluno);
    }

    @Test
    void testDelete() {
        doNothing().when(alunoRepository).deleteById(1); // Simula deleção sem erro
        
        assertDoesNotThrow(() -> alunoService.delete(1)); // Deve executar sem exceção
    }

    @Test
    void testUpdateAlunoExists() {
        Aluno alunoAlterado = new Aluno(1, "Ana", "F", new Date());

        when(alunoRepository.findById(1)).thenReturn(Optional.of(aluno)); // Simula busca válida
        when(alunoRepository.save(any(Aluno.class))).thenReturn(alunoAlterado); // Simula atualização

        Aluno updatedAluno = alunoService.update(1, alunoAlterado);

        assertNotNull(updatedAluno);
        assertEquals("Ana", updatedAluno.getNome()); // Verifica se o nome foi alterado
        assertEquals("F", updatedAluno.getSexo());
    }

    @Test
    void testUpdateAlunoNotExists() {
        Aluno alunoAlterado = new Aluno(1, "Ana", "F", new Date());

        when(alunoRepository.findById(1)).thenReturn(Optional.empty()); // Simula ID inexistente

        assertThrows(RuntimeException.class, () -> alunoService.update(1, alunoAlterado)); // Deve lançar erro
    }

    @Test
    void testEqualsAndHashCode() {
        Aluno aluno2 = new Aluno(1, "Maria", "F", new Date()); 
        assertEquals(aluno, aluno2);
        assertEquals(aluno.hashCode(), aluno2.hashCode()); // Verifica se dois alunos com o mesmo ID são considerados iguais.

        Aluno aluno3 = new Aluno(2, "João", "M", new Date()); 
        assertNotEquals(aluno, aluno3);
    }

    @Test
    void testToString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String expected = "Aluno [id=1, nome=Maria, sexo=F, dt_nasc=" + sdf.format(aluno.getDtNasc()) + "]";

        assertEquals(expected, aluno.toString()); // Verifica se toString() retorna a string esperada.    
    }

    @Test
    void testAlunoNullFields() { // Garante que um objeto Aluno sem atributos definidos retorna null.
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
    void testSetAndGetId() { // Garante que setId() e getId() funcionam corretamente.
        aluno.setId(10);
        assertEquals(10, aluno.getId());
    }
}
