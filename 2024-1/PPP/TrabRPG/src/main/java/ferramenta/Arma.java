package ferramenta;

public abstract class Arma {
  public String nome;
  public boolean disponivel;

  public int getDano() {
    return 0;
  }

  public String getNome() {
    return nome;
  }

  public void setDisponivel(boolean disponivel) {
    this.disponivel = disponivel;
  }

  public boolean isDisponivel() {
    return disponivel;
  }
}
