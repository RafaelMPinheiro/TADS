package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import negocio.Formando;

public class FormandoDAO {
    private final EntityManager em;

    public FormandoDAO(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }

    /**
     * Item 5: Busca formandos por curso usando NamedQuery
     */
    public List<Formando> findByCurso(String curso) {
        return em
                .createNamedQuery("Formando.findByCurso", Formando.class)
                .setParameter("curso", curso)
                .getResultList();
    }

    /**
     * Item 1: Busca formandos por curso OU turma usando NamedQuery
     */
    public List<Formando> findByCursoOrTurma(String curso, String turma) {
        return em
                .createNamedQuery("Formando.findByCursoOrTurma", Formando.class)
                .setParameter("curso", curso)
                .setParameter("turma", turma)
                .getResultList();
    }

    /**
     * Item 2: Contagem de formandos por curso com GROUP BY
     */
    public Map<String, Long> countFormandosByCurso() {
        @SuppressWarnings("unchecked")
        List<Object[]> results = em
                .createNamedQuery("Formando.countByCurso")
                .getResultList();

        // Converter Object[] para Map<String, Long>
        Map<String, Long> cursoCounts = new HashMap<>();
        for (Object[] result : results) {
            String curso = (String) result[0];
            Long count = (Long) result[1];
            cursoCounts.put(curso, count);
        }

        return cursoCounts;
    }

    /**
     * Item 4: Listagem paginada de formandos usando JPA puro
     * 
     * @param page Número da página (começando em 0)
     * @param size Tamanho da página
     * @return Lista de formandos na página solicitada
     */
    public List<Formando> findAllPaginated(int page, int size) {
        return em.createQuery("SELECT f FROM Formando f ORDER BY f.id", Formando.class)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
    }

    /**
     * Item 4: Conta o total de formandos (para cálculo de páginas)
     */
    public long countAll() {
        return em.createQuery("SELECT COUNT(f) FROM Formando f", Long.class)
                .getSingleResult();
    }
}