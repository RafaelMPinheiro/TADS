package negocio;

import dto.ParticipanteDTO;
import org.bson.Document;
import persistencia.MongoEventoDAO;
import persistencia.Neo4jDAO;

import java.util.List;
import java.util.stream.Collectors;

public class Participante {
    private final MongoEventoDAO mongoDAO;
    private final Neo4jDAO neo4jDAO;

    public Participante() {
        this.mongoDAO = new MongoEventoDAO();
        this.neo4jDAO = new Neo4jDAO();
    }

    public Participante(MongoEventoDAO mongoDAO, Neo4jDAO neo4jDAO) {
        this.mongoDAO = mongoDAO;
        this.neo4jDAO = neo4jDAO;
    }

    // ==================== CRUD PARTICIPANTES ====================

    public List<ParticipanteDTO> listarTodos() {
        List<Document> docs = mongoDAO.findAllParticipantes();
        return docs.stream()
                .map(ParticipanteDTO::new)
                .collect(Collectors.toList());
    }

    public ParticipanteDTO buscarPorId(String id) {
        Document doc = mongoDAO.findParticipanteById(id);
        return doc != null ? new ParticipanteDTO(doc) : null;
    }

    public ParticipanteDTO buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }

        Document doc = mongoDAO.findParticipanteByEmail(email);
        return doc != null ? new ParticipanteDTO(doc) : null;
    }

    public String criar(String nome, String email, String telefone, String organizacao) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do participante é obrigatório");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (!isEmailValido(email)) {
            throw new IllegalArgumentException("Email inválido");
        }

        Document existente = mongoDAO.findParticipanteByEmail(email);
        if (existente != null) {
            throw new IllegalArgumentException("Já existe um participante com este email");
        }

        String participanteId = mongoDAO.createParticipante(nome, email, telefone, organizacao);
        neo4jDAO.createParticipanteNode(participanteId, nome, email);

        return participanteId;
    }

    public void atualizar(String id, String nome, String email, String telefone, String organizacao) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do participante é obrigatório");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do participante é obrigatório");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email é obrigatório");
        }
        if (!isEmailValido(email)) {
            throw new IllegalArgumentException("Email inválido");
        }

        Document participanteExistente = mongoDAO.findParticipanteById(id);
        if (participanteExistente == null) {
            throw new IllegalArgumentException("Participante não encontrado");
        }

        Document outroParticipante = mongoDAO.findParticipanteByEmail(email);
        if (outroParticipante != null && !outroParticipante.getObjectId("_id").toString().equals(id)) {
            throw new IllegalArgumentException("Já existe outro participante com este email");
        }

        mongoDAO.updateParticipante(id, nome, email, telefone, organizacao);
    }

    public void deletar(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do participante é obrigatório");
        }

        Document participanteExistente = mongoDAO.findParticipanteById(id);
        if (participanteExistente == null) {
            throw new IllegalArgumentException("Participante não encontrado");
        }

        mongoDAO.deleteParticipante(id);
        neo4jDAO.deleteParticipanteNode(id);
    }

    // ==================== VALIDAÇÕES ====================

    private boolean isEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }
}
