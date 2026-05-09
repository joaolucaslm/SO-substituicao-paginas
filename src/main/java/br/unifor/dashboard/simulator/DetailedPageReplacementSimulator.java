package br.unifor.dashboard.simulator;

import br.unifor.dashboard.dto.SimulationResultDto;
import br.unifor.dashboard.model.SimulationAlgorithm;

public interface DetailedPageReplacementSimulator {
    SimulationAlgorithm getAlgorithm();

    SimulationResultDto simulate(int[] pages, int frameCount);
}
