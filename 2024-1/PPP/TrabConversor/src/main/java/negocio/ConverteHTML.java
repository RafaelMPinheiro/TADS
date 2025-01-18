package negocio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConverteHTML extends ConverteArquivo {

  public ConverteHTML(String caminhoArquivo, String caracterSeparador) {
    super(caminhoArquivo, caracterSeparador);
  }

  @Override
  public void escreverArquivo() {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(getNomeDoArquivo().toLowerCase() + ".html"));

      writer.write("<!DOCTYPE html>\n<html>\n<head>\n<title>" + getNomeDoArquivo() + "</title>\n</head>\n<body>\n");

      writer.write("<table>\n");
      for (String linha : getLinhas()) {
        writer.write("<tr>\n");
        String[] partes = linha.split(getCaracterSeparador());
        for (String parte : partes) {
          writer.write("<td>" + parte + "</td>\n");
        }
        writer.write("</tr>\n");
      }
      writer.write("</table>\n");

      writer.write("</body>\n</html>\n");

      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
