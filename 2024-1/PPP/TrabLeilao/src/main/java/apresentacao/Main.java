package apresentacao;

import negocio.Leilao;
import negocio.Leiloeiro;
import negocio.Licitante;

public class Main {
    public static void main(String[] args) {
        Leilao leilao = new Leilao("Carro", 100);

        Leiloeiro leiloeiro = new Leiloeiro(leilao);
        Licitante rafael = new Licitante("Rafael");
        Licitante pedro = new Licitante("Pedro");

        leiloeiro.subscribe(pedro);
        leiloeiro.subscribe(rafael);

        leiloeiro.avaliarLance(110, pedro);
        leiloeiro.avaliarLance(200, rafael);
        leiloeiro.avaliarLance(300, pedro);

        leiloeiro.finalizarLeilao();
    }
}