package br.unifor.dashboard.controller;

import br.unifor.dashboard.dto.AlgorithmComparisonDto;
import br.unifor.dashboard.dto.ClockStateDto;
import br.unifor.dashboard.dto.MemoryStepDto;
import br.unifor.dashboard.dto.SimulationResultDto;
import br.unifor.dashboard.model.SimulationAlgorithm;
import br.unifor.dashboard.service.SimulationDashboardViewService;
import br.unifor.dashboard.view.SimulationDashboardViewData;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.SelectItem;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component("simulationDashboardController")
@SessionScope
public class SimulationDashboardController implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DecimalFormat PERCENT_FORMAT = new DecimalFormat("0.0");

    private final SimulationDashboardViewService simulationDashboardViewService;

    private int frameCount;
    private String pageSequence;
    private String selectedAlgorithm;
    private List<String> comparisonAlgorithms;
    private List<SelectItem> algorithmOptions;
    private SimulationResultDto selectedResult;
    private List<AlgorithmComparisonDto> comparisonResults;
    private String faultChartModel;
    private String distributionChartModel;
    private String rankingChartModel;

    public SimulationDashboardController(SimulationDashboardViewService simulationDashboardViewService) {
        this.simulationDashboardViewService = simulationDashboardViewService;
    }

    @PostConstruct
    public void init() {
        frameCount = SimulationDashboardDefaults.defaultFrameCount();
        pageSequence = SimulationDashboardDefaults.defaultPageSequence();
        selectedAlgorithm = SimulationDashboardDefaults.defaultSelectedAlgorithm();
        comparisonAlgorithms = SimulationDashboardDefaults.defaultComparisonAlgorithms();
        algorithmOptions = SimulationDashboardDefaults.algorithmOptions();
        executeSimulation();
    }

    public void executeSimulation() {
        try {
            SimulationDashboardViewData viewData = simulationDashboardViewService.loadDashboard(
                    frameCount,
                    pageSequence,
                    comparisonAlgorithms,
                    selectedAlgorithm);
            applyViewData(viewData);
            addMessage(FacesMessage.SEVERITY_INFO, "Simulacao concluida", "Os dados do dashboard foram atualizados.");
        } catch (IllegalArgumentException exception) {
            addMessage(FacesMessage.SEVERITY_ERROR, "Entrada invalida", exception.getMessage());
        }
    }

    private void applyViewData(SimulationDashboardViewData viewData) {
        selectedResult = viewData.selectedResult();
        comparisonResults = viewData.comparisonResults();
        faultChartModel = viewData.charts().faultChartModel();
        distributionChartModel = viewData.charts().distributionChartModel();
        rankingChartModel = viewData.charts().rankingChartModel();
    }

    public List<SelectItem> getAlgorithmOptions() {
        return algorithmOptions;
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
        if (clockState == null || clockState.getReferenceBits() == null || index < 0
                || index >= clockState.getReferenceBits().size()) {
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

    public String getFaultChartModel() {
        return faultChartModel;
    }

    public String getDistributionChartModel() {
        return distributionChartModel;
    }

    public String getRankingChartModel() {
        return rankingChartModel;
    }
}
