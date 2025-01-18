package negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Leilao implements Observer {
  private String nomeProduto;
  private double valorMinimo;
  private double valorAtual;
  private String nomeMaiorLance;

  private ArrayList<String> historico;

  public Leilao(String nomeProduto, double valorMinimo) {
    this.nomeProduto = nomeProduto;
    this.valorMinimo = valorMinimo;
    this.historico = new ArrayList<>();
  }

  public String getNomeProduto() {
    return nomeProduto;
  }

  public double getValorMinimo() {
    return valorMinimo;
  }

  public double getValorAtual() {
    return valorAtual;
  }

  public String getNomeMaiorLance() {
    return nomeMaiorLance;
  }

  protected void getHistorico() {
    System.out.println("Histórico de lances do leilão do produto " + this.nomeProduto + ":");
    for (int i = 0; i < this.historico.size(); i++) {
      int numeroLance = i + 1;
      String lanceAtual = this.historico.get(i);
      System.out.println("Lance " + numeroLance + ": " + lanceAtual);
    }
  }

  @Override
  public void update(double valorLance, String nomeLicitante) {
    this.valorAtual = valorLance;
    this.nomeMaiorLance = nomeLicitante;
    this.historico.add(nomeLicitante + " no valor de R$" + valorLance + " às " + LocalDateTime.now());
  }
}
