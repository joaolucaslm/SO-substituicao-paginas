package br.unifor.dashboard.util;

public final class PageSequenceParser {
    private PageSequenceParser() {
    }

    public static int[] parse(String rawSequence) {
        if (rawSequence == null || rawSequence.isBlank()) {
            throw new IllegalArgumentException("Informe a sequencia de paginas.");
        }

        String[] tokens = rawSequence.trim().split("\\s+");
        int[] pages = new int[tokens.length];

        for (int i = 0; i < tokens.length; i++) {
            try {
                pages[i] = Integer.parseInt(tokens[i]);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("A sequencia deve conter apenas numeros inteiros.");
            }
        }

        return pages;
    }
}
