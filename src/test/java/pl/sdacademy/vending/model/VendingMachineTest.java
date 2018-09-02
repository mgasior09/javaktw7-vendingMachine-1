package pl.sdacademy.vending.model;

import org.junit.Test;
import pl.sdacademy.vending.util.Configuration;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VendingMachineTest {
    // nazwa parametru ilości rzędów
    private static final String PARAM_NAME_COLS = "machine.size.cols";
    // nazwa parametru ilości kolumn
    private static final String PARAM_NAME_ROWS = "machine.size.rows";

    @Test
    public void shouldBeAbleToCreateMachineWithProperSize() {
        // given
        Configuration mockedConfig = mock(Configuration.class);
        when(mockedConfig.getProperty(eq(PARAM_NAME_COLS), anyLong()))
                .thenReturn(8L);
        when(mockedConfig.getProperty(eq(PARAM_NAME_ROWS), anyLong()))
                .thenReturn(14L);

        // when
        VendingMachine testedMachine = new VendingMachine(mockedConfig);

        // then
        assertEquals((Long) 8L, testedMachine.colsSize());
        assertEquals((Long) 14L, testedMachine.rowsSize());
    }

}