package com.mc.pricing.gpu.gpu;

import com.aparapi.Range;
import com.mc.pricing.params.BatchArrayOptionsParams;
import org.junit.Test;

import static com.mc.generator.Utils.getRandomArray;
import static org.junit.Assert.assertEquals;

public class BaseMCOptionKernelTest {

    @Test
    public void testEurCallOption() {
        int iterations = 1_000_000;
        final float[] rnd = getRandomArray(iterations);

        //Stock price:50, Strike price:45, time to expiry 80d, risk-free rate:2%, vol: 30%. Expected result 6.02
        BatchArrayOptionsParams params = new BatchArrayOptionsParams(new float[]{50f}, new float[]{45f}, new float[]{0.3f}, 0.02f, 80f/365);

        BaseMCOptionKernel kernel = new BaseMCOptionKernel(params, iterations, rnd);
        kernel.execute(Range.create(iterations));
        kernel.setOperation(1);
        kernel.execute(Range.create(1));
        kernel.dispose();
        float price =  kernel.result[0];
        assertEquals(6.02, (double) price, 0.01);
    }
}