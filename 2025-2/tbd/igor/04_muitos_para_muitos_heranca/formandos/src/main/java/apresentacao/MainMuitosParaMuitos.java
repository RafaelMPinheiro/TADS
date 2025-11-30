package apresentacao;

import java.time.LocalDateTime;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import negocio.Aluno;
import negocio.Endereco;
import negocio.Matricula;
import negocio.Trabalho;
import persistencia.AlunoDAO;

public class MainMuitosParaMuitos {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        EntityManager entityManager = emf.createEntityManager();

         AlunoDAO alunoDAO = new AlunoDAO(emf);

        // adicionar
        Aluno aluno = new Aluno();
        Endereco endereco = new Endereco();
        endereco.setBairro("bgv");
        endereco.setRua("alfredo huch");
        aluno.setNome("marcos");      
        aluno.setEndereco(endereco); 
        Matricula matricula = new Matricula();
        matricula.setDataHora(LocalDateTime.now());
        matricula.setAluno(aluno);
        aluno.getMatriculas().add(matricula);
        alunoDAO.adicionar(aluno);


        entityManager.getTransaction().begin();
        Trabalho trabalho = new Trabalho();
        trabalho.setDescricao("trabalho");
        trabalho.getAlunos().add(aluno);
        aluno.getTrabalhos().add(trabalho);
        entityManager.persist(trabalho);
        entityManager.merge(aluno);
        entityManager.getTransaction().commit();
    }

}
