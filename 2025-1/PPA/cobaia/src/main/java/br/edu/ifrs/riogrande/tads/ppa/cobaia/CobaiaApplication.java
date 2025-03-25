package br.edu.ifrs.riogrande.tads.ppa.cobaia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class CobaiaApplication implements CommandLineRunner {

	@Autowired
	@Qualifier("payPal")
	private Pagamento pgto;

	public static void main(String[] args) {
		SpringApplication.run(CobaiaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Inicializando a aplicação...");

		pgto.pagar("Notebook", 5000.0, "123.456.789-00");
	}

}

interface Pagamento {
	void pagar(String produto, double preco, String cpf);
}

// @Configuration
// class PagamentoConfig {
// @Bean
// // @Scope("singleton")
// @Primary
// Pagamento payPal() {
// return new PayPal();
// }

// @Bean
// Pagamento mercadoPago() {
// return new MercadoPago();
// }
// }

@Component("payPal")
@Primary
class PayPal implements Pagamento {
	@Override
	public void pagar(String produto, double preco, String cpf) {
		System.out.println("Pagamento via PayPal\n" + produto + " " + preco + " " + cpf);
	}
}

@Component("mercadoPago")
class MercadoPago implements Pagamento {
	@Override
	public void pagar(String produto, double preco, String cpf) {
		System.out.println("Pagamento via Mercado Pago\n" + produto + " " + preco + " " + cpf);
	}
}