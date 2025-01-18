package negocio;

public class Silenciador extends AcessorioDecorator {
  public Silenciador(Beverage beverage) {
    super(beverage);
    this.nome = "Silenciador";
    this.tag = "Acessório";
  }
}
