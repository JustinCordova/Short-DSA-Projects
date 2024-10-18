package edu.njit.cs114.polynomial;

import java.util.Iterator;

/**
 * Author: Justin Cordova
 * Date created: 1/25/2024
 */
public abstract class AbstractPolynomial implements Polynomial {

    /**
     * Returns true if the object obj is a Polynomial and its coefficients are the same
     *   for all the terms in the polynomial
     * @param obj
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Polynomial)) {
            return false;
        }
        Polynomial other = (Polynomial) obj;
        Iterator<PolynomialTerm> polyIter1 = this.getIterator();
        Iterator<PolynomialTerm> polyIter2 = other.getIterator();
        while (polyIter1.hasNext()) {
            if (!polyIter2.hasNext()) {
                return false;
            }
            if (!polyIter1.next().equals(polyIter2.next())) {
                return false;
            }
        }
        return !polyIter2.hasNext();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        int degree = degree();
        if (degree == 0) {
            return "" + String.format("%.3f",this.coefficient(0));
        }
        Iterator<PolynomialTerm> polyIter = this.getIterator();
        while (polyIter.hasNext()) {
            PolynomialTerm polyTerm = polyIter.next();
            if (polyTerm.getCoefficient() > 0) {
                builder.append(polyTerm.getPower() < degree ? " + " : "");
            }
            builder.append(polyTerm.toString(polyTerm.getPower() == degree));
        }
        if (builder.length() == 0) {
            builder.append("0.000");
        }
        return builder.toString();
    }

}
