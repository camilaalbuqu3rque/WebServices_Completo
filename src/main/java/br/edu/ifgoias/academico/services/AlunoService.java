package br.edu.ifgoias.academico.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import br.edu.ifgoias.academico.entities.Aluno;
import br.edu.ifgoias.academico.repositories.AlunoRepository;

@Service
public class AlunoService {

	private AlunoRepository alunoRep;

	public List<Aluno> findAll() {
		return alunoRep.findAll(); 
	}

	public Aluno findById(Integer id) {
		return alunoRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	} 

	public Aluno insert(Aluno obj) {
		return alunoRep.save(obj);
	}  

	public void delete(Integer id) {
		alunoRep.deleteById(id);
	}

	public Aluno update(Integer id, Aluno objAlterado) { 
		return alunoRep.findById(id).map(alunoDB -> {
			alunoDB.setNome(objAlterado.getNome());
			alunoDB.setSexo(objAlterado.getSexo());
			alunoDB.setDtNasc(objAlterado.getDtNasc());
			return alunoRep.save(alunoDB); 
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
}