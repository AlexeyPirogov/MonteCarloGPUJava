package com.mc.benchmark;

import com.mc.pricing.cpu.MonteCarloCPU;
import com.mc.generator.GeneratorParams;
import com.mc.generator.OptionGenerator;
import com.mc.pricing.gpu.gpu.MonteCarloGPU;
import com.mc.pricing.params.BarrierBatchArrayOptionsParams;
import com.mc.pricing.params.BarrierOptionParams;
import com.mc.pricing.params.BatchArrayOptionsParams;
import com.mc.pricing.params.EurOptionParams;

import java.util.List;

import static com.mc.generator.Utils.*;

public class BenchmarkRunner {

    private final MonteCarloGPU mcGPU = new MonteCarloGPU();
    private final MonteCarloCPU mcCPU = new MonteCarloCPU();

    public void benchmarkBarrierCallOptions(BenchmarkInputs benchmarkInputs) {
        OptionGenerator generator = new OptionGenerator();

        int t = 1;
        int tNum = 100;
        float[][] rnd = get2dRandomArray(benchmarkInputs.getMcIterations(), tNum);
        float[] rnd1d = to1dArray(rnd);

        GeneratorParams param = getGeneratorsParams(benchmarkInputs.getOptionsNum(), t);
        List<BarrierOptionParams> optionParams = generator.generateBarrierOptionParams(param);
        BarrierBatchArrayOptionsParams batchArrayOptionsParams = generator.toBarrierBatchArrayOptionsParams(optionParams);

        for (int i = 0; i < benchmarkInputs.getBenchmarkRuns(); i++) {
            float[] gpuResults = null;
            if (benchmarkInputs.getTestMode() == 1 || benchmarkInputs.getTestMode() == 2) {
                long gpuStart = System.currentTimeMillis();
                gpuResults = mcGPU.priceBarrierOptions(batchArrayOptionsParams, tNum, benchmarkInputs.getMcIterations(), rnd1d, benchmarkInputs.isPrintGPUInfo());
                long gpuTime = System.currentTimeMillis() - gpuStart;
                System.out.println("GPU took = " + gpuTime);
            }

            List<Float> cpuResults = null;
            if (benchmarkInputs.getTestMode() == 1 || benchmarkInputs.getTestMode() == 3) {
                long cpuStart = System.currentTimeMillis();
                cpuResults = mcCPU.priceBarrierOptionsPar(optionParams, tNum, benchmarkInputs.getMcIterations(), rnd);
                long cpuTime = System.currentTimeMillis() - cpuStart;
                System.out.println("CPU took = " + cpuTime);
            }

            if (benchmarkInputs.isAssertResults() && benchmarkInputs.getTestMode() == 1)
                assertResults(benchmarkInputs.getOptionsNum(), gpuResults, cpuResults);
        }
    }

    public void benchmarkEurCallOptions(BenchmarkInputs benchmarkInputs) {
        OptionGenerator generator = new OptionGenerator();

        int t = 3;
        GeneratorParams param = getGeneratorsParams(benchmarkInputs.getOptionsNum(), t);
        final float[] rnd = getRandomArray(benchmarkInputs.getMcIterations());
        List<EurOptionParams> optionParams = generator.generateEurOptionParams(param);
        BatchArrayOptionsParams batchArrayOptionsParams = generator.toBatchArrayOptionsParams(optionParams);

        for (int i = 0; i < benchmarkInputs.getBenchmarkRuns(); i++) {
            float[] gpuResults = null;
            if (benchmarkInputs.getTestMode() == 1 || benchmarkInputs.getTestMode() == 2) {
                long gpuStart = System.currentTimeMillis();
                gpuResults = mcGPU.priceEurCallOptions(batchArrayOptionsParams, benchmarkInputs.getMcIterations(), rnd, benchmarkInputs.isPrintGPUInfo());
                long gpuTime = System.currentTimeMillis() - gpuStart;
                System.out.println("GPU took = " + gpuTime);
            }

            List<Float> cpuResults = null;
            if (benchmarkInputs.getTestMode() == 1 || benchmarkInputs.getTestMode() == 3) {
                long cpuStart = System.currentTimeMillis();
                cpuResults = mcCPU.priceEurCallOptionsPar(optionParams, benchmarkInputs.getMcIterations(), rnd);
                long cpuTime = System.currentTimeMillis() - cpuStart;
                System.out.println("CPU took = " + cpuTime);
            }

            if (benchmarkInputs.isAssertResults() && benchmarkInputs.getTestMode() == 1)
                assertResults(benchmarkInputs.getOptionsNum(), gpuResults, cpuResults);
        }
    }

    private GeneratorParams getGeneratorsParams(int n, int t) {
        float s0Min = 20;
        float s0Max = 40;
        float stdDevMin = 0.1f;
        float stdDevMax = 0.3f;
        float xDelatMin = 1;
        float xDeltaMax = 3;
        float r = 0.01f;

        return new GeneratorParams(n, s0Min, s0Max, stdDevMin, stdDevMax, xDelatMin, xDeltaMax, t, r);
    }

    private void assertResults(int optionsNum, float[] gpuResults, List<Float> cpuResults) {
        for (int j = 0; j < optionsNum; j++) {
            if (Math.abs(gpuResults[j] - cpuResults.get(j)) > 0.001) {
                System.err.printf("CPU and GPU produced different results. CPU = %s , GPU = %s%n", cpuResults.get(j), gpuResults[j]);
            }
        }
    }
}
