package apresentacao;

import golpes.*;
import negocio.*;

public class Main {
    public static void main(String[] args) {
        Soco soco = new Soco(7);
        Chute chute = new Chute(8);
        Voadora voadora = new Voadora(9);

        Lutador balboa = new Lutador("Balboa");

        balboa.atacar(voadora);
        System.out.println("=====");
        balboa.atacar(chute);
        System.out.println("=====");
        balboa.atacar(soco);
    }
}