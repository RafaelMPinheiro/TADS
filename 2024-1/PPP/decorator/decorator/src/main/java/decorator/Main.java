package decorator;

import negocio.*;


public class Main {
    public static void main(String[] args) {
        Beverage ex1 =  new Espresso();
        System.out.println(ex1.getDescription() + ":" + ex1.cost());
        System.out.println("=================");
        Beverage ex2 = new Espresso();
        ex2 = new Milk(ex2);
        ex2 = new Sugar(ex2);
        System.out.println(ex2.getDescription() + ":" + ex2.cost());
        System.out.println("===============");
        Beverage ex3 = new Decaf();
        System.out.println(ex3.getDescription() + ":" + ex3.cost());


    }
}