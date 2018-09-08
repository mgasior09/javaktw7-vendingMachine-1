package pl.sdacademy.vending.model;

import pl.sdacademy.vending.util.Configuration;

import java.util.Optional;
import java.util.Random;

/**
 * Główna klasa automatu przechowująca jego stan oraz zachowania. Aktualnie jest bardzo "uboga" w zachowania, umożliwia
 * jedynie pobranie wielkości automatu, jednak z czasem będzie zawierała co raz więcej metod. Klasa {@link VendingMachine}
 * jest konfigurowana za pomocą dowolnej instancji interface {@link Configuration} - aktualnie jedyną dostępną implementacją
 * jest {@link pl.sdacademy.vending.util.PropertiesFileConfiguration}, jednak klasa VendingMachine o tym nie wie. Jedyne, co
 * interesuje klasę VendingMachine to to, aby otrzymać jakąś konfigurację. To, jaka będzie jej implementacja, jest jej
 * zupełnie obojętne. Ważne, aby udostępniała metody {@link Configuration#getProperty(String, Long)}
 */
public class VendingMachine {

    /**
     * Używana konfiguracja jest zapisana na stałe w polu w klasie. Umożliwia to wykorzystanie klasy w późniejszym
     * czasie przez inne metody klasy {@link VendingMachine}. {@link Configuration} jest wymaganą przez VendingMachine
     * ZALEŻNOśCIĄ, więc zostanie przekazana do niej za pomocą konstruktora przez klasę {@link pl.sdacademy.vending.Application}
     */
    private final Long maxRowsSize;
    private final Long maxColsSize;
    private final Tray[][] trays;

    /**
     * Konstruktor, który umożliwia przekazanie używanej klasy konfiguracji. Podczas normalnego działania aplikacji będzie
     * to jedna z implemetacji interface {@link Configuration}, a podczas testów jednostkowych może to być zmockowany obiekt
     * @param configuration obiekt zawierający używaną konfigurację.
     */
    public VendingMachine(Configuration configuration) {
        // tekst "machine.size.rows" jest kluczem, pod którym powinna być zapisana maksymalna ilość wierszy tego automatu
        maxRowsSize = configuration.getProperty("machine.size.rows", 6L);
        if (maxRowsSize < 1 || maxRowsSize > 26) {
            throw new IllegalArgumentException("VendingMachine can not be created with " + maxRowsSize + " rows");
        }
        maxColsSize = configuration.getProperty("machine.size.cols", 4L);
        if (maxColsSize < 1 || maxColsSize > 9) {
            throw new IllegalArgumentException("VendingMachine can not be created with " + maxColsSize + " cols");
        }
        trays = new Tray[maxRowsSize.intValue()][maxColsSize.intValue()];

        for (int rowNumber = 0; rowNumber < maxRowsSize; rowNumber++) {
            for (int colNumber = 0; colNumber < maxColsSize; colNumber++) {
                trays[rowNumber][colNumber] =
                        createTrayForPosition(rowNumber, colNumber);
            }
        }
    }

    private Tray createTrayForPosition(int rowNumber, int colNumber) {
        if (!shouldGenerateTray()) {
            return null;
        }

        char rowSymbol = (char) ('A' + rowNumber);
        int colSymbol = colNumber + 1;
        String symbol = "" + rowSymbol + colSymbol;
        int calculatedPrice = generateRandomPrice();

        double productProbability = Math.random();


        if (productProbability < 0.1) {
            // 2 produkty
            return Tray.builder(symbol).price((long) calculatedPrice)
                    .product(new Product("Product " + symbol))
                    .product(new Product("Product " + symbol))
                    .build();
        } else if (productProbability < 0.5) {
            // 1 product
            return Tray.builder(symbol).price((long) calculatedPrice)
                    .product(new Product("Product " + symbol))
                    .build();
        }

        return Tray.builder(symbol)
                .price(Long.valueOf(calculatedPrice))
                .build();
    }

    private boolean shouldGenerateTray() {
        // true jeżeli tack powinna zostać wygenerowana
        return Math.random() < 0.8;
    }

    private int generateRandomPrice() {
        Random random = new Random();
//        random.nextInt(100) -> 0 - 99
        int generatedPrice = random.nextInt(401); // values from 0 to 400

        /*
        metoda PRICE oczekuje OBIEKTU Long
        calcalutedPrice jest int
        int -(A)-> long -(B)-> Long
        Scenariusz 1: price((long) calculatedPrice)
            A) (long) intValue
            B) JVM sam rzutuje long na Long
        Scenariusz 2: price(Long.valueOf(calculatedPrice))
            A) JVM sam rzutuje int na long
            B) sami dbamy o przekonwetowanie long na Long
         */
        /*
        Inny sposób na generowanie losowej liczby
        double Math.random()
            double -> 0.0 do 1.0
            (double * 400) -> 0.0 do 400.0
            () + 100 -> 100.0 do 500.0
         */
        return generatedPrice + 100;
    }

    /**
     * Metoda umożliwiająca pobranie ilości wierszy automatu z obiektu konfiguracji. Sposób pobierania wartości parametry
     * zależy od implemetacji interface Configuration.
     * @return ilość wieszy w automacie pobrana z konfiguracji lub domyślna wartość 6.
     */
    public Long rowsSize() {
        return maxRowsSize;
    }


    /**
     * Metoda umożliwiająca pobranie ilości kolumn automatu z obiektu konfiguracji. Sposób pobierania wartości parametry
     * zależy od implemetacji interface Configuration.
     * @return ilość wieszy w automacie pobrana z konfiguracji lub domyślna wartość 4.
     */
    public Long colsSize() {
        // pod kluczem "machine.size.cols" jest zapisana maksymalna ilość kolumn automatu.
        return maxColsSize;
    }

    public Optional<Tray> trayDetailsAtPosition(int rowNumber, int colNumber) {
        Tray obtainedTray = trays[rowNumber][colNumber];
        Optional<Tray> tray = Optional.ofNullable(obtainedTray);
        return tray;
    }
}
