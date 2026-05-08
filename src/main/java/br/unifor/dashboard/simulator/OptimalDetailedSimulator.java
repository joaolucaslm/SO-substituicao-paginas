package br.unifor.dashboard.simulator;

import br.unifor.dashboard.dto.MemoryStepDto;
import br.unifor.dashboard.dto.SimulationResultDto;
import br.unifor.dashboard.model.SimulationAlgorithm;
import br.unifor.dashboard.model.SimulationEventType;
import java.util.ArrayList;
import java.util.List;

public class OptimalDetailedSimulator extends AbstractDetailedSimulator {

    @Override
    public SimulationAlgorithm getAlgorithm() {
        return SimulationAlgorithm.OPTIMAL;
    }

    @Override
    public SimulationResultDto simulate(int[] pages, int frameCount) {
        validateFrameCount(frameCount);

        Integer[] frames = new Integer[frameCount];
        List<MemoryStepDto> steps = new ArrayList<>();

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            if (contains(frames, page)) {
                steps.add(step(i, page, frames, true, null, SimulationEventType.HIT));
                continue;
            }

            Integer removedPage = null;
            int freeIndex = firstEmpty(frames);
            if (freeIndex >= 0) {
                frames[freeIndex] = page;
                steps.add(step(i, page, frames, false, null, SimulationEventType.FAULT));
                continue;
            }

            int replacementIndex = farthestFutureIndex(frames, pages, i + 1);
            removedPage = frames[replacementIndex];
            frames[replacementIndex] = page;
            steps.add(step(i, page, frames, false, removedPage, SimulationEventType.REPLACEMENT));
        }

        return buildResult(getAlgorithm(), steps, pages);
    }

    private boolean contains(Integer[] frames, int page) {
        for (Integer frame : frames) {
            if (frame != null && frame == page) {
                return true;
            }
        }
        return false;
    }

    private int firstEmpty(Integer[] frames) {
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private int farthestFutureIndex(Integer[] frames, int[] pages, int startIndex) {
        int selectedIndex = 0;
        int farthestUse = -1;

        for (int i = 0; i < frames.length; i++) {
            int nextUse = Integer.MAX_VALUE;
            for (int j = startIndex; j < pages.length; j++) {
                if (frames[i] != null && frames[i] == pages[j]) {
                    nextUse = j;
                    break;
                }
            }

            if (nextUse > farthestUse) {
                farthestUse = nextUse;
                selectedIndex = i;
            }
        }

        return selectedIndex;
    }
}
