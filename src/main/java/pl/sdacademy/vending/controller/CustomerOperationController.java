package pl.sdacademy.vending.controller;

import pl.sdacademy.vending.model.VendingMachine;

public class CustomerOperationController {
    private VendingMachine machine;

    public CustomerOperationController() {
        machine = new VendingMachine();
    }

    public void printMachine() {
        for (int row = 0; row < machine.rowsSize(); row++) {
            for (int col = 0; col < machine.colsSize(); col++) {
                System.out.print("+--------+");
            }
            System.out.println();

            for (int col = 0; col < machine.colsSize(); col++) {
                char rowSymbol = (char) ('A' + row);
                int colSymbol = col + 1;
                System.out.print("|   " + rowSymbol + colSymbol + "   |");
            }
            System.out.println();

            for (int col = 0; col < machine.colsSize(); col++) {
                System.out.print("+--------+");
            }
            System.out.println();
        }
        /*
        dla każdego wiersza wyświetlić:
            dla każdej z kolumn, wyświetl:
                - górna część (+---+)
                    +---++---++---++---+
            dla każdej z kolumn, wyświetl:
                - boki oraz numer
                    | A1|| A2|| A3|| A4|
            dla każdej z kolumn, wyświetl:
                - dolną część (+---+)
                    +---++---++---++---+
         */
    }
}
