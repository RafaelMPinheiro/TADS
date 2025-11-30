package apresentacao;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;

import negocio.Pessoa;

import java.time.LocalDate;
import java.util.stream.Collectors;
import static org.neo4j.driver.Values.parameters;

public class Main {
    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687",
                AuthTokens.basic("neo4j", "password"));
        String instrucaoCypher = "match(p:Pessoa) return p;";
        try(Session session = driver.session()){
            // Result rs = session.run(instrucaoCypher, parameters("nome", "Igor Pereira"));
            Result rs = session.run(instrucaoCypher);
            while(rs.hasNext()) {
                Record r = rs.next();
                Pessoa p = mappper(r);
                System.out.println(p);
            }
            // session.run("MATCH (c:Cidade) where c.nome = 'Rio Grande' DETACH DELETE c");
            // session.run("MATCH (p:Pessoa) where p.nome = 'Igor Pereira' SET p.nome = 'Pedro'");
        }
    }

    private static Pessoa mappper(Record r) {
        Pessoa p = new Pessoa();
        p.setId(r.get(0).asNode().id());
        p.setNome(r.get(0).asNode().get("nome").asString());
        p.setProfissao(r.get(0).asNode().get("profissao").asString());
        return p;
    }
}