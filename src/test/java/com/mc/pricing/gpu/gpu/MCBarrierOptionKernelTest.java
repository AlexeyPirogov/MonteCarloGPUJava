package com.mc.pricing.gpu.gpu;

import com.aparapi.Range;
import com.mc.pricing.params.BarrierBatchArrayOptionsParams;
import org.junit.Test;

import static com.mc.generator.Utils.*;
import static org.junit.Assert.*;

public class MCBarrierOptionKernelTest {

    @Test
    public void testBarrierDownOutCallOption() {
        int iterations = 1_000_000;
        int tNum = 300;
        float[][] rnd = get2dRandomArray(iterations, tNum);
        float[] rnd1d = to1dArray(rnd);

        BarrierBatchArrayOptionsParams params = new BarrierBatchArrayOptionsParams(new float[]{50f}, new float[]{50f}, new float[]{40f}, new float[]{0.3f}, 0.1f, 1);

        MCBarrierOptionKernel kernel = new MCBarrierOptionKernel(params, tNum, iterations, rnd1d);
        kernel.execute(Range.create(iterations));
        kernel.setOperation(1);
        kernel.execute(Range.create(1));
        kernel.dispose();
        float price =  kernel.result[0];
        assertEquals(7.8360, (double) price, 0.1);
    }
}