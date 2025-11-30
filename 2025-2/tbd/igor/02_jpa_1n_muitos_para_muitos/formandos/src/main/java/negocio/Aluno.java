package negocio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nome;

    // Cuidar a estrategia! Neste caso Endereco e Aluno estão intimamente ligados, ou seja, por exemplo, se eu deletar a aluno o endereco, por cascata, vai junto.
    @OneToOne(cascade = CascadeType.ALL)
    private Endereco endereco;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aluno")
    private List<Matricula> matriculas;

    public Aluno(){
        this.endereco = new Endereco();
        this.matriculas = new ArrayList<>();
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Endereco getEndereco() {
        return endereco;
    }
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }

    @Override
    public String toString() {
        return "Aluno [id=" + id + ", nome=" + nome + ", endereco=" + endereco + ", matriculas="+this.matriculasToString()+"]";
    }

    private String matriculasToString() {
        String resultado = "";
        for (Matricula matricula : matriculas) {
            resultado += matricula.toString();
        }
        return resultado;
    }

    

    
}
