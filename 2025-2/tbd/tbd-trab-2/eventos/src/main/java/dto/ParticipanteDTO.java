package dto;

import org.bson.Document;

public class ParticipanteDTO {
    private String id;
    private String nome;
    private String email;
    private String telefone;
    private String organizacao;

    public ParticipanteDTO() {
    }

    public ParticipanteDTO(String id, String nome, String email, String telefone, String organizacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.organizacao = organizacao;
    }

    public ParticipanteDTO(Document doc) {
        this.id = doc.getObjectId("_id").toString();
        this.nome = doc.getString("nome");
        this.email = doc.getString("email");
        this.telefone = doc.getString("telefone");
        this.organizacao = doc.getString("organizacao");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getOrganizacao() {
        return organizacao;
    }

    public void setOrganizacao(String organizacao) {
        this.organizacao = organizacao;
    }
}
