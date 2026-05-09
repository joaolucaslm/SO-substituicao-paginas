package br.unifor.dashboard.dto;

public class AlgorithmComparisonDto {
    private final String algorithmKey;
    private final String algorithmLabel;
    private final String icon;
    private final int pageFaults;
    private final int hits;
    private final double hitRate;
    private final int ranking;

    public AlgorithmComparisonDto(String algorithmKey, String algorithmLabel, String icon, int pageFaults, int hits, double hitRate, int ranking) {
        this.algorithmKey = algorithmKey;
        this.algorithmLabel = algorithmLabel;
        this.icon = icon;
        this.pageFaults = pageFaults;
        this.hits = hits;
        this.hitRate = hitRate;
        this.ranking = ranking;
    }

    public String getAlgorithmKey() {
        return algorithmKey;
    }

    public String getAlgorithmLabel() {
        return algorithmLabel;
    }

    public String getIcon() {
        return icon;
    }

    public int getPageFaults() {
        return pageFaults;
    }

    public int getHits() {
        return hits;
    }

    public double getHitRate() {
        return hitRate;
    }

    public int getRanking() {
        return ranking;
    }
}
