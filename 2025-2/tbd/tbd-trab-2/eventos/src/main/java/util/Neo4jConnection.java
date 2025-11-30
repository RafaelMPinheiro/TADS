package util;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

public class Neo4jConnection implements AutoCloseable {
    private static Neo4jConnection instance;
    private final Driver driver;

    private Neo4jConnection() {
        this.driver = GraphDatabase.driver(
            "bolt://localhost:7687",
            AuthTokens.basic("neo4j", "senha123")
        );
        System.out.println("✓ Conexão com Neo4j estabelecida");
    }

    public static Neo4jConnection getInstance() {
        if (instance == null) {
            synchronized (Neo4jConnection.class) {
                if (instance == null) {
                    instance = new Neo4jConnection();
                }
            }
        }
        return instance;
    }

    public Session getSession() {
        return driver.session();
    }

    @Override
    public void close() {
        if (driver != null) {
            driver.close();
            System.out.println("✓ Conexão com Neo4j fechada");
        }
    }
}
