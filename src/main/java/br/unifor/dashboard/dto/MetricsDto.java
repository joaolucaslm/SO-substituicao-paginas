package br.unifor.dashboard.dto;

public class MetricsDto {
    private final int totalReferences;
    private final int pageFaults;
    private final int hits;
    private final double hitRate;
    private final double efficiency;

    public MetricsDto(int totalReferences, int pageFaults, int hits, double hitRate, double efficiency) {
        this.totalReferences = totalReferences;
        this.pageFaults = pageFaults;
        this.hits = hits;
        this.hitRate = hitRate;
        this.efficiency = efficiency;
    }

    public int getTotalReferences() {
        return totalReferences;
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

    public double getEfficiency() {
        return efficiency;
    }
}
