package br.unifor.dashboard.simulator;

import br.unifor.dashboard.dto.ClockStateDto;
import br.unifor.dashboard.dto.MemoryStepDto;
import br.unifor.dashboard.dto.MetricsDto;
import br.unifor.dashboard.dto.SimulationResultDto;
import br.unifor.dashboard.model.SimulationAlgorithm;
import br.unifor.dashboard.model.SimulationEventType;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDetailedSimulator implements DetailedPageReplacementSimulator {

    protected SimulationResultDto buildResult(SimulationAlgorithm algorithm, List<MemoryStepDto> steps, int[] pages) {
        int faults = (int) steps.stream().filter(MemoryStepDto::isFault).count();
        int hits = pages.length - faults;
        double hitRate = pages.length == 0 ? 0 : (hits * 100.0) / pages.length;
        double efficiency = pages.length == 0 ? 0 : (1 - ((double) faults / pages.length)) * 100.0;
        MetricsDto metrics = new MetricsDto(pages.length, faults, hits, hitRate, efficiency);
        return new SimulationResultDto(algorithm, steps, metrics);
    }

    protected List<Integer> snapshot(Integer[] frames) {
        List<Integer> memory = new ArrayList<>(frames.length);
        for (Integer frame : frames) {
            memory.add(frame);
        }
        return memory;
    }

    protected MemoryStepDto step(int index, int page, Integer[] frames, boolean hit, Integer removedPage,
                                 SimulationEventType eventType) {
        return step(index, page, frames, hit, removedPage, eventType, null);
    }

    protected MemoryStepDto step(int index, int page, Integer[] frames, boolean hit, Integer removedPage,
                                 SimulationEventType eventType, ClockStateDto clockState) {
        return new MemoryStepDto(index + 1, page, snapshot(frames), hit, !hit, removedPage, eventType, clockState);
    }

    protected void validateFrameCount(int frameCount) {
        if (frameCount <= 0) {
            throw new IllegalArgumentException("A quantidade de quadros deve ser maior que zero.");
        }
    }
}
