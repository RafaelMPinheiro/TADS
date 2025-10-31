package biblioteca;

import java.util.List;
import java.util.Map;

import io.javalin.Javalin;
import jakarta.persistence.*;
import negocio.Livro;
import negocio.Usuario;
import persistencia.*;
import util.CacheService;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("biblioteca");
        CacheService cacheService = new CacheService();

        LivroDAO livroDAO = new LivroDAO(emf);

        popula(emf);

        var app = Javalin.create().start(7070);

        app.get("/livros", ctx -> {
            String autor = ctx.queryParam("autor");

            if (autor == null || autor.trim().isEmpty()) {
                ctx.status(400).json(Map.of("erro", "Parâmetro 'autor' é obrigatório"));
                return;
            }

            String chaveCache = cacheService.generateCacheKey("lista", "livros-do-" + autor.trim());
            if (cacheService.existsInCache(chaveCache)) {
                List<Livro> dadosCache = cacheService.getCacheAsList(chaveCache, Livro.class);
                if (dadosCache != null) {
                    ctx.header("X-Cache", "HIT");
                    ctx.json(dadosCache);
                    return;
                }
                cacheService.deleteFromCache(chaveCache);
            }

            List<Livro> livros = livroDAO.buscarPorAutor(autor);

            cacheService.setCache(chaveCache, livros);

            ctx.header("X-Cache", "MISS");
            ctx.json(livros);
        });
    }

    private static void popula(EntityManagerFactory emf) {
        var em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            Livro livro1 = new Livro("Livro 1", "Autor 1", "2025");
            em.persist(livro1);
            Livro livro2 = new Livro("Livro 2", "Autor 2", "2025");
            em.persist(livro2);
            Livro livro3 = new Livro("Livro 3", "Autor 3", "2025");
            em.persist(livro3);

            Usuario usuario1 = new Usuario("Usuario 1", "usuario1@email.com");
            em.persist(usuario1);
            Usuario usuario2 = new Usuario("Usuario 2", "usuario2@email.com");
            em.persist(usuario2);
            Usuario usuario3 = new Usuario("Usuario 3", "usuario3@email.com");
            em.persist(usuario3);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erro ao criar dados fictícios: " + e.getMessage());
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}