package apresentacao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import negocio.Aluno;
import negocio.Pessoa;
import negocio.Professor;
import negocio.Trabalho;
import persistencia.AlunoDAO;

public class MainJPQL {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager em = emf.createEntityManager();
        
        em.getTransaction().begin();
        Professor p = new Professor();
        p.setNome("igor");
        em.persist(p);
        em.getTransaction().commit();

        // em.getTransaction().begin();
        // Trabalho t = new Trabalho();
        // t.setDescricao("trabalho");
        // em.persist(t);
        // em.getTransaction().commit();
    
        // em.createNamedQuery("Trabalho.all", Trabalho.class).getResultList().forEach(t -> System.out.println(t.getAlunos().get(0).getId()));

        System.out.println(em.createNamedQuery("Pessoa.all").getResultList().toString());

        // AlunoDAO alunoDAO = new AlunoDAO(emf);
        // System.out.println(alunoDAO.obterDiferente(Long.valueOf("1")).getNome());
        
    }

}
