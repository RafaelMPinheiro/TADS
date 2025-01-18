package negocio;

public class Soy extends CondimentDecorator {

    public Soy(Beverage beverage) {
        super(beverage);
        this.cost = 2;
        this.description = "Soy";
    }
    
}
