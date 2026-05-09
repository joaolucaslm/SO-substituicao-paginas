package br.unifor.dashboard.dto;

public class LegacySimulationSummaryDto {
    private final int fifoFaults;
    private final int lruFaults;
    private final int clockFaults;
    private final int optimalFaults;

    public LegacySimulationSummaryDto(int fifoFaults, int lruFaults, int clockFaults, int optimalFaults) {
        this.fifoFaults = fifoFaults;
        this.lruFaults = lruFaults;
        this.clockFaults = clockFaults;
        this.optimalFaults = optimalFaults;
    }

    public int getFifoFaults() {
        return fifoFaults;
    }

    public int getLruFaults() {
        return lruFaults;
    }

    public int getClockFaults() {
        return clockFaults;
    }

    public int getOptimalFaults() {
        return optimalFaults;
    }
}
