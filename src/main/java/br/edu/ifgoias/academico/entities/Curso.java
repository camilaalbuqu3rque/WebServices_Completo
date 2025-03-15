package br.edu.ifgoias.academico.entities;

import jakarta.persistence.*;

@Entity
public class Curso {

    private static final String CURSO_PADRAO = "Curso Padrão"; // Definição da constante

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nomeCurso = CURSO_PADRAO; // Uso da constante
  
    public Curso() {}

    public Curso(Long id, String nomeCurso) {
        this.id = id;
        this.nomeCurso = (nomeCurso != null && !nomeCurso.isEmpty()) ? nomeCurso : CURSO_PADRAO; // Uso da constante
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
        this.nomeCurso = (nomeCurso != null && !nomeCurso.isEmpty()) ? nomeCurso : CURSO_PADRAO; // Uso da constante
    }
}
