package pl.sdacademy.vending.util;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class StringUtilsTest {

    @Test
    public void shouldNotModifyMatchingText() {
        // given
        String textToMatch = "Pawel";
        Integer expectedTextLength = 5;

        // when
        String adjustedText = StringUtils.adjustText(textToMatch, expectedTextLength);

        // then
        assertEquals("Pawel", adjustedText);
    }

    @Test
    public void shouldTrimTextThatIsTooLong() {
        // given
        String textToAdjust = "Ala ma kota";
        Integer expectedTextLength = 8;

        // when
        String adjustedText = StringUtils.adjustText(textToAdjust, expectedTextLength);

        // then
        assertEquals("Ala ma k", adjustedText);
    }

    @Test
    public void shouldCenterTextWhenSpacesAreEqual() {
        // given
        String textToAdjust = "Ala";
        Integer expectedTextLength = 7;

        // when
        String adjustedText = StringUtils.adjustText(textToAdjust, expectedTextLength);

        // then
        assertEquals("  Ala  ", adjustedText);
    }

    @Test
    public void shouldCenterTextWhenSpacesAreNotEqual() {
        // given
        String textToAdjust = "Ala";
        Integer expectedTextLength = 8;

        // when
        String adjustedText = StringUtils.adjustText(textToAdjust, expectedTextLength);

        // then
        assertEquals("   Ala  ", adjustedText);
    }

    @Test
    @Parameters
    public void shouldPropertyFormatNumberToCash(Long money, String expectedFormatOfMoney) {
        String formattedMoney = StringUtils.formatMoney(money);

        assertEquals(expectedFormatOfMoney, formattedMoney);
    }

    public Object[] parametersForShouldPropertyFormatNumberToCash() {
        return new Object[] {
                new Object[] {123L, "1,23"},
                new Object[] {123456L, "1 234,56"},
                new Object[] {1234567L, "12 345,67"},
                new Object[] {12345678L, "123 456,78"},
                new Object[] {1234567890123456789L, "12 345 678 901 234 567,89"},
                new Object[] {12L, "0,12"},
                new Object[] {3L, "0,03"},
                new Object[] {0L, "0,00"}
        };
    }
}