package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.EventoDTO;
import dto.ParticipanteDTO;
import org.bson.Document;
import persistencia.MongoEventoDAO;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportService {
    private final MongoEventoDAO mongoDAO;
    private final Gson gson;
    private static final DateTimeFormatter SQL_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ExportService(MongoEventoDAO mongoDAO) {
        this.mongoDAO = mongoDAO;
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }

    // ==================== EXPORTAÇÃO JSON ====================
    public String exportEventosToJson() {
        List<Document> eventosDoc = mongoDAO.findAllEventos();
        List<EventoDTO> eventos = new ArrayList<>();
        
        for (Document doc : eventosDoc) {
            eventos.add(new EventoDTO(doc));
        }
        
        Map<String, Object> exportData = new HashMap<>();
        exportData.put("totalEventos", eventos.size());
        exportData.put("dataExportacao", LocalDateTime.now());
        exportData.put("eventos", eventos);
        
        return gson.toJson(exportData);
    }

    public String exportParticipantesToJson() {
        List<Document> participantesDoc = mongoDAO.findAllParticipantes();
        List<ParticipanteDTO> participantes = new ArrayList<>();
        
        for (Document doc : participantesDoc) {
            participantes.add(new ParticipanteDTO(doc));
        }
        
        Map<String, Object> exportData = new HashMap<>();
        exportData.put("totalParticipantes", participantes.size());
        exportData.put("dataExportacao", LocalDateTime.now());
        exportData.put("participantes", participantes);
        
        return gson.toJson(exportData);
    }

    public void exportEventosToFile(String filePath) throws IOException {
        String json = exportEventosToJson();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }

    public void exportParticipantesToFile(String filePath) throws IOException {
        String json = exportParticipantesToJson();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }

    // ==================== EXPORTAÇÃO SQL ====================
    public String generateCreateTableSQL() {
        StringBuilder sql = new StringBuilder();
        
        sql.append("-- Criar tabelas\n");
        sql.append("CREATE TABLE IF NOT EXISTS eventos (\n");
        sql.append("    id SERIAL PRIMARY KEY,\n");
        sql.append("    mongo_id VARCHAR(255) NOT NULL UNIQUE,\n");
        sql.append("    nome VARCHAR(200) NOT NULL,\n");
        sql.append("    data TIMESTAMP NOT NULL,\n");
        sql.append("    local VARCHAR(200) NOT NULL,\n");
        sql.append("    descricao TEXT,\n");
        sql.append("    tags VARCHAR(500),\n");
        sql.append("    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n");
        sql.append(");\n\n");
        
        sql.append("CREATE TABLE IF NOT EXISTS participantes (\n");
        sql.append("    id SERIAL PRIMARY KEY,\n");
        sql.append("    mongo_id VARCHAR(255) NOT NULL UNIQUE,\n");
        sql.append("    nome VARCHAR(200) NOT NULL,\n");
        sql.append("    email VARCHAR(200) NOT NULL UNIQUE,\n");
        sql.append("    telefone VARCHAR(20),\n");
        sql.append("    organizacao VARCHAR(100),\n");
        sql.append("    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP\n");
        sql.append(");\n\n");
        
        return sql.toString();
    }

    public String generateEventosInsertSQL() {
        List<Document> eventosDoc = mongoDAO.findAllEventos();
        StringBuilder sql = new StringBuilder();
        
        sql.append("-- Inserir eventos\n");
        
        for (Document doc : eventosDoc) {
            EventoDTO dto = new EventoDTO(doc);
            String mongoId = doc.getObjectId("_id").toString();
            
            sql.append("INSERT INTO eventos (mongo_id, nome, data, local, descricao, tags, data_criacao) VALUES (\n");
            sql.append("    '").append(escapeSql(mongoId)).append("',\n");
            sql.append("    '").append(escapeSql(dto.getNome())).append("',\n");
            sql.append("    '").append(dto.getData().format(SQL_DATETIME_FORMATTER)).append("',\n");
            sql.append("    '").append(escapeSql(dto.getLocal())).append("',\n");
            sql.append("    '").append(escapeSql(dto.getDescricao() != null ? dto.getDescricao() : "")).append("',\n");
            
            if (dto.getTags() != null && !dto.getTags().isEmpty()) {
                sql.append("    '").append(escapeSql(String.join(", ", dto.getTags()))).append("',\n");
            } else {
                sql.append("    NULL,\n");
            }
            
            sql.append("    CURRENT_TIMESTAMP\n");
            sql.append(");\n\n");
        }
        
        return sql.toString();
    }

    public String generateParticipantesInsertSQL() {
        List<Document> participantesDoc = mongoDAO.findAllParticipantes();
        StringBuilder sql = new StringBuilder();
        
        sql.append("-- Inserir participantes\n");
        
        for (Document doc : participantesDoc) {
            ParticipanteDTO dto = new ParticipanteDTO(doc);
            String mongoId = doc.getObjectId("_id").toString();
            
            sql.append("INSERT INTO participantes (mongo_id, nome, email, telefone, organizacao, data_cadastro) VALUES (\n");
            sql.append("    '").append(escapeSql(mongoId)).append("',\n");
            sql.append("    '").append(escapeSql(dto.getNome())).append("',\n");
            sql.append("    '").append(escapeSql(dto.getEmail())).append("',\n");
            
            if (dto.getTelefone() != null && !dto.getTelefone().isEmpty()) {
                sql.append("    '").append(escapeSql(dto.getTelefone())).append("',\n");
            } else {
                sql.append("    NULL,\n");
            }
            
            if (dto.getOrganizacao() != null && !dto.getOrganizacao().isEmpty()) {
                sql.append("    '").append(escapeSql(dto.getOrganizacao())).append("',\n");
            } else {
                sql.append("    NULL,\n");
            }
            
            sql.append("    CURRENT_TIMESTAMP\n");
            sql.append(");\n\n");
        }
        
        return sql.toString();
    }

    public String generateCompleteSQL() {
        StringBuilder sql = new StringBuilder();
        
        sql.append("-- ==================== EXPORTAÇÃO SQL ====================\n");
        sql.append("-- Gerado em: ").append(LocalDateTime.now().format(SQL_DATETIME_FORMATTER)).append("\n\n");
        
        sql.append(generateCreateTableSQL());
        sql.append("\n");
        sql.append(generateEventosInsertSQL());
        sql.append("\n");
        sql.append(generateParticipantesInsertSQL());
        
        return sql.toString();
    }

    public void exportSQLToFile(String filePath) throws IOException {
        String sql = generateCompleteSQL();
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(sql);
        }
    }

    private String escapeSql(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("'", "''").replace("\n", " ").replace("\r", "");
    }

    // ==================== ESTATÍSTICAS ====================
    public Map<String, Object> getEstatisticas() {
        Map<String, Object> stats = new HashMap<>();
        
        // MongoDB
        stats.put("eventosMongoDB", mongoDAO.findAllEventos().size());
        stats.put("participantesMongoDB", mongoDAO.findAllParticipantes().size());
        
        return stats;
    }
}
