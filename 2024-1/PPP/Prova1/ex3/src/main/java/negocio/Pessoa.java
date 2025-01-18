package negocio;

public class Pessoa implements Observer {
  private String nome;

  public Pessoa(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  @Override
  public void update(double valorAcao) {
    System.out.println(getNome() + " recebeu a notificação do novo preço: " + valorAcao);
  }
}
