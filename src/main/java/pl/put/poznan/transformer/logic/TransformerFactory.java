package pl.put.poznan.transformer.logic;

public class TransformerFactory {
    public static TextTransformerInterface createTransformer(String[] transforms) {
        TextTransformerInterface transformer = new BaseTransformer();

        if (transforms != null) {
            for (String transform : transforms) {
                switch (transform.toLowerCase()) {
                    case "upper":
                        transformer = new UppercaseTransformer(transformer);
                        break;
                    case "lower":
                        transformer = new LowercaseTransformer(transformer);
                        break;
                    case "capitalize":
                        transformer = new CapitalizeTransformer(transformer);
                        break;
                    case "abbreviate":
                        transformer = new AbbreviateTransformer(transformer);
                        break;
                    case "expand":
                        transformer = new ExpandTransformer(transformer);
                        break;
                    case "inverse":
                        transformer = new InverseTransformer(transformer);
                        break;
                    case "removeduplicates":
                        transformer = new RemoveDuplicatesTransformer(transformer);
                        break;
                    case "latex":
                        transformer = new LatexTransformer(transformer);
                        break;
                    default:
                        throw new IllegalArgumentException("Nieznana transformacja: " + transform);
                }
            }
        }
        return transformer;
    }
}
