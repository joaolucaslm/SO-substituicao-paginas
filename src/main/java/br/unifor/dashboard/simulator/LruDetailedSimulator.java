package br.unifor.dashboard.simulator;

import br.unifor.dashboard.dto.MemoryStepDto;
import br.unifor.dashboard.dto.SimulationResultDto;
import br.unifor.dashboard.model.SimulationAlgorithm;
import br.unifor.dashboard.model.SimulationEventType;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LruDetailedSimulator extends AbstractDetailedSimulator {

    @Override
    public SimulationAlgorithm getAlgorithm() {
        return SimulationAlgorithm.LRU;
    }

    @Override
    public SimulationResultDto simulate(int[] pages, int frameCount) {
        validateFrameCount(frameCount);

        Map<Integer, Integer> memory = new LinkedHashMap<>(frameCount, 0.75f, true);
        List<MemoryStepDto> steps = new ArrayList<>();

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];

            if (memory.containsKey(page)) {
                memory.get(page);
                steps.add(step(i, page, toFrames(memory, frameCount), true, null, SimulationEventType.HIT));
                continue;
            }

            Integer removedPage = null;
            SimulationEventType eventType = SimulationEventType.FAULT;
            if (memory.size() == frameCount) {
                removedPage = memory.keySet().iterator().next();
                memory.remove(removedPage);
                eventType = SimulationEventType.REPLACEMENT;
            }

            memory.put(page, page);
            steps.add(step(i, page, toFrames(memory, frameCount), false, removedPage, eventType));
        }

        return buildResult(getAlgorithm(), steps, pages);
    }

    private Integer[] toFrames(Map<Integer, Integer> memory, int frameCount) {
        Integer[] frames = new Integer[frameCount];
        int index = 0;
        for (Integer page : memory.keySet()) {
            frames[index++] = page;
        }
        return frames;
    }
}
