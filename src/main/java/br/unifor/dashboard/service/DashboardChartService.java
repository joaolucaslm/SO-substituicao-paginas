package br.unifor.dashboard.service;

import br.unifor.dashboard.dto.AlgorithmComparisonDto;
import br.unifor.dashboard.dto.SimulationResultDto;
import br.unifor.dashboard.view.DashboardCharts;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class DashboardChartService {
    private final ObjectMapper objectMapper;

    public DashboardChartService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public DashboardCharts buildCharts(SimulationResultDto selectedResult, List<AlgorithmComparisonDto> comparisonResults) {
        if (selectedResult == null || comparisonResults == null || comparisonResults.isEmpty()) {
            return new DashboardCharts(emptyChart(), emptyChart(), emptyChart());
        }

        return new DashboardCharts(
                buildFaultChart(comparisonResults),
                buildDistributionChart(selectedResult),
                buildRankingChart(comparisonResults));
    }

    private String buildFaultChart(List<AlgorithmComparisonDto> comparisonResults) {
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (AlgorithmComparisonDto result : comparisonResults) {
            labels.add(result.getAlgorithmLabel());
            values.add(result.getPageFaults());
        }

        return toJsonChartConfig("bar", labels, List.of(createDataSet(
                "Page Faults",
                values,
                List.of("#ef4444", "#f97316", "#0ea5e9", "#22c55e"))));
    }

    private String buildDistributionChart(SimulationResultDto selectedResult) {
        List<Number> values = new ArrayList<>();
        values.add(selectedResult.getMetrics().getHits());
        values.add(selectedResult.getMetrics().getPageFaults());

        return toJsonChartConfig("doughnut", List.of("Hits", "Faults"), List.of(createDataSet(
                null,
                values,
                List.of("#22c55e", "#ef4444"))));
    }

    private String buildRankingChart(List<AlgorithmComparisonDto> comparisonResults) {
        List<Object> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (AlgorithmComparisonDto result : comparisonResults) {
            labels.add("#" + result.getRanking() + " " + result.getAlgorithmKey());
            values.add(result.getHits());
        }

        return toJsonChartConfig("bar", labels, List.of(createDataSet(
                "Hits",
                values,
                List.of("#1d4ed8", "#0f766e", "#7c3aed", "#475569"))));
    }

    private Map<String, Object> createDataSet(String label, List<? extends Object> values, List<String> colors) {
        Map<String, Object> dataSet = new LinkedHashMap<>();
        if (label != null) {
            dataSet.put("label", label);
        }
        dataSet.put("data", values);
        dataSet.put("backgroundColor", colors);
        return dataSet;
    }

    private String toJsonChartConfig(String type, List<String> labels, List<Map<String, Object>> datasets) {
        Map<String, Object> config = new LinkedHashMap<>();
        config.put("type", type);

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("labels", labels);
        data.put("datasets", datasets);
        config.put("data", data);

        return writeJson(config);
    }

    private String emptyChart() {
        return writeJson(Map.of(
                "type", "bar",
                "data", Map.of("labels", List.of(), "datasets", List.of())));
    }

    private String writeJson(Map<String, Object> value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Nao foi possivel gerar os graficos do dashboard.", exception);
        }
    }
}
