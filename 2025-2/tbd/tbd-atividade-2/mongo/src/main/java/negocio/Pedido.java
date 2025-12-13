package negocio;

import java.util.Objects;

import org.bson.Document;
import persistencia.MongoPedidosDAO;

public class Pedido {
    private final MongoPedidosDAO mongoDAO;
    private String codigo;
    private String cliente;
    private Double valor;
    private String status;

    public Pedido() {
        this.mongoDAO = new MongoPedidosDAO();

    }

    public Pedido(MongoPedidosDAO mongoDAO) {
        this.mongoDAO = mongoDAO;
    }

    public String adicionarPedido() {
        Document pedido = mongoDAO.findPedidoByCodigo(this.getCodigo());

        if (Objects.nonNull(pedido)) {
            return "Já existe um pedido com o código " + this.getCodigo();
        }

        return mongoDAO.createPedido(this);
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
