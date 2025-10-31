package negocio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Livro.buscarPorAutor", query = "SELECT l FROM Livro l WHERE l.autor = :autor"),
})
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Column
    private String autor;

    @Column
    private String anoPublicacao;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "livro")
    private List<Emprestimo> emprestimos;

    public Livro() {
    }

    public Livro(String titulo, String autor, String anoPublicacao) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.emprestimos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public List<Emprestimo> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(List<Emprestimo> emprestimos) {
        this.emprestimos = emprestimos;
    }
}
