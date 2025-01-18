package negocio;

public class Sugar extends CondimentDecorator {

    public Sugar(Beverage beverage) {
        super(beverage);
        this.cost = 2;
        this.description = "Sugar";
    }
}