package br.unifor.dashboard.dto;

import java.util.List;

public class SimulationRequestDto {
    private final int frameCount;
    private final int[] pageSequence;
    private final List<String> comparisonAlgorithms;
    private final String selectedAlgorithm;

    public SimulationRequestDto(int frameCount, int[] pageSequence, List<String> comparisonAlgorithms, String selectedAlgorithm) {
        this.frameCount = frameCount;
        this.pageSequence = pageSequence;
        this.comparisonAlgorithms = comparisonAlgorithms;
        this.selectedAlgorithm = selectedAlgorithm;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public int[] getPageSequence() {
        return pageSequence;
    }

    public List<String> getComparisonAlgorithms() {
        return comparisonAlgorithms;
    }

    public String getSelectedAlgorithm() {
        return selectedAlgorithm;
    }
}
