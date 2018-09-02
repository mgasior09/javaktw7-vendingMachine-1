package pl.sdacademy.vending;

import pl.sdacademy.vending.controller.CustomerOperationController;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.util.Configuration;
import pl.sdacademy.vending.util.PropertiesFileConfiguration;

public class Application {
    private final CustomerOperationController customerOperationController;

    public Application() {
        Configuration configuration = PropertiesFileConfiguration.getInstance();
        VendingMachine vendingMachine = new VendingMachine(configuration);
        customerOperationController = new CustomerOperationController(vendingMachine);
    }

    public void start() {
        customerOperationController.printMachine();
    }
}
