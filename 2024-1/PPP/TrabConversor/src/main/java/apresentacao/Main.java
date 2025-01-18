package apresentacao;

import java.io.IOException;

import negocio.ConverteExcel;
import negocio.ConverteHTML;
import negocio.ConvertePdf;
import negocio.ConverteSQL;

public class Main {
    public static void main(String[] args) throws IOException {
        String caminhoArquivo = "/Users/rafaelpinheiro/Documents/Vault-obsidian/TADS-2024-1/PPP/trab3/dados.csv";

        ConvertePdf pdf = new ConvertePdf(caminhoArquivo, ";");
        pdf.converter();

        ConverteSQL sql = new ConverteSQL(caminhoArquivo, ";");
        sql.converter();

        ConverteExcel excel = new ConverteExcel(caminhoArquivo, ";");
        excel.converter();

        ConverteHTML html = new ConverteHTML(caminhoArquivo, ";");
        html.converter();
    }
}