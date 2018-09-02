package pl.sdacademy.vending.model;

import pl.sdacademy.vending.util.Configuration;
import pl.sdacademy.vending.util.PropertiesFileConfiguration;

public class VendingMachine {

    private final Configuration configuration;

    public VendingMachine(Configuration configuration) {
        this.configuration = configuration;
    }

    public Long rowsSize() {
        return configuration.getProperty("machine.size.rows", 6L);
    }

    public Long colsSize() {
        return configuration.getProperty("machine.size.cols", 4L);
    }
}
