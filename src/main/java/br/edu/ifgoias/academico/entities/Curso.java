package br.edu.ifgoias.academico.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idcurso;

    @Column(name = "nomecurso", nullable = false)
    private String nomecurso;

    @JsonIgnore
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aluno> alunos = new ArrayList<>();

    public Curso() {
    }

    public Curso(Integer idcurso, String nomecurso) {
        this.idcurso = idcurso;
        this.nomecurso = nomecurso;
    }

    public Integer getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public String getNomecurso() {
        return nomecurso;
    }

    public void setNomecurso(String nomecurso) {
        this.nomecurso = nomecurso;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void addAluno(Aluno aluno) {
        if (aluno != null && !alunos.contains(aluno)) {
            alunos.add(aluno);
            aluno.setCurso(this);
        }
    }

    public void removeAluno(Aluno aluno) {
        if (aluno != null && alunos.contains(aluno)) {
            alunos.remove(aluno);
            aluno.setCurso(null);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return Objects.equals(idcurso, curso.idcurso) && Objects.equals(nomecurso, curso.nomecurso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcurso, nomecurso);
    }

    @Override
    public String toString() {
        return "Curso {idcurso=" + idcurso + ", nomecurso='" + nomecurso + "'}";
    }
}