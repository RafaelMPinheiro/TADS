package negocio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("Aluno")
public class Aluno extends Pessoa {

    @Column(unique = true)
    private String matricula;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private List<Matricula> matriculas;

    @ManyToMany
    @JoinTable(name = "trabalhos_alunos")
    private List<Trabalho> trabalhos;

    public Aluno() {
        this.matriculas = new ArrayList<>();
        this.trabalhos = new ArrayList<>();
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    @Override
    public String toString() {
        return "Aluno [id=" + super.getId() + ", nome=" + super.getNome() + ", matriculas=" + this.matriculasToString()
                + "]";
    }

    private String matriculasToString() {
        String resultado = "";
        for (Matricula matricula : matriculas) {
            resultado += matricula.toString();
        }
        return resultado;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Trabalho> getTrabalhos() {
        return trabalhos;
    }

    public void setTrabalhos(List<Trabalho> trabalhos) {
        this.trabalhos = trabalhos;
    }
}
