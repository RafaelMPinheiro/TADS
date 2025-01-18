package apresentacao;

import negocio.Associado;

public class Main {
    public static void main(String[] args) {
        Associado rafael = Associado.builder()
                .nome("Rafael")
                .cpf("123.456.789-01")
                .senha("senha")
                .email("rafael@gmail.com")
                .build();
        System.out.println(rafael.toString());
    }
}