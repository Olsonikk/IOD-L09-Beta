package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpandTransformerTest {

    TextTransformerInterface transformers = new BaseTransformer();

    @Test
    void testExpandSingleWord() {
        // Arrange
        String input = "itd.";
        String expectedOutput = "i tak dalej";
        ExpandTransformer transformer = new ExpandTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testExpandSentence() {
        // Arrange
        String input = "itd. m.in.";
        String expectedOutput = "i tak dalej między innymi";
        ExpandTransformer transformer = new ExpandTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testExpandSentence2() {
        // Arrange
        String input = "np. to jest przykład";
        String expectedOutput = "na przykład to jest przykład";
        ExpandTransformer transformer = new ExpandTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testExpandEmptyString() {
        // Arrange
        String input = "";
        String expectedOutput = "";
        ExpandTransformer transformer = new ExpandTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }
}