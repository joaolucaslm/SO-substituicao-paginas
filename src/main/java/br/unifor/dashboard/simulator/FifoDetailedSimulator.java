package br.unifor.dashboard.simulator;

import br.unifor.dashboard.dto.MemoryStepDto;
import br.unifor.dashboard.dto.SimulationResultDto;
import br.unifor.dashboard.model.SimulationAlgorithm;
import br.unifor.dashboard.model.SimulationEventType;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class FifoDetailedSimulator extends AbstractDetailedSimulator {

    @Override
    public SimulationAlgorithm getAlgorithm() {
        return SimulationAlgorithm.FIFO;
    }

    @Override
    public SimulationResultDto simulate(int[] pages, int frameCount) {
        validateFrameCount(frameCount);

        Integer[] frames = new Integer[frameCount];
        Deque<Integer> queue = new ArrayDeque<>();
        List<MemoryStepDto> steps = new ArrayList<>();

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            int existingIndex = indexOf(frames, page);

            if (existingIndex >= 0) {
                steps.add(step(i, page, frames, true, null, SimulationEventType.HIT));
                continue;
            }

            Integer removedPage = null;
            int freeIndex = firstEmpty(frames);
            if (freeIndex >= 0) {
                frames[freeIndex] = page;
                queue.addLast(page);
                steps.add(step(i, page, frames, false, null, SimulationEventType.FAULT));
                continue;
            }

            removedPage = queue.removeFirst();
            int replacementIndex = indexOf(frames, removedPage);
            frames[replacementIndex] = page;
            queue.addLast(page);
            steps.add(step(i, page, frames, false, removedPage, SimulationEventType.REPLACEMENT));
        }

        return buildResult(getAlgorithm(), steps, pages);
    }

    private int indexOf(Integer[] frames, int page) {
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] != null && frames[i] == page) {
                return i;
            }
        }
        return -1;
    }

    private int firstEmpty(Integer[] frames) {
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == null) {
                return i;
            }
        }
        return -1;
    }
}
