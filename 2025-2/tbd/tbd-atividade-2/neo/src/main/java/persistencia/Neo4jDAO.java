package persistencia;

import org.neo4j.driver.Session;
import org.neo4j.driver.Values;
import util.Neo4jConnection;

public class Neo4jDAO {

    public void createCidadeNode(String cidadeId, String nome) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            session.run(
                "MERGE (e:Cidade {id: $id}) SET e.nome = $nome",
                Values.parameters("id", cidadeId, "nome", nome)
            );
        }
    }

    public void addRotaToCidade(String cidade1Id, String cidade2Id) {
        try (Session session = Neo4jConnection.getInstance().getSession()) {
            session.run(
                "MATCH (p:Cidade {id: $cidade1Id}), (e:Cidade {id: $cidade2Id}) " +
                "MERGE (p)-[r:ROTA_PARA]->(e) " +
                "ON CREATE SET r.dataRelacionamento = datetime()",
                Values.parameters("cidade1Id", cidade1Id, "cidade2Id", cidade2Id)
            );
        }
    }

}
