package pl.put.poznan.transformer.logic;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface TextTransformerInterface {
    String transform(String text);
}

abstract class TextTransformer implements TextTransformerInterface {
    protected final TextTransformerInterface transformer;

    public TextTransformer(TextTransformerInterface transformer) {
        this.transformer = transformer;
    }

    @Override
    public String transform(String text) {
        return transformer.transform(text);
    }
}

// Uppercase Transformer
class UppercaseTransformer extends TextTransformer {
    public UppercaseTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        return transformer.transform(text).toUpperCase();
    }
}

// Lowercase Transformer
class LowercaseTransformer extends TextTransformer {
    public LowercaseTransformer(TextTransformerInterface transformer) {
        super(transformer);
    }

    @Override
    public String transform(String text) {
        return transformer.transform(text).toLowerCase();
    }
}

// Capitalize Transformer
class CapitalizeTransformer extends TextTransformer {
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
class AbbreviateTransformer extends TextTransformer {
    private static final Map<String, String> abbreviations = new HashMap<>();

    static {
        abbreviations.put("na przykład", "np.");
        abbreviations.put("między innymi", "m.in.");
        abbreviations.put("i tym podobne", "itp.");
        abbreviations.put("i tak dalej", "itd.");
    }

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
class ExpandTransformer extends TextTransformer {
    private static final Map<String, String> expansions = new HashMap<>();

    static {
        expansions.put("np.", "na przykład");
        expansions.put("m.in.", "między innymi");
        expansions.put("itp.", "i tym podobne");
        expansions.put("itd.", "i tak dalej");
    }

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
class InverseTransformer extends TextTransformer {
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
class RemoveDuplicatesTransformer extends TextTransformer {
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

// Base Transformer Implementation
class BaseTransformer implements TextTransformerInterface {
    @Override
    public String transform(String text) {
        return text;
    }
}


