package negocio;

public abstract class AcessorioDecorator extends Beverage {
    private Beverage beverage;

    public AcessorioDecorator(Beverage beverage) {
        this.beverage = beverage;
    }

    public String getNome() {
        return this.beverage.getNome() + "\n" + this.nome;
    }

    public String getTag() {
        return this.beverage.getTag() + "\n" + this.tag;
    }

    public String getDescription() {
        return this.beverage.getDescription() +
                "\nItem: " + this.nome
                + " - " + this.tag;
    }
}
