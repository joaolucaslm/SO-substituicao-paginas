package br.unifor.dashboard.dto;

import br.unifor.dashboard.model.SimulationAlgorithm;
import java.util.List;

public class SimulationResultDto {
    private final SimulationAlgorithm algorithm;
    private final List<MemoryStepDto> steps;
    private final MetricsDto metrics;

    public SimulationResultDto(SimulationAlgorithm algorithm, List<MemoryStepDto> steps, MetricsDto metrics) {
        this.algorithm = algorithm;
        this.steps = steps;
        this.metrics = metrics;
    }

    public SimulationAlgorithm getAlgorithm() {
        return algorithm;
    }

    public List<MemoryStepDto> getSteps() {
        return steps;
    }

    public MetricsDto getMetrics() {
        return metrics;
    }

    public ClockStateDto getLatestClockState() {
        if (steps == null || steps.isEmpty()) {
            return null;
        }

        for (int i = steps.size() - 1; i >= 0; i--) {
            if (steps.get(i).getClockState() != null) {
                return steps.get(i).getClockState();
            }
        }

        return null;
    }
}
