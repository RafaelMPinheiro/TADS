package negocio;

public abstract class TagDecorator extends Beverage {
    private Beverage beverage;

    public TagDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getTitle() {
        return this.beverage.getTitle();
    }

    public String getContent() {
        return this.beverage.getContent() + "\n" + this.content;
    }
}
