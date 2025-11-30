package dto;

import org.bson.Document;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class EventoDTO {
    private String id;
    private String nome;
    private LocalDateTime data;
    private String local;
    private String descricao;
    private List<String> tags;

    public EventoDTO() {
    }

    public EventoDTO(String id, String nome, LocalDateTime data, String local, String descricao, List<String> tags) {
        this.id = id;
        this.nome = nome;
        this.data = data;
        this.local = local;
        this.descricao = descricao;
        this.tags = tags;
    }

    public EventoDTO(Document doc) {
        this.id = doc.getObjectId("_id").toString();
        this.nome = doc.getString("nome");
        
        Date dataDoc = doc.getDate("data");
        if (dataDoc != null) {
            this.data = LocalDateTime.ofInstant(dataDoc.toInstant(), ZoneId.systemDefault());
        }
        
        this.local = doc.getString("local");
        this.descricao = doc.getString("descricao");
        this.tags = doc.getList("tags", String.class);
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

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
