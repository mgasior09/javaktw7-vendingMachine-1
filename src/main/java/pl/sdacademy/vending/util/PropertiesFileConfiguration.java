package pl.sdacademy.vending.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Jedyna znana nam implementacja interface {@link Configuration}, która odczytuje konfigurację z pliku application.properties
 * zapisanego w src/main/resources. Jest to singleton w wersji Static Holder, gdzie pojedyńcza jego instancja jest
 * gwarantowana przez ClassLoadera - jest zazwyczaj szybszy w działaniu od wersji Double Checker, ze względu na brak
 * modyfikatora "volatile".
 */
public class PropertiesFileConfiguration implements Configuration {

    /**
     * Stała pomocnicza, która przechowuje nazwę pliku z konfiguracją
     */
    private static final String PROPERTIES_FILE_LOCATION = "application.properties";
    /**
     * Pole, do którego będą załadowane wpisy z pliku wskazanego przez {@link #PROPERTIES_FILE_LOCATION}
     */
    private final Properties properties;

    /**
     * Konstrukt jest prywatny, aby nikt nie mógł go użyć w nieodpowiedni sposób. Jedynym miejscem w aplikacji, gdzie
     * konstruktor może zostać użyty, jest {@link InstanceHolder}, który także przechowuje instancję tego singletona.
     */
    private PropertiesFileConfiguration() {
        // konstruktor inicjalizuje pole properties, do którego wczytamy wszystkie wpisy z pliku application.properties
        properties = new Properties();

        // Ponieważ pliki znajdujące się w katalogu resources będą dołączone automatycznie do zbudowanej paczki, to używamy
        // ClassLoadera do odczytania pliku z konfiguracją. Plik konfiguracji jest odczytywany jako InputStream.
        // Zainicjowanie tego InputStream w instrukcji try zapewnia nam automatyczne zamknięcie strumienia po wyjściu
        // z bloku try-catch. Nie musimy tego robić ręcznie. Gdybyśmy nie zamykali strumieni, to mielibyśmy "wycieki pamięci"
        // czyli używanie przez aplikację więcej pamięci operacyjnej komputera niż faktycznie jest potrzebne. Pamięć, która
        // wyciekła, nie może zostać zwolniona przez aplikację, co powoduje co raz większe jej zużycie.
        try (InputStream propertiesFile = ClassLoader
                .getSystemResourceAsStream(PROPERTIES_FILE_LOCATION)) {
            // metoda load pozwala na wczytanie pliku z parametrami do obiektu properties. Od tego momentu parametry
            // konfiguracyjne mogą być odczytane z obiektu properties.
             properties.load(propertiesFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // statyczna metoda umożliwiająca pobranie instancji singletona.
    public static PropertiesFileConfiguration getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Metoda odczytująca parametr z obiektu properties - obiektu, który przechowuje parametry odczytane z application.properties.
     * @param propertyName nazwa klucza, pod którym jest zapisana wartość
     * @param defaultValue domyślna wartość do zwrócenia
     * @return jeżeli wartość istnieje, to jest zwracana jako String, jeżeli wartość dla klucza nie istnieje, to jest
     * zwracana wartość domyślna
     */
    public String getProperty(String propertyName, String defaultValue) {
        // w pierwszym kroku próbujemy pobrać wartość z properties dla żądanego klucza (czyli na propertyName)
        String requestedValue = properties.getProperty(propertyName);
        // sprawdzamy, czy udało się odczytać wartość z properties
        if (requestedValue == null) {
            // jeżeli nie, to zwracamy domyślną wartość zdefiniowaną przez użytkownika
            return defaultValue;
        } else {
            // jeżeli udało się odczytać wartość z properties, to zwracamy ją.
            return requestedValue;
        }

        // alternatywny, jednolinijkowy sposób implementacji tej metody
//        return properties.getProperty(propertyName, defaultValue);
    }


    /**
     * Metoda odczytująca parametr z obiektu properties - obiektu, który przechowuje parametry odczytane z application.properties.
     * @param propertyName nazwa klucza, pod którym jest zapisana wartość
     * @param defaultValue domyślna wartość do zwrócenia
     * @return jeżeli wartość istnieje, to jest zwracana jako Long, jeżeli wartość dla klucza nie istnieje, to jest
     * zwracana wartość domyślna
     */
    public Long getProperty(String propertyName, Long defaultValue) {
        // w pierwszym kroku próbujemy pobrać wartość z properties dla żądanego klucza (czyli na propertyName)
        String requestedValue = properties.getProperty(propertyName);
        // sprawdzamy, czy udało się odczytać wartość z properties
        if (requestedValue == null) {
            // jeżeli nie, to zwracamy domyślną wartość zdefiniowaną przez użytkownika
            return defaultValue;
        } else {
            // jeżeli odczytaliśmy wartość z properties, to za pomocą metody parseLong z klasy Long konwertujemy teskt na liczbę
            return Long.parseLong(requestedValue);
        }
    }

    /**
     * Klasa przechowująca instancję naszego singletona. ClassLoader gwarantuje nam pojedyńcze załadowanie tej klasy,
     * a co za tym idzie - pojedyńczą instację singletonu.
     */
    private static class InstanceHolder {
        private static final PropertiesFileConfiguration INSTANCE = new PropertiesFileConfiguration();
    }
}
