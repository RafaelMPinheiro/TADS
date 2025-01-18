package apresentacao;

import negocio.Documento;
import negocio.Impressora;

public class Main {
    public static void main(String[] args) {
        Impressora impressora = Impressora.getInstancia();

        Documento documento1 = new Documento("arquivo1", ".txt", 1);

        impressora.addDocumento(documento1);

        impressora.imprimir();
    }
}