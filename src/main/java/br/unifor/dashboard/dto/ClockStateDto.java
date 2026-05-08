package br.unifor.dashboard.dto;

import java.util.List;

public class ClockStateDto {
    private final List<Boolean> referenceBits;
    private final int pointerIndex;
    private final Integer selectedFrameIndex;
    private final boolean secondChanceUsed;

    public ClockStateDto(List<Boolean> referenceBits, int pointerIndex, Integer selectedFrameIndex, boolean secondChanceUsed) {
        this.referenceBits = referenceBits;
        this.pointerIndex = pointerIndex;
        this.selectedFrameIndex = selectedFrameIndex;
        this.secondChanceUsed = secondChanceUsed;
    }

    public List<Boolean> getReferenceBits() {
        return referenceBits;
    }

    public int getPointerIndex() {
        return pointerIndex;
    }

    public Integer getSelectedFrameIndex() {
        return selectedFrameIndex;
    }

    public boolean isSecondChanceUsed() {
        return secondChanceUsed;
    }
}
