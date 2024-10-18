package edu.njit.cs114.polynomial;

import java.util.Iterator;

/**
 * Author: Justin Cordova
 * Date created: 1/25/2024
 */
public interface Polynomial {

    /**
     * Returns coefficient of the term with 'power'
     *
     * @param power
     * @return
     */
    public double coefficient(int power);

    /**
     * Returns the degree of polynomial (i.e. largest power of a non-zero term)
     *
     * @return
     */
    public int degree();

    /**
     * Add a term to the polynomial
     *
     * @param power
     * @param coefficient
     * @throws Exception when the power is invalid (e.g. negative)
     */
    public void addTerm(int power, double coefficient) throws Exception;

    /**
     * Remove and return the term for the specified power,
     * @param power
     * @return removed term if it exists else zero term
     */
    public PolynomialTerm removeTerm(int power);

    /**
     * Returns evaluation of the polynomial at 'point'
     *
     * @param point
     * @return
     */
    public double evaluate(double point);

    /**
     * Returns a new polynomial that is the result of addition of polynomial p to this polynomial
     *
     * @param p
     * @return
     */
    public Polynomial add(Polynomial p);

    /**
     * Returns a new polynomial that is the result of subtraction of polynomial p from this polynomial
     *
     * @param p
     * @return
     */
    public Polynomial subtract(Polynomial p);

    /**
     * Returns a new polynomial that is the result of multiplication of polynomial p with this polynomial
     *
     * @param p
     * @return
     */
    public Polynomial multiply(Polynomial p);

    /**
     * Returns the quotient polynomial that is the result of division of this polynomial
     * by polynomial p
     * @param p
     * @return
     */
    public Polynomial divide(Polynomial p) throws Exception;

    /**
     * Returns composition of polynomial p with this polynomial q (i.e. p(q(x)))
     * @param p
     * @return
     */
    public Polynomial compose(Polynomial p);

    /**
     * Get polynomial terms with non-zero coefficients in decreasing order of power
     * @return
     */
    public Iterator<PolynomialTerm> getIterator();

}

