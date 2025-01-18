package negocio;

public class Licitante implements Observer {
  private String nome;

  public Licitante(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  @Override
  public void update(double valorLance, String nomeLicitante) {
    System.out.println(this.nome + ": " + nomeLicitante + " deu um lance de R$" + valorLance + " no leilão.");
  }
}
