package pl.sdacademy.vending.controller;

import pl.sdacademy.vending.model.Product;
import pl.sdacademy.vending.model.Tray;
import pl.sdacademy.vending.model.VendingMachine;
import pl.sdacademy.vending.util.StringUtils;

import java.util.Optional;

/**
 * Klasa, będąca reprezentacją górnej warstwy architektury. Odpowiada za obsługę interakcji z użytkownikiem – obiera od
 * warstwy prezentacji żądania użytkownika i wywołuje określone informacje. W naszej aplikacji będzie odpowiedzialna za
 * dostarczanie widoku do użytkownika (wyświetlanie automatu) oraz w przyszłości za obsługę żądania zakupu czy też
 * wydania reszty.
 * <br>
 * Klasa ta operuje na obiekcie {@link VendingMachine}– aktualnie przekazywanym do niej jako zależność. Nie jest to poprawne
 * zachowanie, ponieważ VendingMachine jest klasą modelu i powinien na niej głównie operować Serwis – środkowa warstwa
 * z architektury trójwarstwowej. Na potrzeby kursu uprościliśmy nieco naszą architekturę, którą w czasie trwania
 * projektu będziemy modernizować.
 */
public class CustomerOperationController {

    /**
     * {@link VendingMachine} jest zależnością klasy {@link CustomerOperationController} - klasa ta wymaga instancji
     * VendingMachine do poprawnego działania. W aplikacji stosujemy podejście ustawiania zależności przez konstruktor.
     * Ułatwia to zarządzanie zależnościami oraz upraszcza testowanie modułów aplikacji. Konkretna instancja
     * VendingMachine do tej klasy zostanie przekazana przez klasę {@link pl.sdacademy.vending.Application}
     */
    private final VendingMachine machine;

    /**
     * Konstruktor, który jest używany przez {@link pl.sdacademy.vending.Application} do zdefiniowania, jaki {@link VendingMachine}
     * jest obsługiwany przez ten Controller. Przekazana instancja VendingMachine (inaczej mówiąc, obiekt reprezentujący
     * VendingMachine) będzie używana przez wszystkie metody z klasy.
     * <br>
     * W przypadku, gdyby Controller wymagał odczytania pewnych danych konfiguracyjnych z pliku properties, to zależność
     * do {@link pl.sdacademy.vending.util.Configuration} również wymuszałaby przekazanie instancji konfiguracji przez konstruktor.
     * @param machine obiekt reprezentujący konkretny automat sprzedający, na którym będzie operował ten kontroler.
     */
    public CustomerOperationController(VendingMachine machine) {
        this.machine = machine;
    }

    /**
     * Metoda, której zadaniem jest wyświetlenie automatu na ekran. Odczytuje bezpośrednio z instancji {@link VendingMachine}
     * zapisaną w polu {@link #machine} wielkość automatu - ilość wierszy, oraz ilość kolumn, w które można włożyć produkty.
     */
    public void printMachine() {
        // na początku przetwarzania iterujemy (przechodzimy) po wszystkich wierszach automatu. Jego wyświetlanie musi
        // zacząć się od góry, a każdy kolejny rząd podajników będzie skłądał się na kilka wydrukowanych linijek na ekranie
        for (int row = 0; row < machine.rowsSize(); row++) {
            // na sam początek musimy wydrukować górną krawędź każdej kolumny w tym wieszu. Dlatego też przechodzimy przez
            // wszystkie kolumny, jakie może posiadać automat sprzedający
            for (int col = 0; col < machine.colsSize(); col++) {
                // dla każdej z komórek/podajników/tacek wywołaj metodę {@link #printUpperBoundaryForCell(int, int)}. Wyświetli ona
                // górną krawędź komórki.
                printUpperBoundaryForCell(row, col);
            }
            // po wyświetleniu górnej krawędzi każdej z komórek przenosimy kursor tekstowy do nowej linijki za pomocą println
            System.out.println();

            // kolejna pętla, także przechodząca po wszystkich kolumnach W TYM SAMYM WIERSZU co poprzednia pętla - nadal
            // operujemy na tym samym wierszu automatu
            for (int col = 0; col < machine.colsSize(); col++) {
                // dla każdej z komórek wypisujemy na ekran jej symbol w odpowiednim formacie
                printSymbolForCell(row, col);
            }
            // po wypisaniu symboli każdej z komórek przenosimy kursor tekstowy do nowej linii
            System.out.println();

            for (int col = 0; col < machine.colsSize(); col++) {
                printProductNameForCell(row, col);
            }
            System.out.println();

            for (int col = 0; col < machine.colsSize(); col++) {
                printTrayPriceForCell(row, col);
            }
            System.out.println();

            // ostatnią pętlą operującą nadal na TYM SAMYM wierszu jest pętla wypisująca dolną krawędź komórki
            for (int col = 0; col < machine.colsSize(); col++) {
                // ponownie dla każdej komórki wypisujemy na ekran jej dolną krawędź
                printLowerBoundaryForCell(row, col);
            }
            // po wypisaniu dolnej krawędzi każdej z komórek przenosimy kursor do nowej linii
            System.out.println();
            // dopiero teraz pętla operujaca na wierszach może zacząć pracować na kolejnym wierszu.
        }
    }

    /**
     * Metoda mająca na celu wyświetlić górną krawędź zadanej komórki. Tak na prawdę, to zawsze wyświetlna ona stały tekst.
     * Parametry w tej metodzie są przekazywane tylko po to, aby zachować spójność parametrów w metodach pomocniczych.
     * Umożliwia to łatwe wprowadzenie w tym miejscu np. wzorca strategia/polityka, którego konkretne klasy odpowiadałby
     * za wyświetlanie kolejnych fragmentów komórki
     * @param row numer wiersza, z którego komórka powinna zostać wyświetlona. Aktualnie ignorowany.
     * @param col numer kolumny, z którego komórka powinna zostać wyświetlona. Aktualnie ignorowany.
     */
    private void printUpperBoundaryForCell(int row, int col) {
        String score = StringUtils.multiplyText("-", 12);
        System.out.print("+" + score + "+");
    }

    /**
     * Metoda mająca na celu wypisanie symbolu komórki. Zmienia ona numer wiersza na literę (korzystając ze znajomości
     * tablicy ASCII. Kolumna natomiast służy do wyliczenia wyświetlanego numeru - zmieniamy indeksowanie od zera na
     * indeksowanie kolumn od jedynki
     * @param row numer wiersza, z którego komórka powinna zostać wyświetlona
     * @param col numer kolumny, z którego komórka powinna zostać wyświetlona
     */
    private void printSymbolForCell(int row, int col) {
        Optional<Tray> tray = machine.trayDetailsAtPosition(row, col);
        String symbol =
                tray
                        .map(Tray::getSymbol)
                        .orElse("--");
        String centeredSymbol = StringUtils.adjustText(symbol, 12);
        System.out.print("|" + centeredSymbol + "|");
    }

    /**
     * Metoda wyświetlająca nazwę produktu przechowywanego na tacce. Używa metody z {@link VendingMachine} do pobrania nazwy
     * produktu. Jest ona zwracana jako {@link Optional}, z którego jest odczytywana ostateczna wartość: w przypadku, gdy
     * nazwa produktu była dostępna, to jest ona zwracana, natomiast jeżeli nazwa produktu nie istniała, to jest zwracana
     * domyślna wartość "--".
     * @param row numer wiersza, z którego komórka powinna zostać wyświetlona
     * @param col numer kolumny, z którego komórka powinna zostać wyświetlona
     */
    private void printProductNameForCell(int row, int col) {
        Optional<String> productName = machine.productNameAtPosition(row, col);
        String obtainedProductName = productName.orElse("--");
        String productNameToDisplay = StringUtils.adjustText(obtainedProductName, 12);
        System.out.print("|" + productNameToDisplay + "|");
    }

    /**
     * Metoda wyświetlająca cenę produktu. Tacka, na której jest nadrukowana cena, jest pobierana z automatu, jednak
     * ze względu na to, że nie wszystkie tacki muszą być zainstalowane, metoda ta zwraca obiekt {@link Optional}.
     * Tacka znajdująca się wewnątrz Optionala, musi zostać "przekonwertowana" na cenę - inaczej mówiąc, z obiektu, który
     * jest w środku Optionala, należy odczytać cenę. Do wykonania tej operacji służy metoda .map, która "mapuje" wartość
     * w śroku optionala za pomocą przekazanej metody, a jeżeli w środku nie było żadnej wartości, to nie jest wykonywana
     * żadna operacja.
     * Ostateczniem zwracana jest odczytana cena produktu lub wartość domyślna 0. Wartości te są konwertowane na tekst
     * oraz centrowane za pomocą metod z klasy {@link StringUtils}
     * @param row numer wiersza, z którego komórka powinna zostać wyświetlona
     * @param col numer kolumny, z którego komórka powinna zostać wyświetlona
     */
    private void printTrayPriceForCell(int row, int col) {
        Optional<Tray> tray = machine.trayDetailsAtPosition(row, col);
        Long priceForTray = tray
                .map((trayInOptional) -> trayInOptional.getPrice())
                .orElse(0L);
        String formattedMoney = StringUtils.formatMoney(priceForTray);
        String priceToDisplay = StringUtils.adjustText(formattedMoney, 12);
        System.out.print("|" + priceToDisplay + "|");
    }

    /**
     * Metoda wyświetlająca dolną krawędź komórki. Analogiczna do {@link #printUpperBoundaryForCell(int, int)}
     * @param row numer wiersza, z którego komórka powinna zostać wyświetlona. Aktualnie ignorowany.
     * @param col numer kolumny, z którego komórka powinna zostać wyświetlona. Aktualnie ignorowany.
     */
    private void printLowerBoundaryForCell(int row, int col) {
        System.out.print(
                "+" + StringUtils.multiplyText("-", 12) + "+"
        );
    }

    /**
     * Metoda umożliwiająca kupno produktu o danym symbolu (symbol tacki). Aktualnie, metoda ta nie bierze pod uwagę kosztu
     * zakupu. Jedyną rzeczą, którą robi, to przekazuje żądanie kupna do automatu sprzedającego.
     * @param symbol
     * @return
     */
    public Optional<Product> buyProduct(String symbol) {
        return machine.buyProductWithSymbol(symbol);
    }
}
