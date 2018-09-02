package pl.sdacademy.vending.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConfigurationTest {

    @Test
    public void shouldReturnTextPropertyFromConfiguration() {
        // given
        Configuration configuration = new Configuration();
        String propertyName = "property.text.value";

        // when
        String retrievedValue = configuration.getProperty(propertyName, "DEFAULT_VALUE");

        // then
        assertEquals("some text", retrievedValue);
    }

    @Test
    public void shouldReturnLongPropertyFromConfiguration() {
        // given
        Configuration configuration = new Configuration();
        String propertyName = "property.long.value";

        // when
        Long retrievedNumber = configuration
                .getProperty(propertyName, 2L);

        // then
        assertEquals((Long) 1337L, retrievedNumber);
    }

    @Test
    public void shouldReturnDefaultValueWhenTextPropertyDoesNotExist() {
        // given
        Configuration configuration = new Configuration();
        String propertyName = "notExistingProperty";
        String defaultValue = "I'm default";

        // when
        String retrievedValue = configuration.getProperty(propertyName, defaultValue);

        // then
        assertEquals(defaultValue, retrievedValue);
    }

    @Test
    public void shouldReturnDefaultValueWhenLongPropertyDoesNotExist() {
        // given
        Configuration configuration = new Configuration();
        String propertyName = "notExistingProperty";
        Long defaultValue = 555L;

        // when
        Long retrievedValue = configuration.getProperty(propertyName, defaultValue);

        // then
        assertEquals(defaultValue, retrievedValue);
    }

}