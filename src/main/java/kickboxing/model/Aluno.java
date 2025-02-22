package kickboxing.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAluno;

    @Column(nullable = false)
    private String registroAluno;

    @Column(nullable = false)
    private String nomeAluno;

    @Column(nullable = false)
    private String cidadeAluno;

    @Column(nullable = false)
    private String graduacaoAluno;

    @Column(updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate nascimentoAluno;

    @Column(nullable = false)
    private String academiaAluno;

    @Column(nullable = false)
    private String responsavelAluno;

    @Column(nullable = false)
    private String imagemAluno;

    public long getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(long idAluno) {
        this.idAluno = idAluno;
    }

    public String getRegistroAluno() {
        return registroAluno;
    }

    public void setRegistroAluno(String registroAluno) {
        this.registroAluno = registroAluno;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getCidadeAluno() {
        return cidadeAluno;
    }

    public void setCidadeAluno(String cidadeAluno) {
        this.cidadeAluno = cidadeAluno;
    }

    public String getGraduacaoAluno() {
        return graduacaoAluno;
    }

    public void setGraduacaoAluno(String graduacaoAluno) {
        this.graduacaoAluno = graduacaoAluno;
    }

    public LocalDate getNascimentoAluno() {
        return nascimentoAluno;
    }

    public void setNascimentoAluno(LocalDate nascimentoAluno) {
        this.nascimentoAluno = nascimentoAluno;
    }

    public String getAcademiaAluno() {
        return academiaAluno;
    }

    public void setAcademiaAluno(String academiaAluno) {
        this.academiaAluno = academiaAluno;
    }

    public String getResponsavelAluno() {
        return responsavelAluno;
    }

    public void setResponsavelAluno(String responsavelAluno) {
        this.responsavelAluno = responsavelAluno;
    }

    public String getImagemAluno() {
        return imagemAluno;
    }

    public void setImagemAluno(String imagemAluno) {
        this.imagemAluno = imagemAluno;
    }
}