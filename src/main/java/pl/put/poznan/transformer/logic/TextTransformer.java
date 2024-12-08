package pl.put.poznan.transformer.logic;

/**
 * This is just an example to show that the logic should be outside the REST service.
 */
public class TextTransformer {

    private final String[] transforms;

    public TextTransformer(String[] transforms){
        this.transforms = transforms;
    }

    public String transform(String text){
        String result = text;
        // of course, normally it would do something based on the transforms
        for(String tranformer : transforms)
        {
            switch(tranformer.toLowerCase())
            {
                case "uppercase":
                    result = result.toUpperCase();
                    break;
                case "lowercase":
                    result = result.toLowerCase();
                    break;
            }
        }

        return result;
    }
}
