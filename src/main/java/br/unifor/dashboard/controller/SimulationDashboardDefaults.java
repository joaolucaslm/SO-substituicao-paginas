package br.unifor.dashboard.controller;

import br.unifor.dashboard.model.SimulationAlgorithm;
import jakarta.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class SimulationDashboardDefaults {
    private static final int DEFAULT_FRAME_COUNT = 3;
    private static final String DEFAULT_PAGE_SEQUENCE = "7 0 1 2 0 3 0 4 2 3 0 3 2";
    private static final String DEFAULT_SELECTED_ALGORITHM = SimulationAlgorithm.CLOCK.getKey();

    private SimulationDashboardDefaults() {
    }

    public static int defaultFrameCount() {
        return DEFAULT_FRAME_COUNT;
    }

    public static String defaultPageSequence() {
        return DEFAULT_PAGE_SEQUENCE;
    }

    public static String defaultSelectedAlgorithm() {
        return DEFAULT_SELECTED_ALGORITHM;
    }

    public static List<String> defaultComparisonAlgorithms() {
        return new ArrayList<>(Arrays.asList(
                SimulationAlgorithm.FIFO.getKey(),
                SimulationAlgorithm.LRU.getKey(),
                SimulationAlgorithm.CLOCK.getKey(),
                SimulationAlgorithm.OPTIMAL.getKey()));
    }

    public static List<SelectItem> algorithmOptions() {
        List<SelectItem> items = new ArrayList<>();
        for (SimulationAlgorithm algorithm : SimulationAlgorithm.values()) {
            items.add(new SelectItem(algorithm.getKey(), algorithm.getLabel()));
        }
        return items;
    }
}
