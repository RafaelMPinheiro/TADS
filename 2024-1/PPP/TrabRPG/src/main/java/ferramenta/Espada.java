package ferramenta;

public class Espada extends Arma {
  public Espada() {
    this.nome = "Espada";
    this.disponivel = true;
  }

  @Override
  public int getDano() {
    return 7;
  }
}
