package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.types.ObjectId;
import util.MongoConnection;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class MongoEventoDAO {
    private final MongoCollection<Document> eventosCollection;
    private final MongoCollection<Document> participantesCollection;

    public MongoEventoDAO() {
        MongoDatabase database = MongoConnection.getInstance().getDatabase();
        this.eventosCollection = database.getCollection("eventos");
        this.participantesCollection = database.getCollection("participantes");
    }

    // ==================== CRUD EVENTOS ====================
    public String createEvento(String nome, LocalDateTime data, String local, String descricao, List<String> tags) {
        Document evento = new Document()
                .append("nome", nome)
                .append("data", Date.from(data.atZone(ZoneId.systemDefault()).toInstant()))
                .append("local", local)
                .append("descricao", descricao)
                .append("tags", tags)
                .append("dataCriacao", new Date());
        
        eventosCollection.insertOne(evento);
        return evento.getObjectId("_id").toString();
    }

    public Document findEventoById(String id) {
        return eventosCollection.find(Filters.eq("_id", new ObjectId(id))).first();
    }

    public List<Document> findAllEventos() {
        List<Document> eventos = new ArrayList<>();
        eventosCollection.find().sort(Sorts.descending("data")).into(eventos);
        return eventos;
    }

    public void updateEvento(String id, String nome, LocalDateTime data, String local, String descricao, List<String> tags) {
        Document updateDoc = new Document()
                .append("nome", nome)
                .append("data", Date.from(data.atZone(ZoneId.systemDefault()).toInstant()))
                .append("local", local)
                .append("descricao", descricao)
                .append("tags", tags);
        
        eventosCollection.updateOne(
            Filters.eq("_id", new ObjectId(id)),
            new Document("$set", updateDoc)
        );
    }

    public void deleteEvento(String id) {
        eventosCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    // ==================== FILTROS DINÂMICOS ====================
    public List<Document> findEventosByLocal(String local) {
        Pattern pattern = Pattern.compile(local, Pattern.CASE_INSENSITIVE);
        List<Document> eventos = new ArrayList<>();
        eventosCollection.find(Filters.regex("local", pattern))
                .sort(Sorts.descending("data"))
                .into(eventos);
        return eventos;
    }

    public List<Document> findEventosByDataRange(LocalDateTime dataInicio, LocalDateTime dataFim) {
        Date inicio = Date.from(dataInicio.atZone(ZoneId.systemDefault()).toInstant());
        Date fim = Date.from(dataFim.atZone(ZoneId.systemDefault()).toInstant());
        
        List<Document> eventos = new ArrayList<>();
        eventosCollection.find(Filters.and(
                Filters.gte("data", inicio),
                Filters.lte("data", fim)
        )).sort(Sorts.ascending("data")).into(eventos);
        return eventos;
    }

    public List<Document> findEventosByPalavraChave(String palavraChave) {
        Pattern pattern = Pattern.compile(palavraChave, Pattern.CASE_INSENSITIVE);
        
        List<Document> eventos = new ArrayList<>();
        eventosCollection.find(Filters.or(
                Filters.regex("nome", pattern),
                Filters.regex("descricao", pattern),
                Filters.regex("tags", pattern)
        )).sort(Sorts.descending("data")).into(eventos);
        return eventos;
    }

    // ==================== CRUD PARTICIPANTES ====================
    public String createParticipante(String nome, String email, String telefone, String organizacao) {
        Document participante = new Document()
                .append("nome", nome)
                .append("email", email)
                .append("telefone", telefone)
                .append("organizacao", organizacao)
                .append("dataCadastro", new Date());
        
        participantesCollection.insertOne(participante);
        return participante.getObjectId("_id").toString();
    }

    public Document findParticipanteById(String id) {
        return participantesCollection.find(Filters.eq("_id", new ObjectId(id))).first();
    }

    public Document findParticipanteByEmail(String email) {
        return participantesCollection.find(Filters.eq("email", email)).first();
    }

    public List<Document> findAllParticipantes() {
        List<Document> participantes = new ArrayList<>();
        participantesCollection.find().sort(Sorts.ascending("nome")).into(participantes);
        return participantes;
    }

    public void updateParticipante(String id, String nome, String email, String telefone, String organizacao) {
        Document updateDoc = new Document()
                .append("nome", nome)
                .append("email", email)
                .append("telefone", telefone)
                .append("organizacao", organizacao);
        
        participantesCollection.updateOne(
            Filters.eq("_id", new ObjectId(id)),
            new Document("$set", updateDoc)
        );
    }

    public void deleteParticipante(String id) {
        participantesCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }
}
