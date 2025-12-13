package apresentacao;

import negocio.Pedido;

public class Main {
    public static void main(String[] args) {
        Pedido pedido1 = new Pedido();
        pedido1.setCodigo("123");
        pedido1.setCliente("Cliente 1");
        pedido1.setValor(14.50);
        pedido1.setStatus("Concluido");

        Pedido pedido2 = new Pedido();
        pedido1.setCodigo("123");
        pedido1.setCliente("Cliente 2");
        pedido1.setValor(15.0);
        pedido1.setStatus("Em analise");

        System.out.println(pedido1.adicionarPedido());
        System.out.println(pedido2.adicionarPedido());
    }
}