package com.mc.pricing;

import org.apache.commons.math3.distribution.NormalDistribution;

import static java.lang.Math.*;

public class ReferencePricer {

    private static final NormalDistribution  normalDistribution = new NormalDistribution();

    public double eurCallBSMPrice(double s, double x, double r, double sigma, double t) {
        double d1 = (log(s / x) + (r + sigma * sigma / 2) * t) / (sigma * sqrt(t));
        double d2 = d1 - sigma * sqrt(t);
        return s * normalCDF(d1) - x * Math.exp(-r * t) * normalCDF(d2);
    }

    public double barrierOutCallPrice(double S0, double K, double Sb, double r, double stdDev, double t) {
        double stdDev2 = stdDev*stdDev;
        double d1 = (log(S0/K) + (r+stdDev2/2)*t)/(stdDev*sqrt(t));
        double d2 = d1 - stdDev*sqrt(t);
        double s = 2 / (stdDev * sqrt(t)) * log(Sb / S0);
        double d3 = d1 + s;
        double d4 = d2 + s;
        double q = 2*r/stdDev2;
        return S0*(normalCDF(d1) - pow(Sb/S0, q+1)*normalCDF(d3))
                - K*exp(-r*t)*(normalCDF(d2)-pow(Sb/S0,q-1)* normalCDF(d4));
    }

    private double normalCDF(double x) {
        return normalDistribution.cumulativeProbability(x);
    }

}
