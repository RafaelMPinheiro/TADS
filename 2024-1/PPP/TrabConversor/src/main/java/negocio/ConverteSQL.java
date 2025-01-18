package negocio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ConverteSQL extends ConverteArquivo {

  public ConverteSQL(String caminhoArquivo, String caracterSeparador) {
    super(caminhoArquivo, caracterSeparador);
  }

  @Override
  public void escreverArquivo() {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(getNomeDoArquivo().toLowerCase() + ".sql"));
      writer.write(createTable());
      writer.write(createInsert());
      writer.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  private String createTable() {
    String createTable = "CREATE TABLE " + getNomeDoArquivo() + " (\n";
    String[] colunas = getLinhas().get(0).split(getCaracterSeparador());
    for (int i = 0; i < colunas.length; i++) {
      createTable += "  " + colunas[i] + " VARCHAR(255)";
      if (i < colunas.length - 1) {
        createTable += ",\n";
      } else {
        createTable += "\n";
      }
    }
    createTable += ");\n";
    return createTable;
  }

  private String createInsert() {
    String createInsert = "INSERT INTO " + getNomeDoArquivo() + " (";
    String[] colunas = getLinhas().get(0).split(getCaracterSeparador());
    for (int i = 0; i < colunas.length; i++) {
      createInsert += colunas[i];
      if (i < colunas.length - 1) {
        createInsert += ", ";
      } else {
        createInsert += ")\nVALUES\n";
      }
    }

    for (int i = 1; i < getLinhas().size(); i++) {
      String[] values = getLinhas().get(i).split(getCaracterSeparador());
      createInsert += "  (";

      for (int j = 0; j < values.length; j++) {
        createInsert += "'" + values[j] + "'";
        if (j < values.length - 1) {
          createInsert += ", ";
        }
      }

      createInsert += ")";
      if (i < getLinhas().size() - 1) {
        createInsert += ",\n";
      } else {
        createInsert += ";\n";
      }
    }

    return createInsert;
  }
}
