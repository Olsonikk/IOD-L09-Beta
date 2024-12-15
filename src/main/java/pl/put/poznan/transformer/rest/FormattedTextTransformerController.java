package pl.put.poznan.transformer.rest;



import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import pl.put.poznan.transformer.logic.TextTransformer;

import java.util.Arrays;

import org.springframework.web.bind.annotation.RequestBody;



@RestController

@RequestMapping("/formated")

@CrossOrigin

public class FormattedTextTransformerController {



    private static final Logger logger = LoggerFactory.getLogger(FormattedTextTransformerController.class);



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

class TransformRequest {

    private String text;

    private String[] transforms;



    public String getText() {

        return text;

    }



    public void setText(String text) {

        this.text = text;

    }



    public String[] getTransforms() {

        return transforms;

    }



    public void setTransforms(String[] transforms) {

        this.transforms = transforms;

    }

}
