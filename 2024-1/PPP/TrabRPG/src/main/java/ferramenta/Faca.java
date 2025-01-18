package ferramenta;

public class Faca extends Arma {
  public Faca(){
    this.nome = "Faca";
    this.disponivel = true;
  }

  @Override
  public int getDano() {
    return 5;
  }
}
