package golpes;

public class Soco extends Golpe {
  private int dano;

  public Soco(int dano) {
    this.nome = "Soco";
    this.dano = dano;
  }

  @Override
  public int getDano() {
    return dano;
  }

  public void socar() {
    System.out.println("Utilizou soco");
  }
}
