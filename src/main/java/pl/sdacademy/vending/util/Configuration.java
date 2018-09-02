package pl.sdacademy.vending.util;

public interface Configuration {
    String getProperty(String propertyName, String defaultValue);
    Long getProperty(String propertyName, Long defaultValue);
}
