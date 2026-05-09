package br.unifor.dashboard.service;

import br.unifor.dashboard.dto.AlgorithmComparisonDto;
import br.unifor.dashboard.dto.SimulationDashboardDto;
import br.unifor.dashboard.dto.SimulationRequestDto;
import br.unifor.dashboard.dto.SimulationResultDto;
import br.unifor.dashboard.model.SimulationAlgorithm;
import br.unifor.dashboard.simulator.ClockDetailedSimulator;
import br.unifor.dashboard.simulator.DetailedPageReplacementSimulator;
import br.unifor.dashboard.simulator.FifoDetailedSimulator;
import br.unifor.dashboard.simulator.LruDetailedSimulator;
import br.unifor.dashboard.simulator.OptimalDetailedSimulator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class SimulationFacadeService {
    private final Map<SimulationAlgorithm, DetailedPageReplacementSimulator> simulators;

    public SimulationFacadeService() {
        simulators = new LinkedHashMap<>();
        register(new FifoDetailedSimulator());
        register(new LruDetailedSimulator());
        register(new ClockDetailedSimulator());
        register(new OptimalDetailedSimulator());
    }

    public SimulationDashboardDto execute(SimulationRequestDto request) {
        List<SimulationAlgorithm> comparisonAlgorithms = request.getComparisonAlgorithms().stream()
                .map(SimulationAlgorithm::fromKey)
                .distinct()
                .toList();

        if (comparisonAlgorithms.isEmpty()) {
            throw new IllegalArgumentException("Selecione ao menos um algoritmo para comparacao.");
        }

        Map<String, SimulationResultDto> resultsByAlgorithm = new LinkedHashMap<>();
        for (SimulationAlgorithm algorithm : comparisonAlgorithms) {
            SimulationResultDto result = simulators.get(algorithm).simulate(request.getPageSequence(), request.getFrameCount());
            resultsByAlgorithm.put(algorithm.getKey(), result);
        }

        SimulationAlgorithm selectedAlgorithm = SimulationAlgorithm.fromKey(request.getSelectedAlgorithm());
        SimulationResultDto selectedResult = resultsByAlgorithm.computeIfAbsent(selectedAlgorithm.getKey(),
                key -> simulators.get(selectedAlgorithm).simulate(request.getPageSequence(), request.getFrameCount()));

        List<AlgorithmComparisonDto> comparison = buildComparison(resultsByAlgorithm);
        return new SimulationDashboardDto(selectedResult, comparison, resultsByAlgorithm);
    }

    private void register(DetailedPageReplacementSimulator simulator) {
        simulators.put(simulator.getAlgorithm(), simulator);
    }

    private List<AlgorithmComparisonDto> buildComparison(Map<String, SimulationResultDto> resultsByAlgorithm) {
        List<SimulationResultDto> orderedResults = new ArrayList<>(resultsByAlgorithm.values());
        orderedResults.sort(Comparator.comparingInt(result -> result.getMetrics().getPageFaults()));

        Map<String, Integer> rankingByKey = new LinkedHashMap<>();
        for (int i = 0; i < orderedResults.size(); i++) {
            rankingByKey.put(orderedResults.get(i).getAlgorithm().getKey(), i + 1);
        }

        return resultsByAlgorithm.values().stream()
                .sorted(Comparator.comparingInt(result -> rankingByKey.get(result.getAlgorithm().getKey())))
                .map(result -> new AlgorithmComparisonDto(
                        result.getAlgorithm().getKey(),
                        result.getAlgorithm().getLabel(),
                        result.getAlgorithm().getIcon(),
                        result.getMetrics().getPageFaults(),
                        result.getMetrics().getHits(),
                        result.getMetrics().getHitRate(),
                        rankingByKey.get(result.getAlgorithm().getKey())))
                .collect(Collectors.toList());
    }
}
