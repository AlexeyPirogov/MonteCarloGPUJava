package com.mc.benchmark;

import com.mc.OptionTypeEnum;

public class BenchmarkInputs {
    private final OptionTypeEnum optionType;
    private final int optionsNum;
    private final int mcIterations;
    private final int benchmarkRuns;
    private final boolean assertResults;
    private final int testMode;
    private final boolean printGPUInfo;

    public BenchmarkInputs(OptionTypeEnum optionType, int optionsNum, int mcIterations, int benchmarkRuns,
                           boolean assertResults, int testMode, boolean printGPUInfo) {
        this.optionType = optionType;
        this.optionsNum = optionsNum;
        this.mcIterations = mcIterations;
        this.benchmarkRuns = benchmarkRuns;
        this.assertResults = assertResults;
        this.testMode = testMode;
        this.printGPUInfo = printGPUInfo;
    }

    public OptionTypeEnum getOptionType() {
        return optionType;
    }

    public int getOptionsNum() {
        return optionsNum;
    }

    public int getMcIterations() {
        return mcIterations;
    }

    public int getBenchmarkRuns() {
        return benchmarkRuns;
    }

    public boolean isAssertResults() {
        return assertResults;
    }

    public int getTestMode() {
        return testMode;
    }

    public boolean isPrintGPUInfo() {
        return printGPUInfo;
    }

    @Override
    public String toString() {
        return "BenchmarkInputs{" +
                "optionsType=" + optionType +
                ", optionsNum=" + optionsNum +
                ", mcIterations=" + mcIterations +
                ", benchmarkRuns=" + benchmarkRuns +
                ", assertResults=" + assertResults +
                ", testMode=" + testMode +
                '}';
    }
}
