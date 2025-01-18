package golpes;

public class Voadora extends Golpe {
  private int dano;

  public Voadora(int dano) {
    this.nome = "Voadora";
    this.dano = dano;
  }

  @Override
  public int getDano() {
    return dano;
  }

  public void socar() {
    System.out.println("Utilizou voadora");
  }
}
