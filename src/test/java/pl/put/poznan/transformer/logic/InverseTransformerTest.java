package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InverseTransformerTest {

    TextTransformerInterface transformers = new BaseTransformer();

    @Test
    void testInverseSingleWord() {
        // Arrange
        String input = "hello";
        String expectedOutput = "olleh";
        InverseTransformer transformer = new InverseTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testInverseSentence() {
        // Arrange
        String input = "this is a test";
        String expectedOutput = "tset a si siht";
        InverseTransformer transformer = new InverseTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testInverseSentence2() {
        // Arrange
        String input = "Hello Ma";
        String expectedOutput = "Am ollEh";
        InverseTransformer transformer = new InverseTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testInverseEmptyString() {
        // Arrange
        String input = "";
        String expectedOutput = "";
        InverseTransformer transformer = new InverseTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }
}