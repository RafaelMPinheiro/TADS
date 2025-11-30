package dto;

import java.util.List;
import java.util.Map;

public class RelatorioDTO {
    private int totalEventos;
    private int totalParticipantes;
    private List<EventoDTO> eventos;
    private Map<String, List<Map<String, Object>>> relacionamentos;
    private String mensagem;

    public RelatorioDTO() {
    }

    public RelatorioDTO(int totalEventos, int totalParticipantes, List<EventoDTO> eventos) {
        this.totalEventos = totalEventos;
        this.totalParticipantes = totalParticipantes;
        this.eventos = eventos;
    }

    public int getTotalEventos() {
        return totalEventos;
    }

    public void setTotalEventos(int totalEventos) {
        this.totalEventos = totalEventos;
    }

    public int getTotalParticipantes() {
        return totalParticipantes;
    }

    public void setTotalParticipantes(int totalParticipantes) {
        this.totalParticipantes = totalParticipantes;
    }

    public List<EventoDTO> getEventos() {
        return eventos;
    }

    public void setEventos(List<EventoDTO> eventos) {
        this.eventos = eventos;
    }

    public Map<String, List<Map<String, Object>>> getRelacionamentos() {
        return relacionamentos;
    }

    public void setRelacionamentos(Map<String, List<Map<String, Object>>> relacionamentos) {
        this.relacionamentos = relacionamentos;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
