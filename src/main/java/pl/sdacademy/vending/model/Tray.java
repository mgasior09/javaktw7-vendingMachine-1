package pl.sdacademy.vending.model;

import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;

/**
 * Klasa reprezentująca tackę/podajnik. Przechowuje cenę produktu oraz swój symbol, a także kolejkę produktów.
 * Produkty są przechowywane w kolejce, ponieważ zawsze kupujemy ten produkt który jest na początku tacki (najbliżej
 * szyby/przedniej ściany automatu).
 *
 * Instancja tacki jest tworzona TYLKO za pomocą Buildera.
 */
public class Tray {
    private String symbol;
    private Long price;
    private Queue<Product> products;

    private Tray(Builder builder) {
        this.symbol = builder.symbol;
        this.price = builder.price;
        this.products = builder.products;
    }

    public static Builder builder(String symbol) {
        return new Builder(symbol);
    }

    public String getSymbol() {
        return symbol;
    }

    public Long getPrice() {
        return price;
    }

    /**
     * Pobiera nazwę pierwszego produkut, jeżeli ten jest dostępny.
     * @return nazwa pierwszego produktu lub pusty obiekt {@link Optional}
     */
    public Optional<String> firstProductName() {
        // metoda peek pobiera, ale nie usuwa z kolejki pierwszego elementu
        // chcemy tylko dowiedzieć się, jak się produkt nazywa. Przy takiej operacji nie musimy wyciągać produktu
        // z tacki
        Product firstProduct = products.peek();
        /*
        if (firstProduct == null) {
            return "EMPTY";
        } else {
            String firstProductName = firstProduct.getName();
            if (firstProductName == null) {
                return "EMPTY";
            } else {
                return firstProductName;
            }
        }

        Optional<Product> firstProduct1 = Optional.ofNullable(firstProduct);
        Optional<String> s = Optional.ofNullable(firstProduct)
                .map(Product::getName);
        Optional<String> s2 = Optional.ofNullable(firstProduct)
                .map((srodekPudelka) -> srodekPudelka.getName());
*/
        // pobrany produkt konwertujemy na optional. dzięki metodzie ofNullable nie musimy się przejmować potencjalnym
        // nullem (czyli brakiem produktu). Optional będzie w stanie wykonać "mapowanie" na obiektie null
        return Optional.ofNullable(firstProduct)
                .map(Product::getName);
    }

    /**
     * Jeżeli produkt jest dostęny, to metoda ta zwraca go opakowanego w {@link Optional}. W przeciwnym wypadku zwracany
     * jest pusty obiekt optional
     * @return
     */
    public Optional<Product> getFirstProduct() {
        // operacja poll pobiera wartość z kolejki oraz usuwa ją z tej kolejki (wszystkie elementy przesuwają się  do przodu)
        return Optional.ofNullable(products.poll());
    }

    public static class Builder {
        private String symbol;
        private Long price;
        private Queue<Product> products;

        private Builder(String symbol) {
            if (symbol == null) {
                throw new IllegalArgumentException("Tray symbol cannot be null");
            }
            this.symbol = symbol;
            this.products = new LinkedList();
        }

        public Builder price(Long price) {
            this.price = price;
            return this;
        }

        public Builder product(Product product) {
            this.products.add(product);
            return this;
        }

        public Tray build() {
            if (price == null) {
                price = 990L;
            }
            return new Tray(this);
        }
    }

}
