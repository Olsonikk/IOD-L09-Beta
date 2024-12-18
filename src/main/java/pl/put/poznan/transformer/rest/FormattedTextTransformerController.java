package pl.put.poznan.transformer.rest;

<<<<<<< HEAD


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



=======
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.put.poznan.transformer.logic.TextTransformerInterface;
import pl.put.poznan.transformer.logic.TransformerFactory;

@RestController
@RequestMapping("/formated")
@CrossOrigin
public class FormattedTextTransformerController {

>>>>>>> master
    private static final Logger logger = LoggerFactory.getLogger(FormattedTextTransformerController.class);

    /**
     * Obsługuje żądanie POST dla transformacji tekstu z żądaniem JSON.
     *
     * @param request Obiekt {@code TransformRequest} zawierający tekst do przetworzenia i listę transformacji.
     * @return Odpowiedź JSON zawierająca przekształcony tekst lub komunikat błędu.
     */
    @RequestMapping(value = "/transform", method = RequestMethod.POST, produces = "application/json")
<<<<<<< HEAD

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
=======
    public ResponseEntity<String> postJson(@RequestBody TransformRequest request) {

        logger.debug("Input text: " + request.getText());
        logger.debug("Transforms: " + Arrays.toString(request.getTransforms()));

        try {
            // Tworzenie dynamicznego łańcucha dekoratorów
            TextTransformerInterface transformer = TransformerFactory.createTransformer(request.getTransforms());
            String result = transformer.transform(request.getText());

            String jsonResponse = "{\"transformedText\": \"" + result + "\"}";
            return ResponseEntity.ok(jsonResponse);

        } catch (IllegalArgumentException e) {
            logger.error("Error: " + e.getMessage());
            String errorResponse = "{\"error\": \"" + e.getMessage() + "\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}

>>>>>>> master
/**
 * Klasa reprezentująca żądanie JSON dla transformacji tekstu.
 */
class TransformRequest {

    private String text;
<<<<<<< HEAD

=======
>>>>>>> master
    private String[] transforms;

    /**
     * Pobiera tekst wejściowy do przetworzenia.
     *
     * @return Tekst wejściowy.
     */
    public String getText() {
<<<<<<< HEAD

        return text;

    }


=======
        return text;
    }

>>>>>>> master
    /**
     * Ustawia tekst wejściowy do przetworzenia.
     *
     * @param text Tekst wejściowy.
     */
    public void setText(String text) {
<<<<<<< HEAD

        this.text = text;

    }


=======
        this.text = text;
    }

>>>>>>> master
    /**
     * Pobiera listę transformacji do zastosowania.
     *
     * @return Tablica transformacji.
     */
    public String[] getTransforms() {
<<<<<<< HEAD

        return transforms;

    }


=======
        return transforms;
    }

>>>>>>> master
    /**
     * Ustawia listę transformacji do zastosowania.
     *
     * @param transforms Tablica transformacji.
     */
    public void setTransforms(String[] transforms) {
<<<<<<< HEAD

        this.transforms = transforms;

    }

}
=======
        this.transforms = transforms;
    }
}
>>>>>>> master
