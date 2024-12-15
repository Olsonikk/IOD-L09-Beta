package pl.put.poznan.transformer.rest;



import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import pl.put.poznan.transformer.logic.TextTransformer;

import java.util.Arrays;

import org.springframework.web.bind.annotation.RequestBody;


/**
 * Kontroler REST do obsługi zapytań HTTP dla transformacji sformatowanego tekstu.
 */
@RestController

@RequestMapping("/formated")

@CrossOrigin

public class FormattedTextTransformerController {



    private static final Logger logger = LoggerFactory.getLogger(FormattedTextTransformerController.class);

    /**
     * Obsługuje żądanie POST dla transformacji tekstu z żądaniem JSON.
     *
     * @param request Obiekt {@code TransformRequest} zawierający tekst do przetworzenia i listę transformacji.
     * @return Odpowiedź JSON zawierająca przekształcony tekst lub komunikat błędu.
     */
    @RequestMapping(value = "/transform", method = RequestMethod.POST, produces = "application/json")

    public ResponseEntity<String> postJson(@RequestBody TransformRequest request) {



        logger.debug("Input text: " + request.getText());

        logger.debug("Transforms: " + Arrays.toString(request.getTransforms()));



        try {

            // Tworzenie obiektu TextTransformer

            TextTransformer transformer = new TextTransformer(request.getTransforms());



            // Zwracanie przetworzonego tekstu w formacie JSON

            String jsonResponse = "{\"transformedText\": \"" + transformer.transform(request.getText()) + "\"}";

            return ResponseEntity.ok(jsonResponse);



        } catch (IllegalArgumentException e) {

            // Obsługa nieznanej transformacji

            logger.error("Error: " + e.getMessage());

            String errorResponse = "{\"error\": \"" + e.getMessage() + "\"}";

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        }

    }

}

// New class to represent the JSON request body
/**
 * Klasa reprezentująca żądanie JSON dla transformacji tekstu.
 */
class TransformRequest {

    private String text;

    private String[] transforms;

    /**
     * Pobiera tekst wejściowy do przetworzenia.
     *
     * @return Tekst wejściowy.
     */
    public String getText() {

        return text;

    }


    /**
     * Ustawia tekst wejściowy do przetworzenia.
     *
     * @param text Tekst wejściowy.
     */
    public void setText(String text) {

        this.text = text;

    }


    /**
     * Pobiera listę transformacji do zastosowania.
     *
     * @return Tablica transformacji.
     */
    public String[] getTransforms() {

        return transforms;

    }


    /**
     * Ustawia listę transformacji do zastosowania.
     *
     * @param transforms Tablica transformacji.
     */
    public void setTransforms(String[] transforms) {

        this.transforms = transforms;

    }

}
