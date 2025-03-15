package br.edu.ifgoias.academico.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idaluno;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "sexo", nullable = false)
    private String sexo;

    @Column(name = "dt_nasc", nullable = false)
    private Date dtNasc;

    @ManyToOne
    @JoinColumn(name = "idcurso")
    private Curso curso;

    public Aluno() {
    } 

    public Aluno(Integer id, String nome, String sexo, Date dtNasc) { 
        this.idaluno = id;
        this.nome = nome;
        this.sexo = sexo;
        this.dtNasc = dtNasc;
    }

    public Integer getIdaluno() {
        return idaluno;
    }

    public void setIdaluno(Integer idaluno) {
        this.idaluno = idaluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Date getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(Date dtNasc) {
        this.dtNasc = dtNasc;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setId(Integer id) { 
        this.idaluno = id;
    }

    public Integer getId() {
        return this.idaluno;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dtNasc, idaluno, nome, sexo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Aluno other = (Aluno) obj;
        return Objects.equals(idaluno, other.idaluno);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Aluno [id=" + idaluno + ", nome=" + nome + ", sexo=" + sexo + ", dt_nasc=" + 
               (dtNasc != null ? sdf.format(dtNasc) : "null") + "]";
    }
}
