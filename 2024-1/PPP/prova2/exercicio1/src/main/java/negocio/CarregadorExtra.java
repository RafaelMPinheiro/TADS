package negocio;

public class CarregadorExtra extends AcessorioDecorator {
  public CarregadorExtra(Beverage beverage) {
    super(beverage);
    this.nome = "CarregadorExtra";
    this.tag = "Acessório";
  }
}
