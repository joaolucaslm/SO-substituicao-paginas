package br.unifor.dashboard.simulator;

import br.unifor.dashboard.dto.ClockStateDto;
import br.unifor.dashboard.dto.MemoryStepDto;
import br.unifor.dashboard.dto.SimulationResultDto;
import br.unifor.dashboard.model.SimulationAlgorithm;
import br.unifor.dashboard.model.SimulationEventType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClockDetailedSimulator extends AbstractDetailedSimulator {

    @Override
    public SimulationAlgorithm getAlgorithm() {
        return SimulationAlgorithm.CLOCK;
    }

    @Override
    public SimulationResultDto simulate(int[] pages, int frameCount) {
        validateFrameCount(frameCount);

        Integer[] frames = new Integer[frameCount];
        boolean[] referenceBits = new boolean[frameCount];
        int pointer = 0;
        List<MemoryStepDto> steps = new ArrayList<>();

        for (int i = 0; i < pages.length; i++) {
            int page = pages[i];
            int existingIndex = indexOf(frames, page);

            if (existingIndex >= 0) {
                referenceBits[existingIndex] = true;
                steps.add(step(i, page, frames, true, null, SimulationEventType.HIT,
                        clockState(referenceBits, pointer, existingIndex, false)));
                continue;
            }

            boolean secondChanceUsed = false;
            while (frames[pointer] != null && referenceBits[pointer]) {
                secondChanceUsed = true;
                referenceBits[pointer] = false;
                pointer = (pointer + 1) % frameCount;
            }

            Integer removedPage = frames[pointer];
            SimulationEventType eventType = removedPage == null ? SimulationEventType.FAULT : SimulationEventType.REPLACEMENT;
            int selectedFrame = pointer;
            frames[pointer] = page;
            referenceBits[pointer] = true;
            pointer = (pointer + 1) % frameCount;

            steps.add(step(i, page, frames, false, removedPage, eventType,
                    clockState(referenceBits, pointer, selectedFrame, secondChanceUsed)));
        }

        return buildResult(getAlgorithm(), steps, pages);
    }

    private ClockStateDto clockState(boolean[] referenceBits, int pointerIndex, Integer selectedFrame, boolean secondChanceUsed) {
        List<Boolean> bits = new ArrayList<>(referenceBits.length);
        for (boolean bit : referenceBits) {
            bits.add(bit);
        }
        return new ClockStateDto(bits, pointerIndex, selectedFrame, secondChanceUsed);
    }

    private int indexOf(Integer[] frames, int page) {
        return Arrays.asList(frames).indexOf(page);
    }
}
