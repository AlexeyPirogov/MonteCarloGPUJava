package com.mc.pricing.params;

public class BatchArrayOptionsParams {
    private final float[] s0s;
    private final float[] xs;
    private final float[] stdDevs;
    private final float r;
    private final float t;

    public BatchArrayOptionsParams(float[] s0s, float[] xs, float[] stdDevs, float r, float t) {
        this.s0s = s0s;
        this.xs = xs;
        this.stdDevs = stdDevs;
        this.r = r;
        this.t = t;
    }

    public float[] getS0s() {
        return s0s;
    }

    public float[] getXs() {
        return xs;
    }

    public float[] getStdDevs() {
        return stdDevs;
    }

    public float getR() {
        return r;
    }

    public float getT() {
        return t;
    }
}
