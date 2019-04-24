package com.mc.pricing;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReferencePricerTest {

    private final ReferencePricer pricer = new ReferencePricer();

    @Test
    public void eurCallBSMPrice() {
        //Stock price:50, Strike price:45, time to expiry 80d, risk-free rate:2%, vol: 30%. Expected result 6.02
        double price = pricer.eurCallBSMPrice(50, 45, 0.02, 0.3, 80d/365);
        assertEquals(6.02, price, 0.01);
    }

    @Test
    public void barrierOutCallPrice() {
        double price = pricer.barrierOutCallPrice(50d, 50d, 40d, 0.1d, 0.3d,1d);
        assertEquals(7.8360, price, 0.01);
    }
}