package apresentacao;

import org.eclipse.jetty.util.log.Log;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import negocio.Aluno;
import negocio.Professor;

public class MainHeranca {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager entityManager = emf.createEntityManager();


        Professor igor = new Professor();
        // igor.setId(Long.MAX_VALUE);
        igor.setNome("Igor");
        igor.setSiape("1751904");

        Aluno jp = new Aluno();
        // jp.setId(Long.MIN_VALUE);
        jp.setMatricula("2023003259");
        jp.setNome("Joao Pedro");

        entityManager.getTransaction().begin();
        entityManager.persist(igor);
        entityManager.persist(jp);
        entityManager.getTransaction().commit();
    }

}
