package com.mc;

import com.mc.benchmark.BenchmarkInputs;
import com.mc.benchmark.BenchmarkRunner;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class MainApp {

    @Option(name = "-t", usage = "options type (EUR|BAR)")
    private OptionTypeEnum optionType = OptionTypeEnum.EUR;
    @Option(name = "-n", usage = "number of options")
    private int optionsNum = 100;
    @Option(name = "-i", usage = "number of Monte Carlo iterations")
    private int mcIterations = 1_000_000;
    @Option(name = "-b", usage = "number of benchmark runs")
    private int benchmarkRuns = 1;
    @Option(name = "-a", usage = "assert results (CPU vs GPU)")
    private boolean assertResults = true;
    @Option(name = "-m", usage = "test mode (1 - GPU+CPU, 2 - GPU, 3 - CPU)")
    private int testMode = 1;
    @Option(name = "-p", usage = "print GPU info")
    private boolean printGPUInfo;

    public static void main(String[] args) throws IOException {
        MainApp mainApp = new MainApp();
        BenchmarkInputs inputs = mainApp.parseInputs(args);
        System.out.println(inputs);

        BenchmarkRunner runner = new BenchmarkRunner();

        if (inputs.getOptionType() == OptionTypeEnum.EUR) {
            System.out.println("Running eur call options pricing");
            runner.benchmarkEurCallOptions(inputs);
        } else {
            System.out.println("Running barrier call options pricing");
            runner.benchmarkBarrierCallOptions(inputs);
        }
    }

    private BenchmarkInputs parseInputs(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println("Input error");
            parser.printUsage(System.err);
            System.err.println();
            throw new IOException();
        }
        return new BenchmarkInputs(optionType, optionsNum, mcIterations, benchmarkRuns, assertResults, testMode, printGPUInfo);
    }

}
