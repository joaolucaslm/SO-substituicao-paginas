package br.unifor.dashboard.service;

import br.unifor.dashboard.dto.SimulationDashboardDto;
import br.unifor.dashboard.dto.SimulationRequestDto;
import br.unifor.dashboard.util.PageSequenceParser;
import br.unifor.dashboard.view.DashboardCharts;
import br.unifor.dashboard.view.SimulationDashboardViewData;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SimulationDashboardViewService {
    private final SimulationFacadeService simulationFacadeService;
    private final DashboardChartService dashboardChartService;

    public SimulationDashboardViewService(
            SimulationFacadeService simulationFacadeService,
            DashboardChartService dashboardChartService) {
        this.simulationFacadeService = simulationFacadeService;
        this.dashboardChartService = dashboardChartService;
    }

    public SimulationDashboardViewData loadDashboard(
            int frameCount,
            String pageSequence,
            List<String> comparisonAlgorithms,
            String selectedAlgorithm) {
        int[] pages = PageSequenceParser.parse(pageSequence);
        List<String> selectedComparisons = comparisonAlgorithms == null
                ? Collections.emptyList()
                : new ArrayList<>(comparisonAlgorithms);

        SimulationRequestDto request = new SimulationRequestDto(frameCount, pages, selectedComparisons, selectedAlgorithm);
        SimulationDashboardDto dashboard = simulationFacadeService.execute(request);
        DashboardCharts charts = dashboardChartService.buildCharts(dashboard.getSelectedResult(), dashboard.getComparison());

        return new SimulationDashboardViewData(
                dashboard.getSelectedResult(),
                dashboard.getComparison(),
                charts);
    }
}
