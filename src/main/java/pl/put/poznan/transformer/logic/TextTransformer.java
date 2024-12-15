package pl.put.poznan.transformer.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Klasa {@code TextTransformer} służy do wykonywania różnych transformacji tekstu.
 * <p>
 * Obejmuje transformacje takie jak zmiana wielkości liter, skracanie lub rozszerzanie fraz,
 * usuwanie zduplikowanych słów, zamiana liczb na tekst oraz formatowanie tekstu zgodnie z wymogami LaTeX.
 */
public class TextTransformer {

    private final String[] transforms;
    private final Map<String, String> abbreviations;
    private final Map<String, String> expansions;
    private final Map<String, String> Latex;
    /**
     * Tworzy obiekt {@code TextTransformer} z określonymi typami transformacji.
     *
     * @param transforms tablica typów transformacji do wykonania w określonej kolejności
     */
    public TextTransformer(String[] transforms){
        this.transforms = transforms;
        this.abbreviations = new HashMap<>();
        this.expansions = new HashMap<>();
        this.Latex = new HashMap<>();
        initializeAbbreviations();
        initializeExpansions();
        initializeLatex();
    }
    /**
     * Inicjalizuje popularne skróty tekstowe.
     */
    private void initializeAbbreviations() {
        abbreviations.put("na przykład", "np.");
        abbreviations.put("między innymi", "m.in.");
        abbreviations.put("i tym podobne", "itp.");
        abbreviations.put("i tak dalej", "itd.");
    }
    /**
     * Inicjalizuje pełne formy skrótów tekstowych.
     */
    private void initializeExpansions() {
        expansions.put("prof.", "profesor");
        expansions.put("dr", "doktor");
        expansions.put("np.", "na przykład");
        expansions.put("itd.", "i tym podobne");
    }
    /**
     * Inicjalizuje mapowanie znaków specjalnych do zgodności z LaTeX.
     */
    private void initializeLatex() {
        //Latex.put("\\", "\\textbackslash");
        Latex.put("&", "\\&");
        Latex.put("$", "\\$");
        Latex.put("%", "\\%");
        Latex.put("#", "\\#");
        Latex.put("_", "\\_");
        Latex.put("{", "\\{");
        Latex.put("}", "\\}");
        Latex.put("~", "\\~{}");
        Latex.put("^", "\\^{}");
    }
    /**
     * Transformuje podany tekst na podstawie określonych typów transformacji.
     *
     * @param text wejściowy tekst do transformacji
     * @return przetransformowany tekst
     */
    public String transform(String text){
        String result = text;
        // Apply transformations based on the given list
        for(String transformer : transforms)
        {
            switch(transformer.toLowerCase())
            {
                case "upper":
                    result = result.toUpperCase();
                    break;
                case "lower":
                    result = result.toLowerCase();
                    break;
                case "capitalize":
                    result = result.substring(0, 1).toUpperCase() + result.substring(1);
                    break;
                case "abbreviate":
                    result = abbreviate(result);
                    break;
                case "expand":
                    result = expand(result);
                    break;
                case "inverse":
                    result = inverse(result);
                    break;
                case "numbertotext":
                    result = convertNumbersToText(result);
                    break;
                case "removeduplicates":
                    result = removeDuplicateWords(result);
                    break;
                case "latex":
                    result = TextToLatex(result);
                    break;
            }
        }

        return result;
    }
    /**
     * Zastępuje popularne frazy ich skróconymi formami.
     *
     * @param text wejściowy tekst
     * @return tekst ze skróconymi frazami
     */
    private String abbreviate(String text) {
        for (Map.Entry<String, String> entry : abbreviations.entrySet()) {
            text = text.replaceAll("(?i)" + entry.getKey(), entry.getValue());
        }
        return text;
    }
    /**
     * Rozszerza skróty tekstowe do ich pełnych form.
     *
     * @param text wejściowy tekst
     * @return tekst z rozszerzonymi skrótami
     */
    private String expand(String text) {
        for (Map.Entry<String, String> entry : expansions.entrySet()) {
            String regex = "(?i)" + Pattern.quote(entry.getKey());
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            StringBuffer buffer = new StringBuffer();

            while (matcher.find()) {
                String replacement = entry.getValue();
                if (Character.isUpperCase(matcher.group().charAt(0))) {
                    replacement = replacement.substring(0, 1).toUpperCase() + replacement.substring(1);
                }
                matcher.appendReplacement(buffer, replacement);
            }
            matcher.appendTail(buffer);
            text = buffer.toString();
        }
        return text;
    }
    /**
     * Odwraca tekst zachowując wielkość liter.
     *
     * @param text wejściowy tekst
     * @return odwrócony tekst z zachowaniem wielkości liter
     */
    private String inverse(String text) {
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
    /**
     * Konwertuje liczby występujące w tekście na ich słowne odpowiedniki.
     *
     * @param text wejściowy tekst
     * @return tekst z liczbami zamienionymi na słowa
     */
    private String convertNumbersToText(String text) {
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
     * Obsługuje liczby od -100 do 100. Dla liczb spoza tego zakresu zwraca ich oryginalną reprezentację.
     *
     * @param number       liczba do przekształcenia na tekst
     * @param unitNames    tablica słownych odpowiedników jednostek (0-9)
     * @param teenNames    tablica słownych odpowiedników liczb od 10 do 19
     * @param tensNames    tablica słownych odpowiedników dziesiątek (20, 30, ..., 90)
     * @param hundredName  słowny odpowiednik liczby 100
     * @return słowna reprezentacja liczby w języku polskim
     */
    private String numberToText(int number, String[] unitNames, String[] teenNames, String[] tensNames, String hundredName) {
        if (number > 100) {
            return Integer.toString(number);
        }
        if (number == 0) {
            return "zero";
        }
        else if (number == 100) {
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
    /**
     * Usuwa zduplikowane słowa występujące obok siebie.
     *
     * @param text wejściowy tekst
     * @return tekst bez zduplikowanych słów
     */
    private String removeDuplicateWords(String text) {
        // Pattern to match duplicate words in direct adjacency
        Pattern pattern = Pattern.compile("\\b(\\w+)(\\s+\\1)+\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("$1");
    }
    /**
     * Konwertuje znaki specjalne w tekście na zgodne z LaTeX odpowiedniki.
     *
     * @param input wejściowy tekst
     * @return tekst przekształcony zgodnie z wymogami LaTeX
     */
    public String TextToLatex(String input) {
        for (Map.Entry<String, String> entry : Latex.entrySet()) {
            input = input.replace(entry.getKey(), entry.getValue());
        }
        return input;
    }

}
