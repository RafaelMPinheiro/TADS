package negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Date;

public class GeradorBaixas {
  private String caminhoArquivo;
  private String nomeDoArquivo;
  private String caracterSeparador;
  private ArrayList<String> linhas;
  ArrayList<Bem> bens;

  public GeradorBaixas(String caminhoArquivo, String caracterSeparador) {
    this.caminhoArquivo = caminhoArquivo;
    this.nomeDoArquivo = caminhoArquivo.substring(caminhoArquivo.lastIndexOf("/") + 1, caminhoArquivo.lastIndexOf("."));
    this.caracterSeparador = caracterSeparador;
    this.linhas = new ArrayList<String>();
    this.bens = new ArrayList<Bem>();
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

  public ArrayList<Bem> getBens() {
    return bens;
  }

  public void printBens() {
    for (Bem bem : bens)
      System.out.println(bem.getPatrimonio() + " - " + bem.getDescricao());
  }

  public void gerarArquivo() {
    System.out.println("Lendo o arquivo " + getNomeDoArquivo() + "...");
    lerArquivo();
    System.out.println("Linhas lidas:");
    printLinhas();
    System.out.println("Gerando o arquivo baixas.xlsx...");
    escreverArquivo();
  }

  public void lerArquivo() {
    try {
      File file = new File(getCaminhoArquivo());
      BufferedReader br = new BufferedReader(new FileReader(file));

      String line;
      while ((line = br.readLine()) != null) {
        this.linhas.add(line.split(getCaracterSeparador())[0]);
      }

      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void escreverArquivo() {
    for (int i = 1; i < getLinhas().size(); i++) {
      Bem bem = GETHttp(getLinhas().get(i));
      if (bem != null)
        this.bens.add(bem);
    }

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Date date = new Date();
    String caminhoArquivo = "baixas.xlsx";
    String caminhoArquivoSaida = "baixas_" + this.getLinhas().get(0) + "_" + dateFormat.format(date) + ".xlsx";

    try {
      FileInputStream arquivoEntrada = new FileInputStream(caminhoArquivo);
      XSSFWorkbook workbook = new XSSFWorkbook(arquivoEntrada);
      Sheet sheet = workbook.getSheetAt(0);

      Row row = sheet.getRow(5);
      Cell cell = row.getCell(0);
      cell.setCellValue("Data de Revisão da Listagem: " + dateFormat.format(date));

      row = sheet.getRow(6);
      cell = row.getCell(0);
      cell.setCellValue(getLinhas().get(0));

      int rowNum = 7;
      for (int i = 0; i < getBens().size(); i++) {
        row = sheet.createRow(++rowNum);
        cell = row.createCell(0);
        cell.setCellValue(i + 1);

        cell = row.createCell(1);
        cell.setCellValue(bens.get(i).getPatrimonio());

        cell = row.createCell(2);
        cell.setCellValue("Não");

        cell = row.createCell(3);
        cell.setCellValue("Antieconômico");

        cell = row.createCell(4);
        cell.setCellValue(bens.get(i).getDescricao());
      }
      FileOutputStream arquivoSaida = new FileOutputStream(caminhoArquivoSaida);
      workbook.write(arquivoSaida);

      arquivoEntrada.close();
      arquivoSaida.close();

      workbook.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  protected static Bem GETHttp(String patrimonio) {
    try {
      String url = "https://api.furg.br/patrimonio/Publico/consultaBens";
      HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");
      conn.setRequestProperty("Content-Type", "application/json");

      conn.setDoOutput(true);

      String parametros = "{\"busca_bem\": \"" + patrimonio + "\", \"limit\": 1}";
      OutputStream os = conn.getOutputStream();
      os.write(parametros.getBytes());
      os.flush();

      if (conn.getResponseCode() != 200) {
        System.out.println("Erro " + conn.getResponseCode() + " ao obter dados da URL " + url);
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      String output = "";
      String line;
      while ((line = br.readLine()) != null) {
        output += line;
      }

      conn.disconnect();

      Gson gson = new Gson();
      JsonObject json = gson.fromJson(output.toString(), JsonObject.class);
      JsonObject res = json.getAsJsonObject("res");
      JsonObject bens = res.getAsJsonArray("bens").get(0).getAsJsonObject();

      return new Bem(bens.get("tombamento").getAsString(), bens.get("descricao").getAsString());
    } catch (IOException e) {
      return null;
    }
  }
}
