package pl.put.poznan.transformer.logic;

import java.util.HashMap;
import java.util.Map;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class TextTransformer {

    private final String[] transforms;
    private final Map<String, String> abbreviations;

    public TextTransformer(String[] transforms){
        this.transforms = transforms;
        this.abbreviations = new HashMap<>();
        initializeAbbreviations();
    }

    private void initializeAbbreviations() {
        abbreviations.put("na przykład", "np.");
        abbreviations.put("między innymi", "m.in.");
        abbreviations.put("i tym podobne", "itp.");
        abbreviations.put("i tak dalej", "itd.");
    }

    public String transform(String text){
        String result = text;
        // of course, normally it would do something based on the transforms
        for(String tranformer : transforms)
        {
            switch(tranformer.toLowerCase())
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
}
