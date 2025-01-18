package negocio;

public abstract class Beverage {
    protected String nome;
    protected String tag;

    public String getNome() {
        return this.nome;
    }

    public String getTag() {
        return this.tag;
    }

    public String getDescription() {
        return "Item: " + this.nome
                + " - " + this.tag;
    }
}
