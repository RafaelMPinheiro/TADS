package apresentacao;

import java.util.Map;

import dto.*;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.rendering.template.JavalinMustache;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import negocio.CacheService;
import negocio.Formando;
import persistencia.FormandoDAO;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("meuPU");
        FormandoDAO formandoDAO = new FormandoDAO(emf);
        CacheService cacheService = new CacheService();

        criarDadosFicticios(formandoDAO, emf);

        var app = Javalin.create(config -> {
            config.fileRenderer(new JavalinMustache());
            config.staticFiles.add("/static", Location.CLASSPATH);
        }).start(7070);

        app.get("/", ctx -> {
            ctx.render("/templates/index.html", Map.of(
                    "titulo", "API de Formandos - Trabalho 1",
                    "descricao", "Sistema com JPA + Redis Cache"));
        });

        // === TRABALHO 1 - ENDPOINTS ===

        // Item 1: NamedQuery para buscar formandos por curso ou turma
        app.get("/formandos/filtrar", ctx -> {
            String curso = ctx.queryParam("curso");
            String turma = ctx.queryParam("turma");

            java.util.List<Formando> formandos = formandoDAO.findByCursoOrTurma(curso, turma);
            java.util.List<FormandoDTO> resposta = formandos.stream()
                    .map(FormandoDTO::new)
                    .collect(java.util.stream.Collectors.toList());

            ctx.json(resposta);
        });

        // Item 2 + 3: Consulta com agregação + Cache Redis para relatórios
        app.get("/relatorios/formandos-por-curso", ctx -> {
            String chaveCache = cacheService.generateCacheKey("relatorio", "formandos-por-curso");

            // Verificar se existe no cache
            if (cacheService.existsInCache(chaveCache)) {
                Map<String, Long> dadosCache = cacheService.getCacheAsMap(chaveCache);
                RelatorioDTO resposta = new RelatorioDTO(dadosCache);
                ctx.header("X-Cache", "HIT");
                ctx.json(resposta);
                return;
            }

            // Se não existe no cache, consultar banco e armazenar no cache
            Map<String, Long> dados = formandoDAO.countFormandosByCurso();
            cacheService.setCache(chaveCache, dados);

            RelatorioDTO resposta = new RelatorioDTO(dados);

            ctx.header("X-Cache", "MISS");
            ctx.json(resposta);
        });

        // Item 4: Paginação na listagem de formandos usando JPA puro
        app.get("/formandos/paginado", ctx -> {
            int pagina = ctx.queryParamAsClass("pagina", Integer.class).getOrDefault(0);
            int tamanho = ctx.queryParamAsClass("tamanho", Integer.class).getOrDefault(10);

            // Validação básica
            if (pagina < 0)
                pagina = 0;
            if (tamanho < 1 || tamanho > 100)
                tamanho = 10;

            // Buscar dados paginados
            java.util.List<Formando> formandos = formandoDAO.findAllPaginated(pagina, tamanho);
            long totalElementos = formandoDAO.countAll();

            // Converter para DTOs
            java.util.List<FormandoDTO> conteudo = formandos.stream()
                    .map(FormandoDTO::new)
                    .collect(java.util.stream.Collectors.toList());

            // Criar resposta paginada
            PaginadoDTO<FormandoDTO> resposta = new PaginadoDTO<>(conteudo, pagina, (int) totalElementos);

            ctx.json(resposta);
        });

        // Item 5: Cache com Redis para listagem de formandos por curso (com TTL)
        app.get("/formandos/cache", ctx -> {
            String curso = ctx.queryParam("curso");

            if (curso == null || curso.trim().isEmpty()) {
                ctx.status(400).json(Map.of("erro", "Parâmetro 'curso' é obrigatório"));
                return;
            }

            String chaveCache = cacheService.generateCacheKey("formandos", "curso" + curso.hashCode());

            // Verificar cache
            if (cacheService.existsInCache(chaveCache)) {
                java.util.List<FormandoDTO> dadosCache = cacheService.getCacheAsList(chaveCache, FormandoDTO.class);
                if (dadosCache != null) {
                    ctx.header("X-Cache", "HIT");
                    ctx.json(dadosCache);
                    return;
                }
                cacheService.deleteFromCache(chaveCache);
            }

            // Buscar no banco, converter para DTO e cachear
            java.util.List<Formando> formandos = formandoDAO.findByCurso(curso);
            java.util.List<FormandoDTO> resposta = formandos.stream()
                    .map(FormandoDTO::new)
                    .collect(java.util.stream.Collectors.toList());

            cacheService.setCache(chaveCache, resposta);

            ctx.header("X-Cache", "MISS");
            ctx.json(resposta);
        });
    }

    private static void criarDadosFicticios(FormandoDAO formandoDAO, EntityManagerFactory emf) {
        var em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Verificar se já existem dados
            long count = em.createQuery("SELECT COUNT(f) FROM Formando f", Long.class).getSingleResult();
            if (count > 0) {
                System.out.println("Dados já existem no banco. Pulando criação de dados fictícios.");
                em.getTransaction().rollback();
                return;
            }

            System.out.println("Criando dados fictícios para testes...");

            // Criar alunos
            negocio.Aluno aluno1 = new negocio.Aluno();
            aluno1.setNome("João Silva");
            aluno1.setMatricula("2021001");
            em.persist(aluno1);

            negocio.Aluno aluno2 = new negocio.Aluno();
            aluno2.setNome("Maria Santos");
            aluno2.setMatricula("2021002");
            em.persist(aluno2);

            negocio.Aluno aluno3 = new negocio.Aluno();
            aluno3.setNome("Pedro Oliveira");
            aluno3.setMatricula("2021003");
            em.persist(aluno3);

            negocio.Aluno aluno4 = new negocio.Aluno();
            aluno4.setNome("Ana Costa");
            aluno4.setMatricula("2021004");
            em.persist(aluno4);

            negocio.Aluno aluno5 = new negocio.Aluno();
            aluno5.setNome("Carlos Ferreira");
            aluno5.setMatricula("2021005");
            em.persist(aluno5);

            negocio.Aluno aluno6 = new negocio.Aluno();
            aluno6.setNome("Lucia Pereira");
            aluno6.setMatricula("2021006");
            em.persist(aluno6);

            // Criar matrículas
            negocio.Matricula matricula1 = new negocio.Matricula();
            matricula1.setAluno(aluno1);
            matricula1.setCurso("Ciência da Computação");
            em.persist(matricula1);

            negocio.Matricula matricula2 = new negocio.Matricula();
            matricula2.setAluno(aluno2);
            matricula2.setCurso("Engenharia de Software");
            em.persist(matricula2);

            negocio.Matricula matricula3 = new negocio.Matricula();
            matricula3.setAluno(aluno3);
            matricula3.setCurso("Ciência da Computação");
            em.persist(matricula3);

            negocio.Matricula matricula4 = new negocio.Matricula();
            matricula4.setAluno(aluno4);
            matricula4.setCurso("Sistemas de Informação");
            em.persist(matricula4);

            negocio.Matricula matricula5 = new negocio.Matricula();
            matricula5.setAluno(aluno5);
            matricula5.setCurso("Engenharia de Software");
            em.persist(matricula5);

            negocio.Matricula matricula6 = new negocio.Matricula();
            matricula6.setAluno(aluno6);
            matricula6.setCurso("Ciência da Computação");
            em.persist(matricula6);

            // Criar formandos
            negocio.Formando formando1 = new negocio.Formando();
            formando1.setMatricula(matricula1);
            formando1.setCurso("Ciência da Computação");
            formando1.setTurma("2021A");
            em.persist(formando1);

            negocio.Formando formando2 = new negocio.Formando();
            formando2.setMatricula(matricula2);
            formando2.setCurso("Engenharia de Software");
            formando2.setTurma("2021B");
            em.persist(formando2);

            negocio.Formando formando3 = new negocio.Formando();
            formando3.setMatricula(matricula3);
            formando3.setCurso("Ciência da Computação");
            formando3.setTurma("2021A");
            em.persist(formando3);

            negocio.Formando formando4 = new negocio.Formando();
            formando4.setMatricula(matricula4);
            formando4.setCurso("Sistemas de Informação");
            formando4.setTurma("2021C");
            em.persist(formando4);

            negocio.Formando formando5 = new negocio.Formando();
            formando5.setMatricula(matricula5);
            formando5.setCurso("Engenharia de Software");
            formando5.setTurma("2021B");
            em.persist(formando5);

            negocio.Formando formando6 = new negocio.Formando();
            formando6.setMatricula(matricula6);
            formando6.setCurso("Ciência da Computação");
            formando6.setTurma("2021D");
            em.persist(formando6);

            em.getTransaction().commit();
            System.out.println("Dados fictícios criados com sucesso!");
            System.out.println("- 6 alunos criados");
            System.out.println("- 6 matrículas criadas");
            System.out.println("- 6 formandos criados");
            System.out.println("- Cursos: Ciência da Computação, Engenharia de Software, Sistemas de Informação");
            System.out.println("- Turmas: 2021A, 2021B, 2021C, 2021D");

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
