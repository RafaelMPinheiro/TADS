package apresentacao;

import negocio.*;

public class Main {
    public static void main(String[] args) {
        SistemaScanner sistemaScanner = new SistemaScanner();
        SistemaJOption sistemaJOption = new SistemaJOption();

        sistemaScanner.executarLogin();

        sistemaJOption.executarLogin();
    }
}