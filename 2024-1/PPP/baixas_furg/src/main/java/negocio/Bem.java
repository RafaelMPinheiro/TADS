package negocio;

public class Bem {
  private String patrimonio;
  private String descricao = "";

  public Bem(String patrimonio, String descricao) {
    this.patrimonio = patrimonio;
    this.descricao = descricao;
  }

  public String getPatrimonio() {
    return patrimonio;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setPatrimonio(String patrimonio) {
    this.patrimonio = patrimonio;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }
}
