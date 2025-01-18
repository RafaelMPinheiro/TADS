package games;

public class StreetFighter {
  private int versao;

  public StreetFighter(int versao) {
    this.versao = versao;
  }

  public int getVersao() {
    return versao;
  }

  public void setVersao(int versao) {
    this.versao = versao;
  }

  public void soco() {
    System.out.println("Soco");
  }

  public void chute() {
    System.out.println("Chute");
  }
}
