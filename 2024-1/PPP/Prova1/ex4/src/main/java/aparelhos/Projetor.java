package aparelhos;

public class Projetor {
  private String nome;

  public Projetor(String nome) {
    this.nome = nome;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public void ligar() {
    System.out.println("Ligando projetor");
  }

  public void desligar() {
    System.out.println("Desligando projetor");
  }
}
