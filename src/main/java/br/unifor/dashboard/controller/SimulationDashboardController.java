package br.unifor.dashboard.controller;

import br.unifor.dashboard.dto.AlgorithmComparisonDto;
import br.unifor.dashboard.dto.ClockStateDto;
import br.unifor.dashboard.dto.MemoryStepDto;
import br.unifor.dashboard.dto.SimulationDashboardDto;
import br.unifor.dashboard.dto.SimulationRequestDto;
import br.unifor.dashboard.dto.SimulationResultDto;
import br.unifor.dashboard.model.SimulationAlgorithm;
import br.unifor.dashboard.service.SimulationFacadeService;
import br.unifor.dashboard.util.PageSequenceParser;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component("simulationDashboardController")
@SessionScope
public class SimulationDashboardController implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("0.0");

    private final SimulationFacadeService simulationFacadeService;

    private int frameCount;
    private String pageSequence;
    private String selectedAlgorithm;
    private List<String> comparisonAlgorithms;
    private List<SelectItem> algorithmOptions;
    private SimulationResultDto selectedResult;
    private List<AlgorithmComparisonDto> comparisonResults;
    private BarChartModel faultChartModel;
    private DonutChartModel distributionChartModel;
    private BarChartModel rankingChartModel;

    public SimulationDashboardController(SimulationFacadeService simulationFacadeService) {
        this.simulationFacadeService = simulationFacadeService;
    }

    @PostConstruct
    public void init() {
        frameCount = 3;
        pageSequence = "7 0 1 2 0 3 0 4 2 3 0 3 2";
        selectedAlgorithm = SimulationAlgorithm.CLOCK.getKey();
        comparisonAlgorithms = new ArrayList<>(Arrays.asList(
                SimulationAlgorithm.FIFO.getKey(),
                SimulationAlgorithm.LRU.getKey(),
                SimulationAlgorithm.CLOCK.getKey(),
                SimulationAlgorithm.OPTIMAL.getKey()));
        algorithmOptions = createAlgorithmOptions();
        executeSimulation();
    }

    public void executeSimulation() {
        try {
            int[] pages = PageSequenceParser.parse(pageSequence);
            List<String> selectedComparisons = comparisonAlgorithms == null
                    ? Collections.emptyList()
                    : new ArrayList<>(comparisonAlgorithms);
            SimulationRequestDto request = new SimulationRequestDto(frameCount, pages, selectedComparisons, selectedAlgorithm);
            SimulationDashboardDto dashboard = simulationFacadeService.execute(request);

            selectedResult = dashboard.getSelectedResult();
            comparisonResults = dashboard.getComparison();
            buildCharts();
            addMessage(FacesMessage.SEVERITY_INFO, "Simulacao concluida", "Os dados do dashboard foram atualizados.");
        } catch (IllegalArgumentException exception) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Entrada invalida", exception.getMessage());
        }
    }

    public List<SelectItem> getAlgorithmOptions() {
        return algorithmOptions;
    }

    private List<SelectItem> createAlgorithmOptions() {
        List<SelectItem> items = new ArrayList<>();
        for (SimulationAlgorithm algorithm : SimulationAlgorithm.values()) {
            items.add(new SelectItem(algorithm.getKey(), algorithm.getLabel()));
        }
        return items;
    }

    public String formatRate(double value) {
        return PERCENT_FORMAT.format(value) + "%";
    }

    public String formatFrame(Integer frame) {
        return frame == null ? "-" : String.valueOf(frame);
    }

    public String resolveEventClass(MemoryStepDto step) {
        return switch (step.getEventType()) {
            case HIT -> "badge-hit";
            case FAULT -> "badge-fault";
            case REPLACEMENT -> "badge-replacement";
        };
    }

    public String resolveEventIcon(MemoryStepDto step) {
        return switch (step.getEventType()) {
            case HIT -> "pi pi-check-circle";
            case FAULT -> "pi pi-exclamation-circle";
            case REPLACEMENT -> "pi pi-refresh";
        };
    }

    public String resolveRankingClass(int ranking) {
        if (ranking == 1) {
            return "ranking-first";
        }
        if (ranking == 2) {
            return "ranking-second";
        }
        return "ranking-default";
    }

    public boolean isClockSelected() {
        return selectedResult != null && selectedResult.getAlgorithm() == SimulationAlgorithm.CLOCK;
    }

    public ClockStateDto getClockState() {
        return selectedResult == null ? null : selectedResult.getLatestClockState();
    }

    public List<Integer> getClockFrames() {
        if (!isClockSelected() || selectedResult.getSteps() == null || selectedResult.getSteps().isEmpty()) {
            return Collections.emptyList();
        }
        return selectedResult.getSteps().get(selectedResult.getSteps().size() - 1).getMemoryFrames();
    }

    public String getClockReferenceBitLabel(int index) {
        ClockStateDto clockState = getClockState();
        if (clockState == null || clockState.getReferenceBits() == null || index < 0 || index >= clockState.getReferenceBits().size()) {
            return "0";
        }
        return Boolean.TRUE.equals(clockState.getReferenceBits().get(index)) ? "1" : "0";
    }

    public String getClockPointerLabel() {
        ClockStateDto clockState = getClockState();
        if (clockState == null) {
            return "-";
        }
        return "quadro " + (clockState.getPointerIndex() + 1);
    }

    public String getClockSecondChanceLabel() {
        ClockStateDto clockState = getClockState();
        if (clockState == null) {
            return "-";
        }
        return clockState.isSecondChanceUsed() ? "utilizada" : "nao utilizada";
    }

    public String resolveClockSlotClass(int index) {
        ClockStateDto clockState = getClockState();
        if (clockState == null) {
            return "clock-slot";
        }

        if (clockState.getSelectedFrameIndex() != null && clockState.getSelectedFrameIndex() == index) {
            return "clock-slot selected";
        }
        if (clockState.getPointerIndex() == index) {
            return "clock-slot pointer";
        }
        return "clock-slot";
    }

    private void buildCharts() {
        if (selectedResult == null || comparisonResults == null || comparisonResults.isEmpty()) {
            return;
        }
        buildFaultChart();
        buildDistributionChart();
        buildRankingChart();
    }

    private void buildFaultChart() {
        faultChartModel = new BarChartModel();
        ChartData data = new ChartData();
        BarChartDataSet dataSet = new BarChartDataSet();
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (AlgorithmComparisonDto result : comparisonResults) {
            labels.add(result.getAlgorithmLabel());
            values.add(result.getPageFaults());
        }

        dataSet.setLabel("Page Faults");
        dataSet.setData(values);
        dataSet.setBackgroundColor(List.of("#ef4444", "#f97316", "#0ea5e9", "#22c55e"));
        data.addChartDataSet(dataSet);
        data.setLabels(labels);
        faultChartModel.setData(data);
    }

    private void buildDistributionChart() {
        distributionChartModel = new DonutChartModel();
        ChartData data = new ChartData();
        DonutChartDataSet dataSet = new DonutChartDataSet();
        List<Number> values = new ArrayList<>();
        values.add(selectedResult.getMetrics().getHits());
        values.add(selectedResult.getMetrics().getPageFaults());
        dataSet.setData(values);
        dataSet.setBackgroundColor(List.of("#22c55e", "#ef4444"));
        data.addChartDataSet(dataSet);
        data.setLabels(List.of("Hits", "Faults"));
        distributionChartModel.setData(data);
    }

    private void buildRankingChart() {
        rankingChartModel = new BarChartModel();
        ChartData data = new ChartData();
        BarChartDataSet dataSet = new BarChartDataSet();
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (AlgorithmComparisonDto result : comparisonResults) {
            labels.add("#" + result.getRanking() + " " + result.getAlgorithmKey());
            values.add(result.getHits());
        }

        dataSet.setLabel("Hits");
        dataSet.setData(values);
        dataSet.setBackgroundColor(List.of("#1d4ed8", "#0f766e", "#7c3aed", "#475569"));
        data.addChartDataSet(dataSet);
        data.setLabels(labels);
        rankingChartModel.setData(data);
    }

    private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public String getPageSequence() {
        return pageSequence;
    }

    public void setPageSequence(String pageSequence) {
        this.pageSequence = pageSequence;
    }

    public String getSelectedAlgorithm() {
        return selectedAlgorithm;
    }

    public void setSelectedAlgorithm(String selectedAlgorithm) {
        this.selectedAlgorithm = selectedAlgorithm;
    }

    public List<String> getComparisonAlgorithms() {
        return comparisonAlgorithms;
    }

    public void setComparisonAlgorithms(List<String> comparisonAlgorithms) {
        this.comparisonAlgorithms = comparisonAlgorithms;
    }

    public SimulationResultDto getSelectedResult() {
        return selectedResult;
    }

    public List<AlgorithmComparisonDto> getComparisonResults() {
        return comparisonResults;
    }

    public BarChartModel getFaultChartModel() {
        return faultChartModel;
    }

    public DonutChartModel getDistributionChartModel() {
        return distributionChartModel;
    }

    public BarChartModel getRankingChartModel() {
        return rankingChartModel;
    }
}
