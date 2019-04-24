package com.mc.pricing.params;

public class BarrierBatchArrayOptionsParams extends BatchArrayOptionsParams {
    private final float[] bs;

    public BarrierBatchArrayOptionsParams(float[] s0s, float[] xs, float[] bs, float[] stdDevs, float r, float t) {
        super(s0s, xs, stdDevs, r, t);
        this.bs = bs;
    }

    public float[] getBs() {
        return bs;
    }
}
