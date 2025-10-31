package dto;

import java.util.List;

public class PaginadoDTO<T> {
    private List<T> dados;
    private int pagina;
    private int total;
    
    public PaginadoDTO(List<T> dados, int pagina, int total) {
        this.dados = dados;
        this.pagina = pagina;
        this.total = total;
    }
    
    public List<T> getDados() {
        return dados;
    }
    
    public int getPagina() {
        return pagina;
    }
    
    public int getTotal() {
        return total;
    }
}