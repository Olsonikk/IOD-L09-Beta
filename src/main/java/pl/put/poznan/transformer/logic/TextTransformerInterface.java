package pl.put.poznan.transformer.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Interfejs definiujący metodę transformacji tekstu.
 */
public interface TextTransformerInterface {
    /**
     * Transformuje dany tekst zgodnie z implementacją.
     *
     * @param text Tekst wejściowy do przetworzenia.
     * @return Przekształcony tekst.
     */
    String transform(String text);
}

/**
 * Abstrakcyjna klasa bazowa dla transformacji tekstu.
 * Umożliwia tworzenie dekoratorów dla różnych typów transformacji.
 */
abstract class TextTransformer implements TextTransformerInterface {
    protected final TextTransformerInterface transformer;

    /**
     * Konstruktor klasy bazowej dla transformatorów.
     *
     * @param transformer Dekorowany transformer.
     */
    public TextTransformer(TextTransformerInterface transformer) {
        this.transformer = transformer;
    }

    @Override
    public String transform(String text) {
        return transformer.transform(text);
    }
}

// Uppercase Transformer
/**
 * Transformer konwertujący tekst na wielkie litery.
 */
class UppercaseTransformer extends TextTransformer {
    /**
     * Tworzy nową instancję transformera do wielkich liter.
     *
     * @param transformer Dekorowany transformer.
     */
    public UppercaseTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        return transformer.transform(text).toUpperCase();
    }
}

// Lowercase Transformer
/**
 * Transformer konwertujący tekst na małe litery.
 */
class LowercaseTransformer extends TextTransformer {
    /**
     * Tworzy nową instancję transformera do małych liter.
     *
     * @param transformer Dekorowany transformer.
     */
    public LowercaseTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        return transformer.transform(text).toLowerCase();
    }
}

// Capitalize Transformer
/**
 * Transformer konwertujący pierwszy znak tekstu na wielką literę.
 */
class CapitalizeTransformer extends TextTransformer {
    /**
     * Tworzy nową instancję transformera do kapitalizacji.
     *
     * @param transformer Dekorowany transformer.
     */
    public CapitalizeTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        if (text == null || text.isEmpty()) return text;
        return transformer.transform(text.substring(0, 1).toUpperCase() + text.substring(1));
    }
}

// Abbreviate Transformer
/**
 * Transformer zamieniający frazy na ich skrócone formy.
 */
class AbbreviateTransformer extends TextTransformer {
    private static final Map<String, String> abbreviations = new HashMap<>();

    static {
        abbreviations.put("na przykład", "np.");
        abbreviations.put("między innymi", "m.in.");
        abbreviations.put("i tym podobne", "itp.");
        abbreviations.put("i tak dalej", "itd.");
    }
    /**
     * Tworzy nową instancję transformera do skracania tekstu.
     *
     * @param transformer Dekorowany transformer.
     */
    public AbbreviateTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        String result = transformer.transform(text);
        for (Map.Entry<String, String> entry : abbreviations.entrySet()) {
            result = result.replaceAll("(?i)" + entry.getKey(), entry.getValue());
        }
        return result;
    }
}

// Expand Transformer
/**
 * Transformer zamieniający skróty na ich pełne formy.
 */
class ExpandTransformer extends TextTransformer {
    private static final Map<String, String> expansions = new HashMap<>();

    static {
        expansions.put("np.", "na przykład");
        expansions.put("m.in.", "między innymi");
        expansions.put("itp.", "i tym podobne");
        expansions.put("itd.", "i tak dalej");
    }
    /**
     * Tworzy nową instancję transformera do rozszerzania skrótów.
     *
     * @param transformer Dekorowany transformer.
     */
    public ExpandTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        String result = transformer.transform(text);
        for (Map.Entry<String, String> entry : expansions.entrySet()) {
            result = result.replaceAll("(?i)" + Pattern.quote(entry.getKey()), entry.getValue());
        }
        return result;
    }
}

// Inverse Transformer
/**
 * Transformer odwracający tekst, zachowując wielkość liter.
 */
class InverseTransformer extends TextTransformer {
    /**
     * Tworzy nową instancję transformera do odwracania tekstu.
     *
     * @param transformer Dekorowany transformer.
     */
    public InverseTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        char[] characters = text.toCharArray();
        char[] reversed = new char[characters.length];

        for (int i = 0; i < characters.length; i++) {
            reversed[i] = characters[characters.length - 1 - i];
        }

        for (int i = 0; i < characters.length; i++) {
            if (Character.isUpperCase(characters[i])) {
                reversed[i] = Character.toUpperCase(reversed[i]);
            } else {
                reversed[i] = Character.toLowerCase(reversed[i]);
            }
        }
        return new String(reversed);
    }
}

// Remove Duplicates Transformer
/**
 * Transformer usuwający zduplikowane słowa w tekście.
 */
class RemoveDuplicatesTransformer extends TextTransformer {
    /**
     * Tworzy nową instancję transformera do usuwania duplikatów.
     *
     * @param transformer Dekorowany transformer.
     */
    public RemoveDuplicatesTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        Pattern pattern = Pattern.compile("\\b(\\w+)(\\s+\\1)+\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("$1");
    }
}

// LaTeX Transformer
/**
 * Transformer konwertujący znaki specjalne na ich odpowiedniki LaTeX.
 */
class LatexTransformer extends TextTransformer {
    private static final Map<String, String> latex = new HashMap<>();

    static {
        latex.put("&", "\\&");
        latex.put("$", "\\$");
        latex.put("%", "\\%");
        latex.put("#", "\\#");
        latex.put("_", "\\_");
        latex.put("{", "\\{");
        latex.put("}", "\\}");
        latex.put("~", "\\~{}");
        latex.put("^", "\\^{}");
    }
    /**
     * Tworzy nową instancję transformera do konwersji znaków specjalnych na LaTeX.
     *
     * @param transformer Dekorowany transformer.
     */
    public LatexTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        String result = transformer.transform(text);
        for (Map.Entry<String, String> entry : latex.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }
}

/**
 * Transformer konwertujący liczby w tekście na ich słowne odpowiedniki w języku polskim.
 */
class NumberToTextTransformer extends TextTransformer {

    public NumberToTextTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        return convertNumbersToText(transformer.transform(text));
    }

    /**
     * Konwertuje liczby występujące w tekście na ich słowne odpowiedniki.
     *
     * @param text wejściowy tekst
     * @return tekst z liczbami zamienionymi na słowa
     */
    public String convertNumbersToText(String text) {
        String[] unitNames = {"", "jeden", "dwa", "trzy", "cztery", "pięć", "sześć", "siedem", "osiem", "dziewięć"};
        String[] teenNames = {"dziesięć", "jedenaście", "dwanaście", "trzynaście", "czternaście", "piętnaście",
                "szesnaście", "siedemnaście", "osiemnaście", "dziewiętnaście"};
        String[] tensNames = {"", "", "dwadzieścia", "trzydzieści", "czterdzieści", "pięćdziesiąt",
                "sześćdziesiąt", "siedemdziesiąt", "osiemdziesiąt", "dziewięćdziesiąt"};
        String hundredName = "sto";

        Pattern pattern = Pattern.compile("\\b\\d+(\\.\\d{1,2})?\\b");
        Matcher matcher = pattern.matcher(text);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String match = matcher.group();
            String replacement;

            try {
                if (match.contains(".")) {
                    String[] parts = match.split("\\.");
                    int wholePart = Integer.parseInt(parts[0]);
                    int fractionalPart = Integer.parseInt(parts[1]);

                    replacement = (wholePart <= 100 ? numberToText(wholePart, unitNames, teenNames, tensNames, hundredName) : match) +
                            " koma " +
                            numberToText(fractionalPart, unitNames, teenNames, tensNames, hundredName);
                } else {
                    int number = Integer.parseInt(match);
                    replacement = numberToText(number, unitNames, teenNames, tensNames, hundredName);
                }
            } catch (NumberFormatException e) {
                replacement = match;
            }

            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * Konwertuje liczbę całkowitą na jej słowny odpowiednik w języku polskim.
     * <p>
     * Obsługuje liczby od 0 do 100. Dla liczb spoza tego zakresu zwraca ich oryginalną reprezentację.
     *
     * @param number       liczba do przekształcenia na tekst
     * @param unitNames    tablica słownych odpowiedników jednostek (0-9)
     * @param teenNames    tablica słownych odpowiedników liczb od 10 do 19
     * @param tensNames    tablica słownych odpowiedników dziesiątek (20, 30, ..., 90)
     * @param hundredName  słowny odpowiednik liczby 100
     * @return słowna reprezentacja liczby w języku polskim
     */
    public String numberToText(int number, String[] unitNames, String[] teenNames, String[] tensNames, String hundredName) {
        if (number > 100) {
            return Integer.toString(number);
        }
        if (number == 0) {
            return "zero";
        } else if (number == 100) {
            return hundredName;
        } else if (number >= 20) {
            int tens = number / 10;
            int units = number % 10;
            return tensNames[tens] + (units > 0 ? " " + unitNames[units] : "");
        } else if (number >= 10) {
            return teenNames[number - 10];
        } else {
            return unitNames[number];
        }
    }
}

// Base Transformer Implementation
/**
 * Bazowy transformer, który zwraca tekst bez żadnych zmian.
 */
class BaseTransformer implements TextTransformerInterface {
    @Override
    public String transform(String text) {
        return text;
    }
}


