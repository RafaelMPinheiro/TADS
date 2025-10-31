package negocio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import util.LocalDateTimeTypeAdapter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Serviço de cache usando Redis/Jedis
 * TTL configurado para 1 minutos em todas as chaves
 */
public class CacheService {

    private final JedisPool jedisPool;
    private final Gson gson;
    private static final int CACHE_TTL_SECONDS = 1 * 60; // 1 minuto

    public CacheService() {
        // Pool de conexões Redis na porta padrão
        this.jedisPool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);

        // Gson com adapter customizado para LocalDateTime
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                .create();
    }

    // Salva qualquer objeto no cache (serializa em JSON)
    public void setCache(String key, Object value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String jsonValue = gson.toJson(value);
            jedis.setex(key, CACHE_TTL_SECONDS, jsonValue); // setex = set + expire
        } catch (Exception e) {
            System.err.println("Erro ao salvar no cache: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Busca Map<String, Long> do cache (usado nos relatórios agregados)
    public Map<String, Long> getCacheAsMap(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            String jsonValue = jedis.get(key);
            if (jsonValue != null && !jsonValue.isEmpty()) {
                Type mapType = new TypeToken<Map<String, Long>>() {}.getType();
                return gson.fromJson(jsonValue, mapType);
            }
            return null;
        }
    }

    // Busca List<T> do cache (usado nas listagens de formandos)
    public <T> List<T> getCacheAsList(String key, Class<T> clazz) {
        try (Jedis jedis = jedisPool.getResource()) {
            String jsonValue = jedis.get(key);
            if (jsonValue != null && !jsonValue.isEmpty()) {
                Type listType = TypeToken.getParameterized(List.class, clazz).getType();
                return gson.fromJson(jsonValue, listType);
            }
            return null;
        } catch (Exception e) {
            System.err.println("Erro ao deserializar cache para lista: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    // Busca objeto genérico do cache
    public <T> T getCache(String key, Class<T> clazz) {
        try (Jedis jedis = jedisPool.getResource()) {
            String jsonValue = jedis.get(key);
            if (jsonValue != null && !jsonValue.isEmpty()) {
                return gson.fromJson(jsonValue, clazz);
            }
            return null;
        }
    }

    // Verifica se a chave existe
    public boolean existsInCache(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }

    // Remove uma chave do cache
    public void deleteFromCache(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    // Gera chave padronizada: "prefixo:identificador"
    public String generateCacheKey(String prefix, String identifier) {
        return prefix + ":" + identifier;
    }

    // Fecha o pool (chamar ao encerrar a aplicação)
    public void closePool() {
        if (jedisPool != null && !jedisPool.isClosed()) {
            jedisPool.close();
        }
    }
}