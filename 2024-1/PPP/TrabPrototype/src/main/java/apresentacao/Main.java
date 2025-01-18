package apresentacao;

import negocio.Personagem;

public class Main {
    public static void main(String[] args) {
        Personagem guerreiro = new Personagem("Arthur", 100, "Guerreiro");

        Personagem guerreiroClone = guerreiro.clone();
        guerreiroClone.setNome("Lancelot");
        guerreiroClone.setVida(90);
        guerreiroClone.setEspecialidade("Paladino");

        System.out.println("Personagem original: ");
        exibirPersonagem(guerreiro);
        System.out.println("\nClone modificado: ");
        exibirPersonagem(guerreiroClone);

        Personagem arqueiro = new Personagem("Robin", 80, "Arqueiro");
        Personagem arqueiroClone = new Personagem(arqueiro);
        System.out.println("\nTerceiro personagem (Arqueiro) e seu clone:");
        exibirPersonagem(arqueiro);
        exibirPersonagem(arqueiroClone);
    }

    private static void exibirPersonagem(Personagem p) {
        System.out.println("Nome: " + p.getNome());
        System.out.println("Vida: " + p.getVida());
        System.out.println("Especialidade: " + p.getEspecialidade());
    }
}
