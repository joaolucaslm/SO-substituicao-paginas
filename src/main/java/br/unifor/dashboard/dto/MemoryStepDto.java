package br.unifor.dashboard.dto;

import br.unifor.dashboard.model.SimulationEventType;
import java.util.List;

public class MemoryStepDto {
    private final int step;
    private final int referencedPage;
    private final List<Integer> memoryFrames;
    private final boolean hit;
    private final boolean fault;
    private final Integer removedPage;
    private final SimulationEventType eventType;
    private final ClockStateDto clockState;

    public MemoryStepDto(int step, int referencedPage, List<Integer> memoryFrames, boolean hit, boolean fault,
                         Integer removedPage, SimulationEventType eventType, ClockStateDto clockState) {
        this.step = step;
        this.referencedPage = referencedPage;
        this.memoryFrames = memoryFrames;
        this.hit = hit;
        this.fault = fault;
        this.removedPage = removedPage;
        this.eventType = eventType;
        this.clockState = clockState;
    }

    public int getStep() {
        return step;
    }

    public int getReferencedPage() {
        return referencedPage;
    }

    public List<Integer> getMemoryFrames() {
        return memoryFrames;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isFault() {
        return fault;
    }

    public Integer getRemovedPage() {
        return removedPage;
    }

    public SimulationEventType getEventType() {
        return eventType;
    }

    public ClockStateDto getClockState() {
        return clockState;
    }

    public String getEventLabel() {
        return switch (eventType) {
            case HIT -> "Hit";
            case FAULT -> "Fault";
            case REPLACEMENT -> "Substituicao";
        };
    }
}
