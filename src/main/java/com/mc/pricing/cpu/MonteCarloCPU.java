package com.mc.pricing.cpu;

import com.mc.pricing.params.BarrierOptionParams;
import com.mc.pricing.params.EurOptionParams;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.exp;
import static java.lang.Math.sqrt;

public class MonteCarloCPU {

    public List<Float> priceEurCallOptionsPar(List<? extends EurOptionParams> optionParams, int iterations, float[] rnd) {
        return optionParams.parallelStream().
                map(opt -> priceEurCallOption(opt.getS0(), opt.getX(), opt.getR(), opt.getStdDev(), opt.getT(), iterations, rnd)).
                collect(Collectors.toList());
    }

    public float priceEurCallOption(float s0, float x, float r, float stdDev, float t, int iterations, float[] rnd) {
        float totalSt = 0;
        for (int i = 0; i < iterations; i++) {
            float random = rnd[i];
            float pow = (r - 0.5f * stdDev * stdDev) * t + stdDev * (float) Math.sqrt(t) * random;
            float sT = Math.max(s0 * (float) exp(pow) - x, 0);
            totalSt += sT;
        }
        float mean = totalSt / iterations;
        return mean * (float) exp(-r * t);
    }

    public List<Float> priceBarrierOptionsPar(List<BarrierOptionParams> optionParams, int tNum, int iterations, float[][] rnd) {
        return optionParams.parallelStream().
                map(opt -> priceBarrierDownOutOption(opt.getS0(), opt.getX(), opt.getB(), opt.getStdDev(), opt.getR(),
                        opt.getT(), tNum, iterations, rnd)).
                collect(Collectors.toList());
    }

    public float priceBarrierDownOutOption(float s0, float x, float b, float stdDev, float r, float t, int tNum, int iterations, float[][] rnd) {
        float sum = 0;
        float dt = t / tNum;

        float c1 = (r - stdDev * stdDev / 2) * dt;
        float c2 = (float) (stdDev * sqrt(dt));
        for (int i = 0; i < iterations; i++) {
            float curS = s0;
            for (int j = 0; j < tNum && curS > b; j++) {
                float eps = rnd[j][i];
                curS = curS * (float) exp(c1 + c2 * eps);
            }

            sum += curS > x ? curS - x : 0.0f;
        }

        float sTr = sum / iterations;
        return sTr * (float) exp(-r * t);
    }

}
