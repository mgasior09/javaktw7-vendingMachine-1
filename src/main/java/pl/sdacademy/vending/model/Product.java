package pl.sdacademy.vending.model;

/**
 * Klasa reprezentująca produkt, który można kupić. Przechowuje nazwę produktu.
 */
public class Product {
    private final String name;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
