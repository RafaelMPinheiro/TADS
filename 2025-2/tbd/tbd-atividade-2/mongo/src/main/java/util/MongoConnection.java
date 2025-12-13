package util;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {
    private static MongoConnection instance;
    private final MongoClient mongoClient;
    private final MongoDatabase database;

    private MongoConnection() {
        String connectionString = "mongodb://admin:admin123@localhost:27017/eventos_db?authSource=admin";
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase("eventos_db");
        System.out.println("✓ Conexão com MongoDB estabelecida");
    }

    public static MongoConnection getInstance() {
        if (instance == null) {
            synchronized (MongoConnection.class) {
                if (instance == null) {
                    instance = new MongoConnection();
                }
            }
        }
        return instance;
    }

    public MongoDatabase getDatabase() {
        return database;
    }

    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("✓ Conexão com MongoDB fechada");
        }
    }
}
