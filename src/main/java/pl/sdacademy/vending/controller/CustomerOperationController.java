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
                printUpperBoundaryForCell(row, col);
            }
            System.out.println();

            for (int col = 0; col < machine.colsSize(); col++) {
                printSymbolForCell(row, col);
            }
            System.out.println();

            for (int col = 0; col < machine.colsSize(); col++) {
                printLowerBoundaryForCell(row, col);
            }
            System.out.println();
        }
    }

    private void printUpperBoundaryForCell(int row, int col) {
        System.out.print("+--------+");
    }

    private void printSymbolForCell(int row, int col) {
        char rowSymbol = (char) ('A' + row);
        int colSymbol = col + 1;
        System.out.print("|   " + rowSymbol + colSymbol + "   |");
    }

    private void printLowerBoundaryForCell(int row, int col) {
        System.out.print("+--------+");
    }
}
