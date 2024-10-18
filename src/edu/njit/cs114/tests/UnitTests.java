package edu.njit.cs114.tests;

import org.junit.jupiter.api.AfterAll;

/**
 * Author: Ravi Varadarajan
 * Date created: 1/30/2024
 */
public class UnitTests {

    protected static int nTestsPassed = 0;
    protected static int totalScore = 0;
    private static final double DELTA = 0.0001;

    @AfterAll
    public static void onEndTests() {
        System.out.println(nTestsPassed + " tests passed");
        System.out.println("Total score = " + totalScore);
    }

    protected void failure(String test, Exception e) {
        e.printStackTrace();
        System.out.println(test + " failed");
    }

    protected void success(String test) {
        System.out.println(test+ " passed");
        nTestsPassed++;
    }

    protected boolean approxEquals(double a1, double a2) {
        return Math.abs(a1-a2) <= DELTA;
    }


}
