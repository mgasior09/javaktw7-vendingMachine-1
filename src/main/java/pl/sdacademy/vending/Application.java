package pl.sdacademy.vending;

import pl.sdacademy.vending.controller.CustomerOperationController;
import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.util.Configuration;
import pl.sdacademy.vending.util.PropertiesFileConfiguration;

import java.util.Scanner;

/**
 * Klasa, która zarządza zleżnościami w naszym projekcie, oraz udostępnia główną metodę startującą aplikację.
 */
public class Application {
    // pole do przechowywania głównego kontrolera
    private final CustomerOperationController customerOperationController;
    private final VendingMachine vendingMachine;

    /**
     * Kostruktor dba o ustawienie wszystkich wymaganych zależności w klasach projektu.
     */
    public Application() {
        // najpierw tworzymy klasy, które nie posiadają żadnych zależności - w tym przypadku konfigurację.
        Configuration configuration = PropertiesFileConfiguration.getInstance();
        // Mając konfigurację aplikacji, możemy utworzyć VendingMachine, który jej wymagał. Wcześniej nie było to możliwe,
        // ponieważ najepierw trzeba było stworzyć wymagany obiekt
        vendingMachine = new VendingMachine(configuration);
        // po utworzeniu VendingMachine, możemy przekazać go do konstruktora CustomerOperationController, tworząc tym samym
        // instancję głównego kontrolera. Zapisujemy tę instancję do pola w klasie.
        customerOperationController = new CustomerOperationController(vendingMachine);
    }

    /**
     * Metoda uruchamiająca Automat Sprzedający.
     */
    public void start() {
        int userSelection = -1;
        do {
            customerOperationController.printMachine();
            printMenu();
            userSelection = getUserInput();
            switch (userSelection) {
                case 1:
                    System.out.print("Select product: ");
                    String selectedSymbol = new Scanner(System.in).nextLine();
                    Product boughtProduct = customerOperationController.buyProduct(selectedSymbol);
                    String productName = "Sold out";
                    if (boughtProduct != null) {
                        productName = boughtProduct.getName();
                    }
                    System.out.println(productName);
                    break;
                case 9:
                    System.out.println("Bye");
                    break;
                default:
                    System.out.println("Invalid selection");
            }
            // pobierz wybór użytkownika i zapisz w zmiennej userSelection
            // jeżeli użytkownik wybrał 1 - to pobierz od niego symbol tacki
            //      i kup produkt
            // jeżeli użytkownik wybrał 9, to wyświetl pożegnanie
            // jeżeli użytkownik wybrał dowolną inną opcję (nieistniejącą)
            //      to wyświetl komunikat błednego wyboru
        } while (userSelection != 9);
    }

    private void printMenu() {
        System.out.println("1. Buy product");
        System.out.println("9. Exit");
    }

    private int getUserInput() {
        System.out.print("Your selection: ");
        String userText = new Scanner(System.in).nextLine();
        try {
            int userNumber = Integer.parseInt(userText);
            return userNumber;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
