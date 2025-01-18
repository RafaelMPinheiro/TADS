package negocio;

public class Bolo {
  private String sabor;
  private String ingredientes;

  public Bolo() {
  }

  public Bolo(String sabor, String ingredientes) {
    this.sabor = sabor;
    this.ingredientes = ingredientes;
  }

  public static Bolo cloneDoBolo() {
    Bolo novoBolo = new Bolo();
    novoBolo.setSabor("Chocolate");
    novoBolo.setIngredientes("Confete, bombom");
    return novoBolo;
  }

  public String getSabor() {
    return sabor;
  }

  public void setSabor(String sabor) {
    this.sabor = sabor;
  }

  public String getIngredientes() {
    return ingredientes;
  }

  public void setIngredientes(String ingredientes) {
    this.ingredientes = ingredientes;
  }

  @Override
  public String toString() {
    return "Bolo [sabor=" + sabor + ", ingredientes=" + ingredientes + "]";
  }
}
