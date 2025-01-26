package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LatexTransformerTest {

    TextTransformerInterface transformers = new BaseTransformer();

    @Test
    void testLatexSingleWord() {
        // Arrange
        String input = "&";
        String expectedOutput = "\\&";
        LatexTransformer transformer = new LatexTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testLatexSentence() {
        // Arrange
        String input = "Tom&Jerry";
        String expectedOutput = "Tom\\&Jerry";
        LatexTransformer transformer = new LatexTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testLatexSentence2() {
        // Arrange
        String input = "Dziedzina = {1, 2, 3}";
        String expectedOutput = "Dziedzina = \\{1, 2, 3\\}";
        LatexTransformer transformer = new LatexTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testLatexEmptyString() {
        // Arrange
        String input = "";
        String expectedOutput = "";
        LatexTransformer transformer = new LatexTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }
}