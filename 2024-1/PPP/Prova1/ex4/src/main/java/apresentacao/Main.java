package apresentacao;

import negocio.*;

public class Main {
    public static void main(String[] args) {
        Controle controle = new Controle();

        ProjetorCommand projetorCommand = new ProjetorCommand("Projetor");

        controle.setAparelho(0, projetorCommand);

        controle.buttonAPressed(0);
        controle.buttonBPressed(0);
    }
}