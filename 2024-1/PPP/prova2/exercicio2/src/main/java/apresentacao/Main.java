package apresentacao;

import negocio.Bolo;

public class Main {
    public static void main(String[] args) {
        Bolo bolo1 = Bolo.cloneDoBolo();
        System.out.println("Bolo 1: " + bolo1.toString());

        System.out.println("\n-------\n");
        
        Bolo bolo2 = Bolo.cloneDoBolo();
        bolo2.setSabor("Cenoura");
        System.out.println("Bolo 2: " + bolo2.toString());
    }
}