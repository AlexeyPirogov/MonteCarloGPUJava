package com.mc.pricing.gpu.gpu;

import com.aparapi.Kernel;
import com.mc.pricing.params.BatchArrayOptionsParams;

public class BaseMCOptionKernel extends Kernel {
    protected final float[] s0s;
    protected final float[] xs;
    protected final float[] stdDevs;
    protected final float r;
    protected final float t;
    protected final int iterations;

    protected final float[] rnd;
    protected final float[] mcResults;
    public final float[] result;

    public int operation = 0;

    public <T extends BatchArrayOptionsParams> BaseMCOptionKernel(T params, int iterations, float[] rnd) {
        this.s0s = params.getS0s();
        this.xs = params.getXs();
        this.stdDevs = params.getStdDevs();
        this.r = params.getR();
        this.t = params.getT();
        this.rnd = rnd;
        this.iterations = iterations;

        mcResults = new float[iterations * s0s.length];
        result = new float[s0s.length];
    }

    @Override
    public void run() {
        int mcIter = getGlobalId();
        int stockIdx = mcIter / iterations;
        if (operation == 0) {
            calcMc(mcIter, stockIdx);
        }

        if (operation == 1) {
            aggregate(mcIter);
        }
    }

    protected void calcMc(int mcIter, int stockIdx) {
        float eps = rnd[mcIter % iterations];
        float stdDev = stdDevs[stockIdx];
        float s0 = s0s[stockIdx];
        float x = xs[stockIdx];

        float pow = (r - 0.5f * stdDev * stdDev) * t + stdDev * sqrt(t) * eps;
        mcResults[mcIter] = max(s0 * exp(pow) - x, 0.0f);
    }

    protected void aggregate(int stockIdx) {
        float sum = 0;
        for (int i = 0; i < iterations; i++) {
            int index = stockIdx * iterations + i;
            sum += mcResults[index];
        }
        float sTr = sum / iterations;
        result[stockIdx] = sTr * exp(-r * t);
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
}