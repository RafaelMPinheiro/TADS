package apresentacao;

import negocio.*;

public class Main {
    public static void main(String[] args) {
        printMenu(new PancakeHouseMenuIterator());
        System.out.println("===");

        printMenu(new DinerMenuIterator());
        System.out.println("===");

        printMenu(new CafeMenuIterator());
        System.out.println("===");

        printMenu(new BarMenuIterator());
    }

    private static void printMenu(IteratorDoIgor<MenuItem> iterator) {
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}