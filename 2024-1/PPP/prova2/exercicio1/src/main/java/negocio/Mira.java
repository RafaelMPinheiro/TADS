package negocio;

public class Mira extends AcessorioDecorator {
  public Mira(Beverage beverage) {
    super(beverage);
    this.nome = "Mira";
    this.tag = "Acessório";
  }
}
