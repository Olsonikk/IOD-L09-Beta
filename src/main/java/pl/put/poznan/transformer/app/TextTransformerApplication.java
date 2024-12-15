package pl.put.poznan.transformer.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Główna klasa aplikacji Spring Boot.
 * <p>
 * Odpowiada za uruchomienie aplikacji i skanowanie pakietów w celu automatycznej konfiguracji komponentów.
 */
@SpringBootApplication(scanBasePackages = {"pl.put.poznan.transformer.rest"})
public class TextTransformerApplication {
    /**
     * Metoda główna aplikacji. Służy do uruchomienia aplikacji Spring Boot.
     *
     * @param args parametry wejściowe aplikacji
     */
    public static void main(String[] args) {
        SpringApplication.run(TextTransformerApplication.class, args);

    }
}
