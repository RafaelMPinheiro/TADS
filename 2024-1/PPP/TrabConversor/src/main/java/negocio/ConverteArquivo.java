package negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;

abstract class ConverteArquivo {
  private String caminhoArquivo;
  private String nomeDoArquivo;
  private String caracterSeparador;
  private ArrayList<String> linhas;

  public ConverteArquivo(String caminhoArquivo, String caracterSeparador) {
    this.caminhoArquivo = caminhoArquivo;
    this.nomeDoArquivo = caminhoArquivo.substring(caminhoArquivo.lastIndexOf("/") + 1, caminhoArquivo.lastIndexOf("."));
    this.caracterSeparador = caracterSeparador;
    this.linhas = new ArrayList<String>();
  }

  public String getCaminhoArquivo() {
    return caminhoArquivo;
  }

  public String getNomeDoArquivo() {
    return nomeDoArquivo;
  }

  public String getCaracterSeparador() {
    return caracterSeparador;
  }

  public ArrayList<String> getLinhas() {
    return linhas;
  }

  public void printLinhas() {
    for (String linha : getLinhas())
      System.out.println(linha);
  }

  public void converter() {
    lerArquivo(getCaminhoArquivo());
    escreverArquivo();
  };

  protected void lerArquivo(String caminhoArquivo) {
    try {
      File file = new File(caminhoArquivo);
      BufferedReader br = new BufferedReader(new FileReader(file));

      String line;
      while ((line = br.readLine()) != null) {
        getLinhas().add(line);
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  abstract void escreverArquivo();

}
