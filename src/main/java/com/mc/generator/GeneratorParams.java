package com.mc.generator;

public class GeneratorParams {
    private final int n;
    private final float s0Min;
    private final float s0Max;
    private final float stdDevMin;
    private final float stdDevMax;
    private final float xDeltaMin;
    private final float xDeltaMax;
    private final int t;
    private final float r;

    public GeneratorParams(int n, float s0Min, float s0Max, float stdDevMin, float stdDevMax, float xDeltaMin,
                           float xDeltaMax, int t, float r) {
        this.n = n;
        this.s0Min = s0Min;
        this.s0Max = s0Max;
        this.stdDevMin = stdDevMin;
        this.stdDevMax = stdDevMax;
        this.xDeltaMin = xDeltaMin;
        this.xDeltaMax = xDeltaMax;
        this.t = t;
        this.r = r;
    }

    public int getN() {
        return n;
    }

    public float getS0Min() {
        return s0Min;
    }

    public float getS0Max() {
        return s0Max;
    }

    public float getStdDevMin() {
        return stdDevMin;
    }

    public float getStdDevMax() {
        return stdDevMax;
    }

    public float getxDeltaMin() {
        return xDeltaMin;
    }

    public float getxDeltaMax() {
        return xDeltaMax;
    }

    public int getT() {
        return t;
    }

    public float getR() {
        return r;
    }
}
