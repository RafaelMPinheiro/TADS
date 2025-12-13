package persistencia;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import negocio.Pedido;

import org.bson.Document;

import util.MongoConnection;

import java.util.Date;

public class MongoPedidosDAO {
    private final MongoCollection<Document> pedidosCollection;

    public MongoPedidosDAO() {
        MongoDatabase database = MongoConnection.getInstance().getDatabase();
        this.pedidosCollection = database.getCollection("pedidos");
    }

    public String createPedido(Pedido pedido) {
        Document novoPedido = new Document()
                .append("codigo", pedido.getCodigo())
                .append("cliente", pedido.getCliente())
                .append("valor", pedido.getValor())
                .append("status", pedido.getStatus())
                .append("dataCriacao", new Date());

        pedidosCollection.insertOne(novoPedido);
        return novoPedido.getObjectId("_id").toString();
    }

    public Document findPedidoByCodigo(String codigo) {
        Document pedido = pedidosCollection.find(Filters.eq("codigo", codigo)).first();

        return pedido;
    }
}
