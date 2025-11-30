package apresentacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.EventoDTO;
import dto.ParticipanteDTO;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinMustache;
import negocio.Evento;
import negocio.Participante;
import util.ExportService;
import persistencia.MongoEventoDAO;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    private static Evento evento;
    private static Participante participante;
    private static ExportService exportService;
    private static ObjectMapper objectMapper;

    public static void main(String[] args) {
        System.out.println("=== Iniciando Sistema de Gerenciamento de Eventos ===");
        
        evento = new Evento();
        participante = new Participante();
        exportService = new ExportService(new MongoEventoDAO());

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        var app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> it.anyHost());
            });
            config.fileRenderer(new JavalinMustache());
        }).start(7070);

        System.out.println("✓ Servidor iniciado na porta 7070");
        System.out.println("✓ Dashboard disponível em: http://localhost:7070");

        // ==================== ROTAS ====================
        app.get("/", ctx -> {
            Map<String, Object> stats = exportService.getEstatisticas();
            ctx.json(Map.of(
                "mensagem", "Sistema de Gerenciamento de Eventos",
                "status", "ONLINE",
                "estatisticas", stats,
                "endpoints", Map.of(
                    "eventos", "/eventos",
                    "participantes", "/participantes",
                    "filtrar", "/eventos/filtrar",
                    "relacionamentos", "/eventos/{id}/relacionamentos",
                    "exportarJSON", "/export/json",
                    "migrarSQL", "/export/sql"
                )
            ));
        });

        // ==================== CRUD EVENTOS (MongoDB) ====================

        // Listar todos os eventos
        app.get("/eventos", ctx -> {
            List<EventoDTO> eventos = evento.listarTodos();
            ctx.json(eventos);
        });

        // Filtrar eventos
        app.get("/eventos/filtrar", ctx -> {
            String local = ctx.queryParam("local");
            String dataInicio = ctx.queryParam("dataInicio");
            String dataFim = ctx.queryParam("dataFim");
            String palavraChave = ctx.queryParam("palavraChave");
            
            List<EventoDTO> eventos;
            
            try {
                if (local != null && !local.isEmpty()) {
                    eventos = evento.filtrarPorLocal(local);
                } else if (dataInicio != null && dataFim != null) {
                    LocalDateTime inicio = LocalDateTime.parse(dataInicio);
                    LocalDateTime fim = LocalDateTime.parse(dataFim);
                    eventos = evento.filtrarPorPeriodo(inicio, fim);
                } else if (palavraChave != null && !palavraChave.isEmpty()) {
                    eventos = evento.filtrarPorPalavraChave(palavraChave);
                } else {
                    eventos = evento.listarTodos();
                }
                ctx.json(eventos);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // Buscar evento por ID
        app.get("/eventos/{id}", ctx -> {
            String id = ctx.pathParam("id");
            EventoDTO eventoDTO = evento.buscarPorId(id);
            
            if (eventoDTO != null) {
                ctx.json(eventoDTO);
            } else {
                ctx.status(404).json(Map.of("erro", "Evento não encontrado"));
            }
        });

        // Criar novo evento
        app.post("/eventos", ctx -> {
            try {
                Map<String, Object> body = ctx.bodyAsClass(Map.class);
                
                String nome = (String) body.get("nome");
                String dataStr = (String) body.get("data");
                String local = (String) body.get("local");
                String descricao = (String) body.get("descricao");
                List<String> tags = (List<String>) body.get("tags");
                
                LocalDateTime data = LocalDateTime.parse(dataStr);
                
                String eventoId = evento.criar(nome, data, local, descricao, tags);
                
                ctx.status(201).json(Map.of(
                    "mensagem", "Evento criado com sucesso",
                    "id", eventoId
                ));
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // Atualizar evento
        app.put("/eventos/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                Map<String, Object> body = ctx.bodyAsClass(Map.class);
                
                String nome = (String) body.get("nome");
                String dataStr = (String) body.get("data");
                String local = (String) body.get("local");
                String descricao = (String) body.get("descricao");
                List<String> tags = (List<String>) body.get("tags");
                
                LocalDateTime data = LocalDateTime.parse(dataStr);
                
                evento.atualizar(id, nome, data, local, descricao, tags);
                
                ctx.json(Map.of("mensagem", "Evento atualizado com sucesso"));
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // Deletar evento
        app.delete("/eventos/{id}", ctx -> {
            try {
                String id = ctx.pathParam("id");
                evento.deletar(id);
                ctx.json(Map.of("mensagem", "Evento deletado com sucesso"));
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // ==================== CRUD PARTICIPANTES ====================

        // Listar todos os participantes
        app.get("/participantes", ctx -> {
            List<ParticipanteDTO> participantes = participante.listarTodos();
            ctx.json(participantes);
        });

        // Buscar participante por ID
        app.get("/participantes/{id}", ctx -> {
            String id = ctx.pathParam("id");
            ParticipanteDTO participanteDTO = participante.buscarPorId(id);
            
            if (participanteDTO != null) {
                ctx.json(participanteDTO);
            } else {
                ctx.status(404).json(Map.of("erro", "Participante não encontrado"));
            }
        });

        // Criar novo participante
        app.post("/participantes", ctx -> {
            try {
                Map<String, Object> body = ctx.bodyAsClass(Map.class);
                
                String nome = (String) body.get("nome");
                String email = (String) body.get("email");
                String telefone = (String) body.get("telefone");
                String organizacao = (String) body.get("organizacao");
                
                String participanteId = participante.criar(nome, email, telefone, organizacao);
                
                ctx.status(201).json(Map.of(
                    "mensagem", "Participante criado com sucesso",
                    "id", participanteId
                ));
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // ==================== RELACIONAMENTOS (Neo4j) ====================

        // Adicionar participante a evento
        app.post("/eventos/{eventoId}/participantes/{participanteId}", ctx -> {
            try {
                String eventoId = ctx.pathParam("eventoId");
                String participanteId = ctx.pathParam("participanteId");
                
                evento.adicionarParticipante(eventoId, participanteId);
                
                ctx.json(Map.of("mensagem", "Participante adicionado ao evento"));
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // Adicionar organizador a evento
        app.post("/eventos/{eventoId}/organizadores/{participanteId}", ctx -> {
            try {
                String eventoId = ctx.pathParam("eventoId");
                String participanteId = ctx.pathParam("participanteId");
                
                evento.adicionarOrganizador(eventoId, participanteId);
                
                ctx.json(Map.of("mensagem", "Organizador adicionado ao evento"));
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // Listar todos os envolvidos em um evento (participantes + organizadores)
        app.get("/eventos/{id}/relacionamentos", ctx -> {
            try {
                String eventoId = ctx.pathParam("id");
                Map<String, List<Map<String, Object>>> envolvidos = 
                    evento.listarTodosEnvolvidos(eventoId);
                ctx.json(envolvidos);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // Listar apenas participantes de um evento
        app.get("/eventos/{id}/participantes", ctx -> {
            try {
                String eventoId = ctx.pathParam("id");
                List<Map<String, Object>> participantes = evento.listarParticipantes(eventoId);
                ctx.json(participantes);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // Listar apenas organizadores de um evento
        app.get("/eventos/{id}/organizadores", ctx -> {
            try {
                String eventoId = ctx.pathParam("id");
                List<Map<String, Object>> organizadores = evento.listarOrganizadores(eventoId);
                ctx.json(organizadores);
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // Promover participante para organizador
        app.post("/eventos/{eventoId}/promover/{participanteId}", ctx -> {
            try {
                String eventoId = ctx.pathParam("eventoId");
                String participanteId = ctx.pathParam("participanteId");
                
                evento.promoverParticipanteParaOrganizador(eventoId, participanteId);
                
                ctx.json(Map.of(
                    "mensagem", "Participante promovido a organizador com sucesso",
                    "nota", "O participante agora possui relacionamento ORGANIZOU (e pode manter PARTICIPOU)"
                ));
            } catch (IllegalArgumentException e) {
                ctx.status(400).json(Map.of("erro", e.getMessage()));
            }
        });

        // ==================== EXPORTAÇÃO JSON ====================

        // Exportar eventos para JSON
        app.get("/export/json/eventos", ctx -> {
            String json = exportService.exportEventosToJson();
            ctx.contentType("application/json");
            ctx.result(json);
        });

        // Exportar participantes para JSON
        app.get("/export/json/participantes", ctx -> {
            String json = exportService.exportParticipantesToJson();
            ctx.contentType("application/json");
            ctx.result(json);
        });

        // Exportar tudo para JSON
        app.get("/export/json", ctx -> {
            Map<String, Object> exportData = new HashMap<>();
            exportData.put("eventos", exportService.exportEventosToJson());
            exportData.put("participantes", exportService.exportParticipantesToJson());
            exportData.put("dataExportacao", LocalDateTime.now());
            
            ctx.contentType("application/json");
            ctx.json(exportData);
        });

        // ==================== EXPORTAÇÃO SQL ====================

        // Gerar SQL CREATE TABLE
        app.get("/export/sql/create-tables", ctx -> {
            String sql = exportService.generateCreateTableSQL();
            ctx.contentType("text/plain; charset=utf-8");
            ctx.result(sql);
        });

        // Gerar SQL INSERT para eventos
        app.get("/export/sql/eventos", ctx -> {
            String sql = exportService.generateEventosInsertSQL();
            ctx.contentType("text/plain; charset=utf-8");
            ctx.result(sql);
        });

        // Gerar SQL INSERT para participantes
        app.get("/export/sql/participantes", ctx -> {
            String sql = exportService.generateParticipantesInsertSQL();
            ctx.contentType("text/plain; charset=utf-8");
            ctx.result(sql);
        });

        // Gerar SQL completo (CREATE + INSERT)
        app.get("/export/sql", ctx -> {
            String sql = exportService.generateCompleteSQL();
            ctx.contentType("text/plain; charset=utf-8");
            ctx.result(sql);
        });

        // Baixar arquivo SQL completo
        app.get("/export/sql/download", ctx -> {
            String sql = exportService.generateCompleteSQL();
            ctx.contentType("application/sql");
            ctx.header("Content-Disposition", "attachment; filename=eventos_export.sql");
            ctx.result(sql);
        });

        // ==================== ESTATÍSTICAS ====================

        app.get("/stats", ctx -> {
            Map<String, Object> stats = exportService.getEstatisticas();
            ctx.json(stats);
        });
    }
}