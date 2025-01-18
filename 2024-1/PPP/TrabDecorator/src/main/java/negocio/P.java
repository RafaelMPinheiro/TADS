package negocio;

public class P extends TagDecorator {
    public P(Beverage beverage, String text) {
        super(beverage);
        this.content = "<p>" + text + "</p>";
    }
}