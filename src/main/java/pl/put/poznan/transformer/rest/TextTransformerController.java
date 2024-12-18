package pl.put.poznan.transformer.rest;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.put.poznan.transformer.logic.TextTransformerInterface;
import pl.put.poznan.transformer.logic.TransformerFactory;

/**
 * Kontroler REST do obsługi zapytań HTTP dla klasycznych transformacji tekstu.
 */
@RestController
@RequestMapping("/classic")
@CrossOrigin
public class TextTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    /**
     * Obsługuje żądanie GET dla transformacji tekstu.
     *
     * @param text       Tekst wejściowy, który ma zostać przetworzony.
     * @param transforms Lista transformacji do zastosowania na tekście. Jeśli nie podano, domyślną wartością jest "upper".
     * @return Odpowiedź JSON zawierająca przekształcony tekst lub komunikat błędu.
     */
    @RequestMapping(path = "/{text}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> get(@PathVariable String text,
                                      @RequestParam(value = "transforms", defaultValue = "upper") String[] transforms) {

        logger.debug("Input text: " + text);
        logger.debug("Transforms: " + Arrays.toString(transforms));

        try {
            // Tworzenie dynamicznego łańcucha dekoratorów
            TextTransformerInterface transformer = TransformerFactory.createTransformer(transforms);
            String result = transformer.transform(text);

            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            logger.error("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

