package com.mc.pricing.params;

public class EurOptionParams {

    private final float s0, r, stdDev, x;
    private final float t;

    public EurOptionParams(float s0, float x, float r, float stdDev, float t) {
        this.s0 = s0;
        this.r = r;
        this.stdDev = stdDev;
        this.t = t;
        this.x = x;
    }

    public float getS0() {
        return s0;
    }

    public float getR() {
        return r;
    }

    public float getStdDev() {
        return stdDev;
    }

    public float getT() {
        return t;
    }

    public float getX() {
        return x;
    }

}
