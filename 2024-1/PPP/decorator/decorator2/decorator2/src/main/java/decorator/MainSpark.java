package decorator;

import java.util.HashMap;
import java.util.Map;

import negocio.DocumentMarkdown;
import negocio.H1;
import negocio.Image;
import negocio.Markdown;
import spark.ModelAndView;
import spark.template.mustache.MustacheTemplateEngine;

import static spark.Spark.get;

public class MainSpark {

    static Markdown m1 = new DocumentMarkdown();

    public static void main(String args[]) {

        get("/", (request, response) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.html"); // hello.mustache file is in resources/templates directory
        }, new MustacheTemplateEngine());

        get("/adicionar/h1", (request, response) -> {
            m1 = new H1(m1, "Meu MD");
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.html"); // hello.mustache file is in resources/templates directory
        }, new MustacheTemplateEngine());

        get("/adicionar/image", (request, response) -> {
            m1 = new Image(m1, "dog", "https://pipz.com/static/images/blog/eddie.png");
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "index.html"); // hello.mustache file is in resources/templates directory
        }, new MustacheTemplateEngine());

        get("/gerar", (req, res) -> m1.getCode());

    }
}
