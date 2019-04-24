package com.mc.pricing.gpu.gpu;

import com.aparapi.Range;
import com.aparapi.device.Device;
import com.mc.pricing.params.BarrierBatchArrayOptionsParams;
import com.mc.pricing.params.BatchArrayOptionsParams;

public class MonteCarloGPU {

    public float[] priceBarrierOptions(BarrierBatchArrayOptionsParams params, int tNum,
                                       int iterations, float[] rnd, boolean printGPUInfo) {
        MCBarrierOptionKernel kernel = new MCBarrierOptionKernel(params, tNum, iterations, rnd);
        return runKernel(params.getS0s(), iterations, kernel, printGPUInfo);
    }

    public float[] priceEurCallOptions(BatchArrayOptionsParams params, int iterations, float[] rnd, boolean printGPUInfo) {
        BaseMCOptionKernel kernel = new BaseMCOptionKernel(params, iterations, rnd);
        return runKernel(params.getS0s(), iterations, kernel, printGPUInfo);
    }

    private float[] runKernel(float[] s0s, int iterations, BaseMCOptionKernel kernel, boolean printGPUInfo) {
        System.out.println("Running on GPU:" + kernel.isRunningCL());
        if (printGPUInfo)
            System.out.println("Device info:" + Device.best());

        long mcStart = System.currentTimeMillis();
        kernel.execute(Range.create(s0s.length * iterations));
        long mcTook = System.currentTimeMillis() - mcStart;

        kernel.setOperation(1);
        long aggregateStart = System.currentTimeMillis();
        kernel.execute(Range.create(s0s.length));
        long aggregateTook = System.currentTimeMillis() - aggregateStart;

        kernel.dispose();
        System.out.println(String.format("Kernel stats {MC ms=%d; aggregate ms=%d}", mcTook, aggregateTook));
        return kernel.result;
    }
}