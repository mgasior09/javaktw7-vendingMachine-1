package pl.sdacademy.vending.util;

/**
 * Klasa przechowuje statyczne metody pomocnicze, używane podczas formatowania tekstu.
 */
public class StringUtils {
    /**
     * Metoda dopasowuje wskazany tekst do oczekiwanej długości. Jeżeli tekst jest za długi, to ucinane są nadmiarowe znaki.
     * Jeżeli tekst był za krótki, to jest on centrowany za pomocą spacji - spacje są doklejane przed i po przekazanym tekście.
     * @param textToMatch
     * @param expectedTextLength
     * @return
     */
    public static String adjustText(String textToMatch, Integer expectedTextLength) {
        if (textToMatch.length() < expectedTextLength) {
            // jeżeli tekst jest za krótki, to doklejamy spacje. Wyliczamy potrzebną ilość spacji.
            int requiredSpaces = expectedTextLength - textToMatch.length();
            // korzystamy z zaokrąglania liczb całkowitych. Części dziesiętne z dzielenia są ucinane. np.:
            // * jeżeli required spaces jest = 4, to po dodaniu 1 otrzymujemy 5. 5 podzielone przez 2, po rzutowaniu na inta
            // daje wartość 2 - czyli nie ma to wpływu na ilość spacji.
            // * jeżeli required spaces jest = 5, to po dodaniu 1 otrzymujemy 6. 6 podzielone przez 2, po rzutowaniu na inta
            // daje wartość 3 - czyli dodatkowa spacje (bo 5 nie można równo podzielić) znajdzie się przed tekstem do wycentrowania
            int spacesOnLeft = (requiredSpaces + 1) / 2;
            int spacesOnRight = requiredSpaces / 2;
            return multiplyText(" ", spacesOnLeft)
                    + textToMatch
                    + multiplyText(" ", spacesOnRight);
        } else {
            return textToMatch.substring(0, expectedTextLength);
        }
    }

    /**
     * Pomocnicza metoda zwielokroniająca tekst za pomocą pętli.
     * @param textToMultiply
     * @param times
     * @return
     */
    public static String multiplyText(String textToMultiply, Integer times) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer count = 0; count < times; count++) {
            stringBuilder = stringBuilder.append(textToMultiply);
        }
        return stringBuilder.toString();
    }

    /**
     * Metoda formatująca liczbę jako pieniądze. Rozbija złożony problem na dwa mniejsze:
     * - formatowanie całych złotówek
     * - formatowanie groszy
     * @param money
     * @return
     */
    public static String formatMoney(Long money) {
        return formatMoneyIntegrals(money) + "," + formatMoneyDecimals(money);
    }

    /**
     * Metoda formatująca ilość złotówek. Na początku ucinamy grosze z kwoty za pomocą dzielenia przez 100 oraz rzutowania
     * na longa - ucina to część dziesiętną i otrzymujemy tylko całości (a więc złotówki).
     * Następnie stosujemy odwrotną pętlę, aby przejść po otrzymanej liczbie od końca i co trzy znaki wstawić spację.
     * @param money
     * @return
     */
    private static String formatMoneyIntegrals(Long money) {
        String integrals = Long.toString(money / 100);
        StringBuilder formattedIntegralsBuilder = new StringBuilder();
        int spaceCounter = 0;
        for (int charIndex = integrals.length() - 1; charIndex >= 0; charIndex--) {
            formattedIntegralsBuilder = formattedIntegralsBuilder.append(integrals.charAt(charIndex));
            spaceCounter++;
            if (spaceCounter >= 3) {
                formattedIntegralsBuilder = formattedIntegralsBuilder.append(" ");
                spaceCounter = 0;
            }
        }
        return formattedIntegralsBuilder.reverse().toString().trim();
    }

    /**
     * Proste ucięcie wszystkich złotówek za pomocą operacji modulo.
     * Jeżeli ilość otrzymanych groszy jest mniejsza niż 10, to trzeba jeszcze dokleić zero, aby otrzymać 2 cyfry, np:
     * 1205 groszy (modulo) 100 = 5; do 5 doklejamy 0 i otrzymujemy 05, czyli poprawnie sformatowaną część groszy
     * @param money
     * @return
     */
    private static String formatMoneyDecimals(Long money) {
        String cents = Long.toString(money % 100);
        if (cents.length() == 1) {
            return "0" + cents;
        }
        return cents;
    }
}
