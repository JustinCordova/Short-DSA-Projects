package edu.njit.cs114.polynomial;

/**
 * Author: Justin Cordova
 * Date created: 1/25/2024
 */
public class PolynomialTerm {

    private final int power;
    private final double coefficient;

    public PolynomialTerm(double coefficient, int power) {
        this.coefficient = coefficient;
        this.power = power;
    }

    public int getPower() {
        return power;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public String toString(boolean isLeadingTerm) {
        StringBuilder builder = new StringBuilder();
        if (coefficient < 0) {
            builder.append(isLeadingTerm ? "-" : " - ");
        }
        builder.append(String.format("%.3f",Math.abs(coefficient)));
        if (power > 0) {
            builder.append("x");
            if (power > 1) {
                builder.append("^" + power);
            }
        }
        return builder.toString();
    }

    public String toString() {
        return toString(false);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PolynomialTerm)) {
            return false;
        }
        PolynomialTerm other = (PolynomialTerm) obj;
        return (this.power == other.power && this.coefficient == other.coefficient);
    }
}
