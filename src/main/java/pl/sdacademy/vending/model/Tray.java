package pl.sdacademy.vending.model;

import java.util.LinkedList;
import java.util.Queue;

public class Tray {
    private String symbol;
    private Long price;
    private Queue<Product> products;

    public Tray(String symbol) {
        this.symbol = symbol;
        products = new LinkedList<>();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
