/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;
import negocio.Aluno;

/**
 *
 * @author iapereira
 */
public class AlunoDAO {
    private final EntityManager em;

    public AlunoDAO(EntityManagerFactory emf) {
        em = emf.createEntityManager();
    }
    
    public Aluno obterPorId(Long id){
        return em.find(Aluno.class, id);
    }
    
    public void atualizar(Aluno aluno){        
        em.getTransaction().begin();
        em.merge(aluno);
        em.getTransaction().commit();
    }
    
    public void remover(Long id){
        try {
            Aluno aluno = em.find(Aluno.class, id);
            em.getTransaction().begin();
            em.remove(aluno);
            em.getTransaction().commit();     
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    
    public void adicionar(Aluno aluno) {
        // System.out.println(aluno.getMatriculas().get(0).getDataHora().toString());
        em.getTransaction().begin();
        em.persist(aluno);
        em.getTransaction().commit();
    }
    
    public List<Aluno> listar() {
        // nativa
        // return em.createNativeQuery("select * from alunos inner join enderecos on enderecos.id = alunos.endereco_id left join matriculas on matriculas.aluno_id = alunos.id;", Aluno.class).getResultList();
        // jpql (java persistence query language)
        
        return em.createQuery("select a from Aluno a ", Aluno.class).getResultList();

    }

    public Aluno obterDiferente(Long id){
        try {
            TypedQuery<Aluno> query = em.createQuery("select a from Aluno a where a.id = :id ", Aluno.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch (Exception e) { 
            return new Aluno();
        }

    }
    
}
