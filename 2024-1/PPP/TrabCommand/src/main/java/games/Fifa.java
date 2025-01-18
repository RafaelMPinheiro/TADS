package games;

public class Fifa {
  private String ano;

  public Fifa(String ano) {
    this.ano = ano;
  }

  public String getAno() {
    return ano;
  }

  public void setAno(String ano) {
    this.ano = ano;
  }

  public void chutar() {
    System.out.println("Chutar");
  }

  public void passe() {
    System.out.println("Passe");
  }
}
