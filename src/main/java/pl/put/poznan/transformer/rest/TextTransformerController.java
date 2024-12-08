package pl.put.poznan.transformer.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.TextTransformer;

import java.util.Arrays;

@RestController
@RequestMapping("/{text}")
public class TextTransformerController {

    private static final Logger logger = LoggerFactory.getLogger(TextTransformerController.class);

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> get(@PathVariable String text,
                              @RequestParam(value="transforms", defaultValue="uppercase") String[] transforms) {

        logger.debug("Input text: " + text);
        logger.debug("Transforms: " + Arrays.toString(transforms));

        try {
            // Tworzenie obiektu TextTransformer
            TextTransformer transformer = new TextTransformer(transforms);

            // Zwracanie przetworzonego tekstu
            return ResponseEntity.ok(transformer.transform(text));

        } catch (IllegalArgumentException e) {
            // Obsługa nieznanej transformacji
            logger.error("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> post(@PathVariable String text,
                                       @RequestBody String[] transforms) {

        logger.debug("Input text: " + text);
        logger.debug("Transforms: " + Arrays.toString(transforms));

        try {
            // Tworzenie obiektu TextTransformer
            TextTransformer transformer = new TextTransformer(transforms);

            // Zwracanie przetworzonego tekstu
            return ResponseEntity.ok(transformer.transform(text));

        } catch (IllegalArgumentException e) {
            // Obsługa nieznanej transformacji
            logger.error("Error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
