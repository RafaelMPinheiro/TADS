package apresentacao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinMustache;
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
        
        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinMustache());
            config.staticFiles.add("/static", Location.CLASSPATH);
        }).start(7070);
       

        app.get("/", ctx -> {
            Map<String, Object> model = new HashMap<>();
            model.put("vetAluno", alunoDAO.listar());            
            ctx.render("/templates/index.html", model);
        });

        app.get("/api", ctx -> {
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
