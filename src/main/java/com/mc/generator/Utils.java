package com.mc.generator;

import java.util.Random;

public class Utils {

    public static float[][] get2dRandomArray(int mcIterations, int t) {
        float[][] result = new float[t][];
        for (int i = 0; i < t; i++) {
            result[i] = getRandomArray(mcIterations);
        }
        return result;
    }

    public static float[] getRandomArray(int mcIterations) {
        Random random = new Random();
        final float[] rnd = new float[mcIterations];
        for (int i = 0; i < mcIterations; i++) {
            rnd[i] = Double.valueOf(random.nextGaussian()).floatValue();
        }
        return rnd;
    }

    public static float[] to1dArray(float[][] rnd) {
        float[] res = new float[rnd.length * rnd[0].length];
        int n = 0;
        for (float[] floats : rnd) {
            for (int j = 0; j < rnd[0].length; j++) {
                res[n] = floats[j];
                n++;
            }
        }
        return res;
    }
}
