package apresentacao;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import negocio.Aluno;
import negocio.Endereco;
import negocio.Matricula;
import persistencia.AlunoDAO;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        
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

    
        alunoDAO.listar().forEach(p -> System.out.println(p));        
        // alunoDAO.remover(Integer.toUnsignedLong(1));
      

    }
}
