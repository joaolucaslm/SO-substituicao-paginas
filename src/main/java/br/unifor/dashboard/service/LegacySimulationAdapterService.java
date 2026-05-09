package br.unifor.dashboard.service;

import br.unifor.dashboard.dto.LegacySimulationSummaryDto;
import br.unifor.dashboard.dto.SimulationRequestDto;
import br.unifor.dashboard.model.SimulationAlgorithm;
import org.springframework.stereotype.Service;

@Service
public class LegacySimulationAdapterService {
    private final SimulationFacadeService simulationFacadeService;

    public LegacySimulationAdapterService(SimulationFacadeService simulationFacadeService) {
        this.simulationFacadeService = simulationFacadeService;
    }

    public LegacySimulationSummaryDto executeSummary(SimulationRequestDto request) {
        var dashboard = simulationFacadeService.execute(request);
        return new LegacySimulationSummaryDto(
                dashboard.getResultsByAlgorithm().get(SimulationAlgorithm.FIFO.getKey()).getMetrics().getPageFaults(),
                dashboard.getResultsByAlgorithm().get(SimulationAlgorithm.LRU.getKey()).getMetrics().getPageFaults(),
                dashboard.getResultsByAlgorithm().get(SimulationAlgorithm.CLOCK.getKey()).getMetrics().getPageFaults(),
                dashboard.getResultsByAlgorithm().get(SimulationAlgorithm.OPTIMAL.getKey()).getMetrics().getPageFaults());
    }
}
