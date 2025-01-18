package decorator;

import negocio.*;

public class Main {
    public static void main(String[] args) {
        Beverage ex1 = new HTMLDocument("Teste");
        ex1 = new H1(ex1, "Teste 1");
        ex1 = new Link(ex1, "https://www.google.fr/", "Google");
        
        Beverage div = new DivBeverage();
        div = new H1(div, "Teste 2");
        div = new P(div, "Teste 3");
        ex1 = new Div(ex1, div);

        ex1 = new Image(ex1,
                "https://s2-techtudo.glbimg.com/L9wb1xt7tjjL-Ocvos-Ju0tVmfc=/0x0:1200x800/984x0/smart/filters:strip_icc()/i.s3.glbimg.com/v1/AUTH_08fbf48bc0524877943fe86e43087e7a/internal_photos/bs/2023/q/l/TIdfl2SA6J16XZAy56Mw/canvaai.png",
                "Imagem teste");

        ex1.build();
    }
}