package negocio;

public abstract class CondimentDecorator extends Beverage  {

    private Beverage beverage;

    public CondimentDecorator(Beverage beverage){
        this.beverage = beverage;
    }

    public String getDescription(){
        return this.beverage.getDescription()+"\n"+this.description;
    }

    public double cost(){
        return this.beverage.cost()+this.cost;
    }



}
