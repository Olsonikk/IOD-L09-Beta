package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CapitalizeTransformerTest {

    TextTransformerInterface transformers = new BaseTransformer();

    @Test
    void testCapitalizeSingleWord() {
        // Arrange
        String input = "hello";
        String expectedOutput = "Hello";
        CapitalizeTransformer transformer = new CapitalizeTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testCapitalizeSentence() {
        // Arrange
        String input = "this is a test";
        String expectedOutput = "This is a test";
        CapitalizeTransformer transformer = new CapitalizeTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testCapitalizeEmptyString() {
        // Arrange
        String input = "";
        String expectedOutput = "";
        CapitalizeTransformer transformer = new CapitalizeTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testCapitalizeAlreadyCapitalized() {
        // Arrange
        String input = "Already capitalized text";
        String expectedOutput = "Already capitalized text";
        CapitalizeTransformer transformer = new CapitalizeTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }
}
