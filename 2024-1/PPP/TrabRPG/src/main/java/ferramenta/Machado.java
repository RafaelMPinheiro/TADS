package ferramenta;

public class Machado extends Arma{
  public Machado() {
    this.nome = "Machado";
    this.disponivel = true;
  }

  @Override
  public int getDano(){
    return 10;
  }
}
