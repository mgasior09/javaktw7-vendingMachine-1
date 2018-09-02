package pl.sdacademy.vending;

import pl.sdacademy.vending.controller.CustomerOperationController;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.util.Configuration;
import pl.sdacademy.vending.util.PropertiesFileConfiguration;

/**
 * Klasa, która zarządza zleżnościami w naszym projekcie, oraz udostępnia główną metodę startującą aplikację.
 */
public class Application {
    // pole do przechowywania głównego kontrolera
    private final CustomerOperationController customerOperationController;

    /**
     * Kostruktor dba o ustawienie wszystkich wymaganych zależności w klasach projektu.
     */
    public Application() {
        // najpierw tworzymy klasy, które nie posiadają żadnych zależności - w tym przypadku konfigurację.
        Configuration configuration = PropertiesFileConfiguration.getInstance();
        // Mając konfigurację aplikacji, możemy utworzyć VendingMachine, który jej wymagał. Wcześniej nie było to możliwe,
        // ponieważ najepierw trzeba było stworzyć wymagany obiekt
        VendingMachine vendingMachine = new VendingMachine(configuration);
        // po utworzeniu VendingMachine, możemy przekazać go do konstruktora CustomerOperationController, tworząc tym samym
        // instancję głównego kontrolera. Zapisujemy tę instancję do pola w klasie.
        customerOperationController = new CustomerOperationController(vendingMachine);
    }

    /**
     * Metoda uruchamiająca Automat Sprzedający.
     */
    public void start() {
        customerOperationController.printMachine();
    }
}
