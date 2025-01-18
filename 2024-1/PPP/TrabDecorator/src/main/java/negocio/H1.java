package negocio;

public class H1 extends TagDecorator {
    public H1(Beverage beverage, String text) {
        super(beverage);
        this.content = "<h1>" + text + "</h1>";
    }
}