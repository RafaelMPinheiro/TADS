package games;

public class NeedforSpeed {
  private String ano;

  public NeedforSpeed(String ano) {
    this.ano = ano;
  }

  public String getAno() {
    return ano;
  }

  public void setAno(String ano) {
    this.ano = ano;
  }

  public void acelerar() {
    System.out.println("Acelerar");
  }

  public void freiar() {
    System.out.println("Freiar");
  }
}
