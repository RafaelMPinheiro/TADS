package negocio;

public class Milk extends CondimentDecorator {

    public Milk(Beverage beverage) {
        super(beverage);
        this.cost = 1;
        this.description = "Milk";
    }
    
}
