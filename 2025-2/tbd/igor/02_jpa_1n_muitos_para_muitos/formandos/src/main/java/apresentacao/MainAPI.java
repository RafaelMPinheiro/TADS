package apresentacao;

import java.time.LocalDateTime;

import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import negocio.Aluno;
import negocio.Endereco;
import persistencia.AlunoDAO;

public class MainAPI {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        AlunoDAO alunoDAO = new AlunoDAO(emf);


        Aluno aluno = new Aluno();
        Endereco endereco = new Endereco();
        endereco.setBairro("bgv");
        endereco.setRua("alfredo huch");
        aluno.setNome("marcos");
        aluno.setEndereco(endereco);
        alunoDAO.adicionar(aluno);
        
        Javalin app = Javalin.create().start(7000);
        app.get("/", ctx -> {
            // ctx.html(alunoDAO.listar().toString());
            ctx.json(alunoDAO.listar());
        });
        app.post("/add", ctx -> {
            Aluno a = new Aluno();
            a.setNome(ctx.formParam("nome"));
            alunoDAO.adicionar(aluno);
            ctx.json("ok");
        });
    }
}
