package br.unifor.dashboard.dto;

import java.util.List;
import java.util.Map;

public class SimulationDashboardDto {
    private final SimulationResultDto selectedResult;
    private final List<AlgorithmComparisonDto> comparison;
    private final Map<String, SimulationResultDto> resultsByAlgorithm;

    public SimulationDashboardDto(SimulationResultDto selectedResult, List<AlgorithmComparisonDto> comparison, Map<String, SimulationResultDto> resultsByAlgorithm) {
        this.selectedResult = selectedResult;
        this.comparison = comparison;
        this.resultsByAlgorithm = resultsByAlgorithm;
    }

    public SimulationResultDto getSelectedResult() {
        return selectedResult;
    }

    public List<AlgorithmComparisonDto> getComparison() {
        return comparison;
    }

    public Map<String, SimulationResultDto> getResultsByAlgorithm() {
        return resultsByAlgorithm;
    }
}
