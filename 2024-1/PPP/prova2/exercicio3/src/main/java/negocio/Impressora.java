package negocio;

import java.util.ArrayList;

public class Impressora {
  private static Impressora uniqueInstance = new Impressora();

  private ArrayList<Documento> documentos;

  private Impressora() {
    this.documentos = new ArrayList<>();
  }

  public static synchronized Impressora getInstancia() {
    if (uniqueInstance == null) {
      uniqueInstance = new Impressora();
    }
    return uniqueInstance;
  }

  public void addDocumento(Documento d) {
    if (documentos.size() < 3) {
      documentos.add(d);
      System.out.println("Adicionado");
      return;
    }
    System.out.println("Não adicionado");
  }

  public ArrayList<Documento> getDocumentos() {
    return documentos;
  }

  public void imprimir() {
    if (documentos.size() > 0) {
      System.out.println("Imprimindo " + documentos.get(0).toString());
      return;
    }
    System.out.println("Não foi possivel imprimir");
  }
}
