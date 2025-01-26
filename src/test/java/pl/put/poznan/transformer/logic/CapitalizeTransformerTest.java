package pl.put.poznan.transformer.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CapitalizeTransformerTest {

    @Mock
    private TextTransformerInterface mockBaseTransformer;

    // Wstrzykujemy ten mock do klasy CapitalizeTransformer
    @InjectMocks
    private CapitalizeTransformer capitalizeTransformer;

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

    @Test
    void testMockCapitalizeTransformer() {
        when(mockBaseTransformer.transform(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        String input = "mock test";
        String result = capitalizeTransformer.transform(input);
        assertEquals("Mock test", result);
        verify(mockBaseTransformer, atLeastOnce()).transform(anyString());
    }
    @Test
    void testMock2CapitalizeTransformer() {
        when(mockBaseTransformer.transform(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        String input = "MOCK";
        String result = capitalizeTransformer.transform(input);
        assertEquals("MOCK", result);
        verify(mockBaseTransformer, atLeastOnce()).transform(anyString());
    }@Test
    void testMock3CapitalizeTransformer() {
        when(mockBaseTransformer.transform(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        String input = "mOCK";
        String result = capitalizeTransformer.transform(input);
        assertEquals("MOCK", result);
        verify(mockBaseTransformer, atLeastOnce()).transform(anyString());
    }@Test
    void testMock4CapitalizeTransformer() {
        when(mockBaseTransformer.transform(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        String input = "1 skibidi sigma rel";
        String result = capitalizeTransformer.transform(input);
        assertEquals("1 skibidi sigma rel", result);
        verify(mockBaseTransformer, atLeastOnce()).transform(anyString());
    }@Test
    void testMock5CapitalizeTransformer() {
        when(mockBaseTransformer.transform(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        String input = "?XD";
        String result = capitalizeTransformer.transform(input);
        assertEquals("?XD", result);
        verify(mockBaseTransformer, atLeastOnce()).transform(anyString());
    }@Test
    void testMock6CapitalizeTransformer() {
        when(mockBaseTransformer.transform(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        String input = "olek chce stypendium";
        String result = capitalizeTransformer.transform(input);
        assertEquals("Olek chce stypendium", result);
        verify(mockBaseTransformer, atLeastOnce()).transform(anyString());
    }@Test
    void testMock7CapitalizeTransformer() {
        when(mockBaseTransformer.transform(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        String input = "a ja nie";
        String result = capitalizeTransformer.transform(input);
        assertEquals("A ja nie", result);
        verify(mockBaseTransformer, atLeastOnce()).transform(anyString());
    }@Test
    void testMock8CapitalizeTransformer() {
        when(mockBaseTransformer.transform(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        String input = "o co chodzi w mockach?";
        String result = capitalizeTransformer.transform(input);
        assertEquals("O co chodzi w mockach?", result);
        verify(mockBaseTransformer, atLeastOnce()).transform(anyString());
    }
    @Test
    void testMock9CapitalizeTransformer() {
        when(mockBaseTransformer.transform(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        String input = "moczki UwU";
        String result = capitalizeTransformer.transform(input);
        assertEquals("Moczki UwU", result);
        verify(mockBaseTransformer, atLeastOnce()).transform(anyString());
    }
    @Test
    void testMock10CapitalizeTransformer() {
        when(mockBaseTransformer.transform(anyString()))
                .thenAnswer(invocation -> invocation.getArgument(0));
        String input = "https://youtu.be/xvFZjo5PgG0?si=WvgzqI5dzbiiLd0p";
        String result = capitalizeTransformer.transform(input);
        assertEquals("Https://youtu.be/xvFZjo5PgG0?si=WvgzqI5dzbiiLd0p", result);
        verify(mockBaseTransformer, atLeastOnce()).transform(anyString());
    }
}
