package pl.sdacademy.vending.util;

public class StringUtils {
    public static String adjustText(String textToMatch, Integer expectedTextLength) {
        if (textToMatch.length() < expectedTextLength) {
            int requiredSpaces = expectedTextLength - textToMatch.length();
            int spacesOnLeft = (requiredSpaces + 1) / 2;
            int spacesOnRight = requiredSpaces / 2;
            return multiplyText(" ", spacesOnLeft)
                    + textToMatch
                    + multiplyText(" ", spacesOnRight);
        } else {
            return textToMatch.substring(0, expectedTextLength);
        }
    }

    public static String multiplyText(String textToMultiply, Integer times) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer count = 0; count < times; count++) {
            stringBuilder = stringBuilder.append(textToMultiply);
        }
        return stringBuilder.toString();
    }

    public static String formatMoney(Long money) {
        return formatMoneyIntegrals(money) + "," + formatMoneyDecimals(money);
    }

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

    private static String formatMoneyDecimals(Long money) {
        String cents = Long.toString(money % 100);
        if (cents.length() == 1) {
            return "0" + cents;
        }
        return cents;
    }
}
