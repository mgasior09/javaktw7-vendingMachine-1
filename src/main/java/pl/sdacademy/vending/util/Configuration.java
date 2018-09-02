package pl.sdacademy.vending.util;

/**
 * Interface reprezentujący konfigurację aplikacji. Do klas wymagających konfiguracji powinna zostać przekazana dowolna
 * implementacja tego interface, aby mogły one poprawnie działać. Interface oryginalnie został wprowadzony po to, aby
 * zmniejszość zleżność między klasami aplikacji z singletonem reprezentującym konfigurację zaczytaną z pliku properties.
 */
public interface Configuration {
    /**
     * Metoda pozwalająca pobrać wartość typy String z konfiguracji aplikacji
     * @param propertyName nazwa klucza, pod którym jest zapisana wartość
     * @param defaultValue domyślna wartość do zwrócenia
     * @return odczytana z konfiguracji wartość dla klucza propertyName. Jeżeli wartość nie istnieje, to zwracana jest
     * wartość zapisana w defaultValue
     */
    String getProperty(String propertyName, String defaultValue);
    /**
     * Metoda pozwalająca pobrać wartość typy Long z konfiguracji aplikacji
     * @param propertyName nazwa klucza, pod którym jest zapisana wartość
     * @param defaultValue domyślna wartość do zwrócenia
     * @return odczytana z konfiguracji wartość dla klucza propertyName. Jeżeli wartość nie istnieje, to zwracana jest
     * wartość zapisana w defaultValue
     */
    Long getProperty(String propertyName, Long defaultValue);
}
