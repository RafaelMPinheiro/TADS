package golpes;

public class Chute extends Golpe {
  private int dano;

  public Chute(int dano) {
    this.nome = "Chute";
    this.dano = dano;
  }

  @Override
  public int getDano() {
    return dano;
  }

  public void chutar() {
    System.out.println("Utilizou chute");
  }
}