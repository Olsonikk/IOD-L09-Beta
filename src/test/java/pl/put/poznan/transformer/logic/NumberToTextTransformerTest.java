package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NumberToTextTransformerTest {

    TextTransformerInterface transformers = new BaseTransformer();

    @Test
    void testNumberToTextSingleWord() {
        // Arrange
        String input = "13";
        String expectedOutput = "trzynaście";
        NumberToTextTransformer transformer = new NumberToTextTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testNumberToTextSentence() {
        // Arrange
        String input = "100 dalmatyńczyków";
        String expectedOutput = "sto dalmatyńczyków";
        NumberToTextTransformer transformer = new NumberToTextTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testNumberToTextSentence2() {
        // Arrange
        String input = "40 lat minęło jak 1 dzień";
        String expectedOutput = "czterdzieści lat minęło jak jeden dzień";
        NumberToTextTransformer transformer = new NumberToTextTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }

    @Test
    void testNumberToTextEmptyString() {
        // Arrange
        String input = "";
        String expectedOutput = "";
        NumberToTextTransformer transformer = new NumberToTextTransformer(transformers);

        // Act
        String result = transformer.transform(input);

        // Assert
        assertEquals(expectedOutput, result);
    }
}