package br.unifor.dashboard.model;

public enum SimulationAlgorithm {
    FIFO("FIFO", "First In, First Out", "pi pi-sort-amount-down"),
    LRU("LRU", "Least Recently Used", "pi pi-history"),
    CLOCK("CLOCK", "Relogio (Clock)", "pi pi-sync"),
    OPTIMAL("OPTIMAL", "Otimo", "pi pi-star");

    private final String key;
    private final String label;
    private final String icon;

    SimulationAlgorithm(String key, String label, String icon) {
        this.key = key;
        this.label = label;
        this.icon = icon;
    }

    public String getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public String getIcon() {
        return icon;
    }

    public static SimulationAlgorithm fromKey(String key) {
        for (SimulationAlgorithm algorithm : values()) {
            if (algorithm.key.equalsIgnoreCase(key)) {
                return algorithm;
            }
        }
        throw new IllegalArgumentException("Algoritmo invalido: " + key);
    }
}
