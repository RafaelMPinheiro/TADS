package negocio;

import dto.EventoDTO;
import org.bson.Document;
import persistencia.MongoEventoDAO;
import persistencia.Neo4jDAO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Evento {
    private final MongoEventoDAO mongoDAO;
    private final Neo4jDAO neo4jDAO;

    public Evento() {
        this.mongoDAO = new MongoEventoDAO();
        this.neo4jDAO = new Neo4jDAO();
    }

    public Evento(MongoEventoDAO mongoDAO, Neo4jDAO neo4jDAO) {
        this.mongoDAO = mongoDAO;
        this.neo4jDAO = neo4jDAO;
    }

    // ==================== CRUD EVENTOS ====================

    public List<EventoDTO> listarTodos() {
        List<Document> docs = mongoDAO.findAllEventos();
        return docs.stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }

    public EventoDTO buscarPorId(String id) {
        Document doc = mongoDAO.findEventoById(id);
        return doc != null ? new EventoDTO(doc) : null;
    }

    public String criar(String nome, LocalDateTime data, String local, String descricao, List<String> tags) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do evento é obrigatório");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data do evento é obrigatória");
        }
        if (local == null || local.trim().isEmpty()) {
            throw new IllegalArgumentException("Local do evento é obrigatório");
        }

        String eventoId = mongoDAO.createEvento(nome, data, local, descricao, tags);
        neo4jDAO.createEventoNode(eventoId, nome);

        return eventoId;
    }

    public void atualizar(String id, String nome, LocalDateTime data, String local, String descricao,
            List<String> tags) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do evento é obrigatório");
        }
        if (data == null) {
            throw new IllegalArgumentException("Data do evento é obrigatória");
        }
        if (local == null || local.trim().isEmpty()) {
            throw new IllegalArgumentException("Local do evento é obrigatório");
        }

        Document eventoExistente = mongoDAO.findEventoById(id);
        if (eventoExistente == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }

        mongoDAO.updateEvento(id, nome, data, local, descricao, tags);
    }

    public void deletar(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }

        Document eventoExistente = mongoDAO.findEventoById(id);
        if (eventoExistente == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }

        mongoDAO.deleteEvento(id);
        neo4jDAO.deleteEventoNode(id);
    }

    // ==================== FILTROS ====================

    public List<EventoDTO> filtrarPorLocal(String local) {
        if (local == null || local.trim().isEmpty()) {
            throw new IllegalArgumentException("Local é obrigatório para filtro");
        }

        List<Document> docs = mongoDAO.findEventosByLocal(local);
        return docs.stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }

    public List<EventoDTO> filtrarPorPeriodo(LocalDateTime dataInicio, LocalDateTime dataFim) {
        if (dataInicio == null || dataFim == null) {
            throw new IllegalArgumentException("Data de início e fim são obrigatórias");
        }
        if (dataInicio.isAfter(dataFim)) {
            throw new IllegalArgumentException("Data de início deve ser anterior à data de fim");
        }

        List<Document> docs = mongoDAO.findEventosByDataRange(dataInicio, dataFim);
        return docs.stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }

    public List<EventoDTO> filtrarPorPalavraChave(String palavraChave) {
        if (palavraChave == null || palavraChave.trim().isEmpty()) {
            throw new IllegalArgumentException("Palavra-chave é obrigatória para filtro");
        }

        List<Document> docs = mongoDAO.findEventosByPalavraChave(palavraChave);
        return docs.stream()
                .map(EventoDTO::new)
                .collect(Collectors.toList());
    }

    // ==================== RELACIONAMENTOS ====================

    public void adicionarParticipante(String eventoId, String participanteId) {
        if (eventoId == null || eventoId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }
        if (participanteId == null || participanteId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do participante é obrigatório");
        }

        Document evento = mongoDAO.findEventoById(eventoId);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }

        Document participante = mongoDAO.findParticipanteById(participanteId);
        if (participante == null) {
            throw new IllegalArgumentException("Participante não encontrado");
        }

        neo4jDAO.addParticipanteToEvento(participanteId, eventoId);
    }

    public void adicionarOrganizador(String eventoId, String participanteId) {
        if (eventoId == null || eventoId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }
        if (participanteId == null || participanteId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do participante é obrigatório");
        }

        Document evento = mongoDAO.findEventoById(eventoId);
        if (evento == null) {
            throw new IllegalArgumentException("Evento não encontrado");
        }

        Document participante = mongoDAO.findParticipanteById(participanteId);
        if (participante == null) {
            throw new IllegalArgumentException("Participante não encontrado");
        }

        neo4jDAO.addOrganizadorToEvento(participanteId, eventoId);
    }

    public void promoverParticipanteParaOrganizador(String eventoId, String participanteId) {
        if (eventoId == null || eventoId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }
        if (participanteId == null || participanteId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do participante é obrigatório");
        }

        if (neo4jDAO.isOrganizador(participanteId, eventoId)) {
            throw new IllegalArgumentException("Participante já é organizador deste evento");
        }

        neo4jDAO.promoverParticipanteToOrganizador(participanteId, eventoId);
    }

    public List<java.util.Map<String, Object>> listarParticipantes(String eventoId) {
        if (eventoId == null || eventoId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }

        return neo4jDAO.findParticipantesByEvento(eventoId);
    }

    public List<java.util.Map<String, Object>> listarOrganizadores(String eventoId) {
        if (eventoId == null || eventoId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }

        return neo4jDAO.findOrganizadoresByEvento(eventoId);
    }

    public java.util.Map<String, List<java.util.Map<String, Object>>> listarTodosEnvolvidos(String eventoId) {
        if (eventoId == null || eventoId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do evento é obrigatório");
        }

        return neo4jDAO.findTodosEnvolvidosByEvento(eventoId);
    }
}
