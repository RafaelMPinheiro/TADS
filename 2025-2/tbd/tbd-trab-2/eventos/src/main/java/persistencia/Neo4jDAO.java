package persistencia;

import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import util.Neo4jConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Neo4jDAO {

    // ==================== CRIAÇÃO DE NÓS ====================
    public void createEventoNode(String eventoId, String nome) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            session.run(
                "MERGE (e:Evento {id: $id}) SET e.nome = $nome",
                Values.parameters("id", eventoId, "nome", nome)
            );
        }
    }

    public void createParticipanteNode(String participanteId, String nome, String email) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            session.run(
                "MERGE (p:Participante {id: $id}) SET p.nome = $nome, p.email = $email",
                Values.parameters("id", participanteId, "nome", nome, "email", email)
            );
        }
    }

    // ==================== CRIAÇÃO DE RELACIONAMENTOS ====================
    public void addParticipanteToEvento(String participanteId, String eventoId) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            session.run(
                "MATCH (p:Participante {id: $participanteId}), (e:Evento {id: $eventoId}) " +
                "MERGE (p)-[r:PARTICIPOU]->(e) " +
                "ON CREATE SET r.dataRelacionamento = datetime()",
                Values.parameters("participanteId", participanteId, "eventoId", eventoId)
            );
        }
    }

    public void addOrganizadorToEvento(String participanteId, String eventoId) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            session.run(
                "MATCH (p:Participante {id: $participanteId}), (e:Evento {id: $eventoId}) " +
                "MERGE (p)-[r:ORGANIZOU]->(e) " +
                "ON CREATE SET r.dataRelacionamento = datetime()",
                Values.parameters("participanteId", participanteId, "eventoId", eventoId)
            );
        }
    }

    // ==================== CONSULTAS ====================
    public List<Map<String, Object>> findParticipantesByEvento(String eventoId) {
        List<Map<String, Object>> participantes = new ArrayList<>();
        
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            Result result = session.run(
                "MATCH (p:Participante)-[r:PARTICIPOU]->(e:Evento {id: $eventoId}) " +
                "RETURN p.id AS id, p.nome AS nome, p.email AS email, r.dataRelacionamento AS dataRelacionamento " +
                "ORDER BY p.nome",
                Values.parameters("eventoId", eventoId)
            );
            
            while (result.hasNext()) {
                Record record = result.next();
                Map<String, Object> participante = new HashMap<>();
                participante.put("id", record.get("id").asString());
                participante.put("nome", record.get("nome").asString());
                participante.put("email", record.get("email").asString());
                participante.put("tipo", "PARTICIPANTE");
                participantes.add(participante);
            }
        }
        
        return participantes;
    }

    public List<Map<String, Object>> findOrganizadoresByEvento(String eventoId) {
        List<Map<String, Object>> organizadores = new ArrayList<>();
        
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            Result result = session.run(
                "MATCH (p:Participante)-[r:ORGANIZOU]->(e:Evento {id: $eventoId}) " +
                "RETURN p.id AS id, p.nome AS nome, p.email AS email, r.dataRelacionamento AS dataRelacionamento " +
                "ORDER BY p.nome",
                Values.parameters("eventoId", eventoId)
            );
            
            while (result.hasNext()) {
                Record record = result.next();
                Map<String, Object> organizador = new HashMap<>();
                organizador.put("id", record.get("id").asString());
                organizador.put("nome", record.get("nome").asString());
                organizador.put("email", record.get("email").asString());
                organizador.put("tipo", "ORGANIZADOR");
                organizadores.add(organizador);
            }
        }
        
        return organizadores;
    }

    public Map<String, List<Map<String, Object>>> findTodosEnvolvidosByEvento(String eventoId) {
        Map<String, List<Map<String, Object>>> resultado = new HashMap<>();
        resultado.put("participantes", findParticipantesByEvento(eventoId));
        resultado.put("organizadores", findOrganizadoresByEvento(eventoId));
        return resultado;
    }

    // ==================== MIGRAÇÃO PARTICIPANTE → ORGANIZADOR ====================
    public void promoverParticipanteToOrganizador(String participanteId, String eventoId) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            // Adiciona relacionamento ORGANIZOU (mantém PARTICIPOU se existir)
            session.run(
                "MATCH (p:Participante {id: $participanteId}), (e:Evento {id: $eventoId}) " +
                "MERGE (p)-[r:ORGANIZOU]->(e) " +
                "ON CREATE SET r.dataRelacionamento = datetime()",
                Values.parameters("participanteId", participanteId, "eventoId", eventoId)
            );
        }
    }

    public boolean isOrganizador(String participanteId, String eventoId) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            Result result = session.run(
                "MATCH (p:Participante {id: $participanteId})-[r:ORGANIZOU]->(e:Evento {id: $eventoId}) " +
                "RETURN count(r) > 0 AS isOrganizador",
                Values.parameters("participanteId", participanteId, "eventoId", eventoId)
            );
            
            if (result.hasNext()) {
                return result.next().get("isOrganizador").asBoolean();
            }
        }
        return false;
    }

    // ==================== REMOÇÃO ====================
    public void removeParticipanteFromEvento(String participanteId, String eventoId) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            session.run(
                "MATCH (p:Participante {id: $participanteId})-[r:PARTICIPOU]->(e:Evento {id: $eventoId}) " +
                "DELETE r",
                Values.parameters("participanteId", participanteId, "eventoId", eventoId)
            );
        }
    }

    public void removeOrganizadorFromEvento(String participanteId, String eventoId) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            session.run(
                "MATCH (p:Participante {id: $participanteId})-[r:ORGANIZOU]->(e:Evento {id: $eventoId}) " +
                "DELETE r",
                Values.parameters("participanteId", participanteId, "eventoId", eventoId)
            );
        }
    }

    public void deleteEventoNode(String eventoId) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            session.run(
                "MATCH (e:Evento {id: $eventoId}) DETACH DELETE e",
                Values.parameters("eventoId", eventoId)
            );
        }
    }

    public void deleteParticipanteNode(String participanteId) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            session.run(
                "MATCH (p:Participante {id: $participanteId}) DETACH DELETE p",
                Values.parameters("participanteId", participanteId)
            );
        }
    }
}
