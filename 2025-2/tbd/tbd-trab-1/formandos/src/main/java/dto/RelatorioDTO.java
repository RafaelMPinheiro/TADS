package dto;

import java.util.Map;

public class RelatorioDTO {
    private Map<String, Long> dados;
    
    public RelatorioDTO(Map<String, Long> dados) {
        this.dados = dados;
    }
    
    public Map<String, Long> getDados() {
        return dados;
    }
}