package com.mc.pricing.gpu.gpu;

import com.mc.pricing.params.BarrierBatchArrayOptionsParams;

public class MCBarrierOptionKernel extends BaseMCOptionKernel {
    private final float[] bs;
    private final int tNum;

    public MCBarrierOptionKernel(BarrierBatchArrayOptionsParams params, int tNum, int iterations, float[] rnd) {
        super(params, iterations, rnd);
        this.tNum = tNum;
        this.bs = params.getBs();
    }

    @Override
    protected void calcMc(int mcIter, int stockIdx) {
        float stdDev = stdDevs[stockIdx];
        float x = xs[stockIdx];
        float b = bs[stockIdx];

        float dt = t / tNum;
        float c1 = (r - stdDev * stdDev / 2) * dt;
        float c2 = stdDev * sqrt(dt);

        int i = 0;
        float curS = s0s[stockIdx];
        while (i < tNum && curS > b) {
            int idx = i * iterations + mcIter % iterations;
            float eps = rnd[idx];
            curS = curS * exp(c1 + c2 * eps);
            i++;
        }

        mcResults[mcIter] = curS > x ? curS - x : 0.0f;
    }

}