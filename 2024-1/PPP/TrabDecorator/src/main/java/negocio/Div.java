package negocio;

public class Div extends TagDecorator {
    public Div(Beverage beverage, Beverage divBeverage) {
        super(beverage);
        this.content = "<div style=\"border: 2px solid #000\">" + divBeverage.getContent() + "\n</div>";
    }
}