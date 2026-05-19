package br.unifor.dashboard.view;

import br.unifor.dashboard.dto.AlgorithmComparisonDto;
import br.unifor.dashboard.dto.SimulationResultDto;
import java.util.List;

public record SimulationDashboardViewData(
        SimulationResultDto selectedResult,
        List<AlgorithmComparisonDto> comparisonResults,
        DashboardCharts charts) {
}
