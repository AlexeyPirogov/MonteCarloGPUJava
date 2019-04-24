package com.mc.generator;

import com.mc.pricing.params.BarrierBatchArrayOptionsParams;
import com.mc.pricing.params.BarrierOptionParams;
import com.mc.pricing.params.BatchArrayOptionsParams;
import com.mc.pricing.params.EurOptionParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OptionGenerator {

    public List<EurOptionParams> generateEurOptionParams(GeneratorParams param) {
        Random rnd = new Random();
        List<EurOptionParams> result = new ArrayList<>(param.getN());
        for (int i = 0; i < param.getN(); i++) {
            float s0 = genRandomWithInterval(rnd, param.getS0Min(), param.getS0Max());
            float stdDev = genRandomWithInterval(rnd, param.getStdDevMin(), param.getStdDevMax());
            float x = s0 + (param.getxDeltaMax() - param.getxDeltaMin()) * rnd.nextFloat();
            result.add(new EurOptionParams(s0, x, param.getR(), stdDev, param.getT()));
        }
        return result;
    }

    public BatchArrayOptionsParams toBatchArrayOptionsParams(List<? extends EurOptionParams> optionParams) {
        int size = optionParams.size();
        float[] s0s = new float[size];
        float[] xs = new float[size];
        float[] stdDevs = new float[size];
        for (int i = 0; i < optionParams.size(); i++) {
            EurOptionParams param = optionParams.get(i);
            s0s[i] = param.getS0();
            xs[i] = param.getX();
            stdDevs[i] = param.getStdDev();
        }
        return new BatchArrayOptionsParams(s0s, xs, stdDevs, optionParams.get(0).getR(), optionParams.get(0).getT());
    }

    public BarrierBatchArrayOptionsParams toBarrierBatchArrayOptionsParams(List<BarrierOptionParams> optionParams) {
        float[] bs = new float[optionParams.size()];
        for (int i = 0; i < optionParams.size(); i++) {
            BarrierOptionParams param = optionParams.get(i);
            bs[i] = param.getB();
        }

        BatchArrayOptionsParams params = toBatchArrayOptionsParams(optionParams);
        return new BarrierBatchArrayOptionsParams(params.getS0s(), params.getXs(), bs, params.getStdDevs(), params.getR(), params.getT());
    }

    public List<BarrierOptionParams> generateBarrierOptionParams(GeneratorParams param) {
        Random rnd = new Random();
        List<BarrierOptionParams> result = new ArrayList<>(param.getN());
        for (int i = 0; i < param.getN(); i++) {
            float s0 = genRandomWithInterval(rnd, param.getS0Min(), param.getS0Max());
            float stdDev = genRandomWithInterval(rnd, param.getStdDevMin(), param.getStdDevMax());
            float x = s0 + (param.getxDeltaMax() - param.getxDeltaMin()) * rnd.nextFloat();
            float b = s0 - 5;
            result.add(new BarrierOptionParams(s0, x, b, stdDev, param.getR(), param.getT()));
        }
        return result;
    }

    private float genRandomWithInterval(Random rnd, float min, float max) {
        return min + (max - min) * rnd.nextFloat();
    }
}
