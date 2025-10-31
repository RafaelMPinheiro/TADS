package persistencia;

import java.util.List;

import jakarta.persistence.*;
import negocio.Livro;

public class LivroDAO {
    private final EntityManager em;

    public LivroDAO(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    public List<Livro> buscarPorAutor(String autor) {
        System.out.println("teste");
        return em
                .createNamedQuery("Livro.buscarPorAutor", Livro.class)
                .setParameter("autor", autor)
                .getResultList();
    }
}
