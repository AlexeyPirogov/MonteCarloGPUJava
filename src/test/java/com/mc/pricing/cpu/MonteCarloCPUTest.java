package com.mc.pricing.cpu;

import com.mc.generator.Utils;
import com.mc.pricing.params.BarrierOptionParams;
import com.mc.pricing.params.EurOptionParams;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MonteCarloCPUTest {

    private final MonteCarloCPU mcCPU = new MonteCarloCPU();

    @Test
    public void priceEurCallOptionsPar() {
        int iterations = 1_000_000;
        final float[] rnd = Utils.getRandomArray(iterations);
        //Stock price:50, Strike price:45, time to expiry 80d, risk-free rate:2%, vol: 30%. Expected result 6.02
        List<Float> results = mcCPU.priceEurCallOptionsPar(Collections.singletonList(
                new EurOptionParams(50, 45, 0.02f, 0.3f, 80f/365)), iterations, rnd);
        assertEquals(6.02, (double) results.get(0), 0.01);
    }

    @Test
    public void priceEurCallOption() {
        int iterations = 1_000_000;
        final float[] rnd = Utils.getRandomArray(iterations);
        //Stock price:50, Strike price:45, time to expiry 80d, risk-free rate:2%, vol: 30%. Expected result 6.02
        float price = mcCPU.priceEurCallOption(50, 45, 0.02f, 0.3f, 80f/365, iterations, rnd);
        assertEquals(6.02, (double) price, 0.01);
    }

    @Test
    public void priceBarrierOptionsPar() {
        int tNum = 200;
        int mcIterations = 1_000_000;
        final float[][] rnd = Utils.get2dRandomArray(mcIterations, tNum);
        List<Float> prices = mcCPU.priceBarrierOptionsPar(Collections.singletonList(
                new BarrierOptionParams(50, 50, 40, 0.3f, 0.1f, 1)), tNum, mcIterations, rnd);
        assertEquals(7.8360, prices.get(0), 0.1);
    }

    @Test
    public void priceBarrierOption() {
        int tNum = 200;
        int mcIterations = 1_000_000;
        final float[][] rnd = Utils.get2dRandomArray(mcIterations, tNum);
        float price = mcCPU.priceBarrierDownOutOption(50, 50, 40, 0.3f, 0.1f, 1, tNum, mcIterations, rnd);
        assertEquals(7.8360, price, 0.1);
    }
}