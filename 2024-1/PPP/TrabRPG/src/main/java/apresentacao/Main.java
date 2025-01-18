package apresentacao;

import java.util.Scanner;
import ferramenta.*;
import personagens.*;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Arma[] listArmas = { new Arco(), new Espada(), new Faca(), new Machado() };

        Personagem jogador1 = selecionarPersonagem(in, "1");
        Personagem jogador2 = selecionarPersonagem(in, "2");

        Personagem[] listPersonagens = { jogador1, jogador2 };
        selecionarArmas(listArmas, listPersonagens, in);

        int jogadorAtual = 1;
        while (jogador1.estaVivo() && jogador2.estaVivo()) {
            listarStatus(listPersonagens);
            Personagem jogadorAtualPersonagem = (jogadorAtual == 1) ? jogador1 : jogador2;
            acaoJogador(jogadorAtualPersonagem, listArmas, listPersonagens, in);
            jogadorAtual = (jogadorAtual == 1) ? 2 : 1;
        }
        listarStatus(listPersonagens);
        System.out.println("Fim de jogo!");
    }

    public static Personagem selecionarPersonagem(Scanner in, String jogador) {
        System.out.println("\nSelecione o personagem do jogador " + jogador + ":\n" +
                "1)Rainha\n" +
                "2)Rei\n" +
                "3)Cavaleiro");

        int escolha = in.nextInt();
        switch (escolha) {
            case 1:
                return new Rainha();
            case 2:
                return new Rei();
            case 3:
                return new Cavaleiro();
            default:
                return null;
        }
    }

    public static void selecionarArmas(Arma[] listArmas, Personagem[] listPersonagens, Scanner in) {
        for (Personagem p : listPersonagens) {
            listarArmas(listArmas, p.getNome());
            int armaSelecionada = in.nextInt();
            while (armaSelecionada < 1 || armaSelecionada > 4) {
                System.out.println("Opção inválida, selecione uma arma válida:");
                armaSelecionada = in.nextInt();
            }
            p.pegaArma(listArmas[armaSelecionada - 1]);
            System.out.println(p.getNome() + " selecionou a arma: " + p.getArma().getNome());
        }
    }

    public static void listarArmas(Arma[] listArmas, String jogador) {
        System.out.println("\nSelecione uma arma para " + jogador + ":\n");
        int i = 0;
        for (Arma a : listArmas) {
            i++;
            if (a.isDisponivel()) {
                System.out.println(i + ")" + a.getNome());
            }
        }
    }

    public static void listarStatus(Personagem[] listPersonagens) {
        System.out.println("Status dos personagens:");
        for (Personagem p : listPersonagens) {
            System.out.println("Nome: " + p.getNome() + " - Saúde: " + p.getSaude() +
                    " - Arma: " + p.getArma().getNome());
        }
    }

    public static void acaoJogador(Personagem jogadorAtualPersonagem, Arma[] listArmas,
            Personagem[] listPersonagens, Scanner in) {
        System.out.println(jogadorAtualPersonagem.getNome() + " você deseja atacar ou trocar de arma?\n" +
                "1)Atacar\n" +
                "2)Trocar de arma");

        int opcao = in.nextInt();
        if (opcao == 1) {
            Personagem alvo = (jogadorAtualPersonagem.equals(listPersonagens[0])) ? listPersonagens[1]
                    : listPersonagens[0];
            atacar(jogadorAtualPersonagem, alvo);
        } else {
            listarArmas(listArmas, jogadorAtualPersonagem.getNome());
            int armaSelecionada = in.nextInt();
            jogadorAtualPersonagem.pegaArma(listArmas[armaSelecionada - 1]);
        }
    }

    public static void atacar(Personagem atacante, Personagem defensor) {
        atacante.atacar(defensor);
    }
}
