package apresentacao;

import negocio.BigTech;
import negocio.Pessoa;

public class Main {
    public static void main(String[] args) {
        BigTech bigTech = new BigTech(10);

        Pessoa rafael = new Pessoa("Rafael");
        Pessoa pedro = new Pessoa("Pedro");
        Pessoa pablo = new Pessoa("Pablo");

        bigTech.subscribe(pablo);
        bigTech.subscribe(pedro);
        bigTech.subscribe(rafael);

        bigTech.setValorAcao(9);
        System.out.println("====");
        bigTech.setValorAcao(10);
    }
}