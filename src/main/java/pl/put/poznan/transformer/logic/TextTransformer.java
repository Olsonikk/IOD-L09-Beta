package pl.put.poznan.transformer.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class TextTransformer {

    private final String[] transforms;
    private final Map<String, String> abbreviations;
    private final Map<String, String> expansions;

    public TextTransformer(String[] transforms){
        this.transforms = transforms;
        this.abbreviations = new HashMap<>();
        this.expansions = new HashMap<>();
        initializeAbbreviations();
        initializeExpansions();
    }

    private void initializeAbbreviations() {
        abbreviations.put("na przykład", "np.");
        abbreviations.put("między innymi", "m.in.");
        abbreviations.put("i tym podobne", "itp.");
        abbreviations.put("i tak dalej", "itd.");
    }

    private void initializeExpansions() {
        expansions.put("prof.", "profesor");
        expansions.put("dr", "doktor");
        expansions.put("np.", "na przykład");
        expansions.put("itd.", "i tym podobne");
    }

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
            }
        }

        return result;
    }

    private String abbreviate(String text) {
        for (Map.Entry<String, String> entry : abbreviations.entrySet()) {
            text = text.replaceAll("(?i)" + entry.getKey(), entry.getValue());
        }
        return text;
    }

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
    
    private String numberToText(int number, String[] unitNames, String[] teenNames, String[] tensNames, String hundredName) {
        if (number < 0 || number > 100) {
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
    private String removeDuplicateWords(String text) {
        // Pattern to match duplicate words in direct adjacency
        Pattern pattern = Pattern.compile("\\b(\\w+)(\\s+\\1)+\\b", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        return matcher.replaceAll("$1");
    }
    
}
