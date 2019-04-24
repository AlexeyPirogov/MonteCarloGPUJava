package com.mc.pricing.params;

public class BarrierOptionParams extends EurOptionParams {

    private final float b;

    public BarrierOptionParams(float s0, float x, float b, float stdDev, float r, int t) {
        super(s0, x, r, stdDev, t);
        this.b = b;
    }

    public float getB() {
        return b;
    }
}
