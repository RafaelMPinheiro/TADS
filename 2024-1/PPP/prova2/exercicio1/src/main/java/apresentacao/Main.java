package apresentacao;

import negocio.Beverage;
import negocio.CarregadorExtra;
import negocio.Mira;
import negocio.Silenciador;
import negocio.Sniper;

public class Main {
    public static void main(String[] args) {
        Beverage arma1 = new Sniper();
        arma1 = new Mira(arma1);
        System.out.println(arma1.getDescription());

        System.out.println("\n--------\n");

        arma1 = new Silenciador(arma1);
        arma1 = new CarregadorExtra(arma1);
        System.out.println(arma1.getDescription());
    }
}