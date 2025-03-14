package br.edu.ifgoias.academico.entities;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "curso")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeCurso = "Curso Padrão"; // Define um valor padrão para evitar problemas com NOT NULL

    public Curso() {}

    public Curso(Long id, String nomeCurso) {
        this.id = id;
        this.nomeCurso = (nomeCurso != null && !nomeCurso.isEmpty()) ? nomeCurso : "Curso Padrão";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = (nomeCurso != null && !nomeCurso.isEmpty()) ? nomeCurso : "Curso Padrão";
    }
}
