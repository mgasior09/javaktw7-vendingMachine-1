package pl.sdacademy.vending.model;

import pl.sdacademy.vending.util.Configuration;

public class VendingMachine {

    private final Configuration configuration;

    public VendingMachine() {
        configuration = new Configuration();
    }

    public Long rowsSize() {
        return configuration.getProperty("machine.size.rows", 6L);
    }

    public Long colsSize() {
        return configuration.getProperty("machine.size.cols", 4L);
    }
}
