package apresentacao;

import negocio.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Garagem garagem = new Garagem("Garagem Central");

        Empregado manobrista = new Empregado("João", LocalDate.of(1999, 8, 12), true);
        Empregado secretaria = new Empregado("Maria", LocalDate.of(1985, 11, 30), true);
        Empregado secretaria2 = new Empregado("Luana", LocalDate.of(1990, 5, 1), false);
        Empregado estagiario = new Empregado("Pedro", LocalDate.of(2005, 4, 25), false);

        garagem.contratarFuncionario(manobrista);
        garagem.contratarFuncionario(secretaria);
        garagem.contratarFuncionario(secretaria2);
        garagem.contratarFuncionario(estagiario);

        CarroProxy carro = new CarroProxy(2010, "XYZ1234", "Civic", "Honda", "0987654321");

        carro.entrarGaragem(garagem);

        System.out.println("\nTentativa de manobra pelo estagiário " + estagiario.getNome() + ":");
        carro.manobrar(estagiario);

        System.out.println("\nTentativa de manobra pela secretária " + secretaria.getNome() + ":");
        carro.manobrar(secretaria);

        System.out.println("\nTentativa de manobra pela secretária " + secretaria2.getNome() + ":");
        carro.manobrar(secretaria2);

        System.out.println("\nTentativa de manobra pelo manobrista " + manobrista.getNome() + ":");
        carro.manobrar(manobrista);

        Empregado manobrista1 = new Empregado("Ana", LocalDate.of(1992, 6, 18), true);

        Garagem garagemColetiva = new Garagem("Garagem Coletiva");

        garagemColetiva.contratarFuncionario(manobrista1);

        System.out.println("\nTentativa de manobra pela nova manobrista " + manobrista1.getNome() + ":");
        carro.manobrar(manobrista1);
    }
}
