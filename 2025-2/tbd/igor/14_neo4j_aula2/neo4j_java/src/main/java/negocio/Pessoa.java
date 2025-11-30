package negocio;

public class Pessoa {
    private long id;
    private String profissao;
    private String nome;

    public void setId(long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setProfissao(String profissao) {
        this.profissao = profissao;
    }

    public long getId() {
        return id;
    }

    public String getProfissao() {
        return profissao;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return "Pessoa [id=" + id + ", profissao=" + profissao + ", nome=" + nome + "]";
    }

    
}
