package edu.njit.cs114.polynomial;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Justin Cordova
 * Date created: 2/21/2024
 */
public class ListPolynomial extends AbstractPolynomial {

    /**
     * To be completed for lab: Initialize the list
     */
    private List<PolynomialTerm> termList = new LinkedList<>();

    private class ListPolyIterator implements Iterator<PolynomialTerm> {
        private Iterator<PolynomialTerm> iter = termList.iterator();

        @Override
        public boolean hasNext() {
            return iter.hasNext();
        }

        @Override
        public PolynomialTerm next() {
            PolynomialTerm term = iter.next();
            return new PolynomialTerm(term.getCoefficient(), term.getPower());
        }
    }

    // Default constructor
    public ListPolynomial() {}

    /**
     * Create a single term polynomial
     * @param power
     * @param coefficient
     * @throws Exception
     */
    public ListPolynomial(int power, double coefficient) throws Exception {
        if (power < 0) {
            throw new Exception("Invalid power for the term");
        }
        addTerm(power, coefficient);
    }

    /**
     * Create a new polynomial that is a copy of "another".
     * NOTE : you should use only the interface methods of Polynomial
     *
     * @param another
     */
    public ListPolynomial(Polynomial another) throws Exception {
        /**
         * Complete the code for lab
         */
        // Iterate
        Iterator<PolynomialTerm> iterator = another.getIterator();
        while (iterator.hasNext()) {
            PolynomialTerm term = iterator.next();
            int power = term.getPower();
            double coefficient = term.getCoefficient();
            addTerm(power, coefficient);
        }
    }


    /**
     * Returns coefficient of power
     * @param power
     * @return
     */
    @Override
    public double coefficient(int power) {
        /**
         * Complete the code for lab
         */
        // For loop to find matching power
        for (PolynomialTerm term : termList) {
            if (term.getPower() == power) {
                // Return if power matches
                return term.getCoefficient();
            }
        }
        // Return 0 if power DNE
        return 0;
    }

    /**
     * Returns degree of the polynomial
     * @return
     */
    @Override
    public int degree() {
        /**
         * Complete the code for lab
         */
        if (termList.isEmpty()) {
            return 0;
        }
        else {
            return termList.get(0).getPower();
        }
    }

    /**
     * Adds polynomial term; add to existing term if power already exists
     * @param power
     * @param coefficient
     * @throws Exception if power < 0
     */
    @Override
    public void addTerm(int power, double coefficient) throws Exception {
        if (power < 0) {
            throw new Exception("Invalid Power");
        }

        if (coefficient == 0) {
            return;
        }

        int index = 0;
        for (PolynomialTerm term : termList) {
            if (power < term.getPower()) {
                index++;
                continue;
            }
            if (power == term.getPower()) {
                double newCoefficient = term.getCoefficient() + coefficient;
                if (newCoefficient == 0) {
                    termList.remove(index);
                }
                else {
                    termList.set(index, new PolynomialTerm(newCoefficient, power));
                }
                return;
            }

            termList.add(index, new PolynomialTerm(coefficient, power));
            return;
        }

        termList.add(new PolynomialTerm(coefficient, power));
    }


    /**
     * Remove and return the term for the specified power,
     * @param power
     * @return removed term if it exists else zero term
     */
    @Override
    public PolynomialTerm removeTerm(int power) {
        /**
         * Complete the code for homework
         */
        Iterator<PolynomialTerm> iterator = termList.iterator();
        while (iterator.hasNext()) {
            PolynomialTerm term = iterator.next();
            if (term.getPower() == power) {
                iterator.remove(); // Iterator to remove term
                return term;
            }
        }
        return new PolynomialTerm(0, power); // Return 0 term when not found
    }

    /**
     * Evaluate polynomial at point
     * @param point
     * @return
     */
    @Override
    public double evaluate(double point) {
        /**
         * Complete the code for homework
         */
        double out = 0.0;
        for (PolynomialTerm term : termList) {
            out += Math.pow(point, term.getPower()) * term.getCoefficient();
        }
        return out;
    }

    /**
     * Add polynomial p to this polynomial and return the result
     * @param p
     * @return
     */
    @Override
    public Polynomial add(Polynomial p) {
        /**
         * Complete the code for homework
         */
        Polynomial out = new ListPolynomial();
        int maxLength = Math.max(this.degree(), p.degree());
        for (int i = maxLength; i >= 0; i--) {
            double valCur = (this.coefficient(i) != 0 ? this.coefficient(i) : 0);
            double valNew = (p.coefficient(i) != 0 ? p.coefficient(i) : 0);

            if (valCur != 0 || valNew != 0) {
                try {
                    out.addTerm(i, valCur + valNew);
                } catch (Exception e) {
                }
            }
        }

        return out;
    }

    /**
     * Substract polynomial p from this polynomial and return the result
     * @param p
     * @return
     */
    @Override
    public Polynomial subtract(Polynomial p) {
        /**
         * Complete the code for homework
         */
        Polynomial out = new ListPolynomial();
        int maxLength = Math.max(this.degree(), p.degree());
        for (int i = maxLength; i >= 0; i--) {
            double valCur = (this.coefficient(i) != 0 ? this.coefficient(i) : 0);
            double valNew = (p.coefficient(i) != 0 ? p.coefficient(i) : 0);

            if (valCur != 0 || valNew != 0) {
                try {
                    out.addTerm(i, valCur - valNew);
                } catch (Exception e) {
                    // Handle the exception if needed
                }
            }
        }

        return out;
    }

    /**
     * Multiply polynomial p with this polynomial and return the result
     * @param p
     * @return
     */
    @Override
    public Polynomial multiply(Polynomial p) {
        /**
         * Complete the code for homework
         */
        Polynomial out = new ListPolynomial();

        for (PolynomialTerm term : this.termList) {
            Iterator<PolynomialTerm> iter = p.getIterator();

            while (iter.hasNext()) {
                PolynomialTerm iterTerm = iter.next();
                double coeff = iterTerm.getCoefficient();
                int power = iterTerm.getPower();

                try {
                    out.addTerm(term.getPower() + power, term.getCoefficient() * coeff);
                } catch (Exception e) {
                }
            }
        }

        return out;
    }

    @Override
    public Polynomial divide(Polynomial p) throws Exception {
        Polynomial quotient = new ListPolynomial();
        /**
         * Complete code here for homework
         */
        return quotient;
    }

    @Override
    // Extra credit
    public Polynomial compose(Polynomial p) {
        Polynomial result = new ListPolynomial();
        /**
         * Complete code here for homework extra-credit
         */
        return result;
    }

    @Override
    public Iterator<PolynomialTerm> getIterator() {
        return new ListPolyIterator();
    }

    public static void main(String [] args) throws Exception {
        /** Uncomment after you have implemented all the functions */
        Polynomial p1 = new ListPolynomial();
        System.out.println("p1(x) = " + p1);
        assert p1.degree() == 0;
        assert p1.coefficient(0) == 0;
        assert p1.coefficient(2) == 0;
        assert p1.equals(new ListPolynomial());
        Polynomial p2 = new ListPolynomial(5, -1.6);
        p2.addTerm(0,3.1);
        p2.addTerm(4,2.5);
        p2.addTerm(2,-2.5);
        System.out.println("p2(x) = " + p2);
        assert p2.degree() == 5;
        assert p2.coefficient(4) == 2.5;
        assert p2.toString().equals("-1.600x^5 + 2.500x^4 - 2.500x^2 + 3.100");
        System.out.println("p2(1) = " + p2.evaluate(1));
        assert Math.abs(p2.evaluate(1)-1.5) <= 0.001;
        Polynomial p3 = new ListPolynomial(0, -4);
        p3.addTerm(5,3);
        p3.addTerm(5,-1);
        System.out.println("p3(x) = " + p3);
        assert p3.degree() == 5;
        assert p3.coefficient(5) == 2;
        assert p3.coefficient(0) == -4;
        System.out.println("p3(2) = " + p3.evaluate(2));
        assert p3.evaluate(2) == 60;
        Polynomial p21 = new ListPolynomial(p2);
        System.out.println("p21(x) = " + p21);
        assert p21.equals(p2);
        p21.addTerm(4, -3.1);
        System.out.println("p21(x) = " + p21);
        assert !p21.equals(p2);
        assert p21.coefficient(4) == p2.coefficient(4)-3.1;
        PolynomialTerm t1 = p21.removeTerm(4);
        System.out.println("p21(x) = " + p21);
        assert !p21.equals(p2);
        assert p21.coefficient(4) == 0;
        assert t1.getPower() == 4;
        assert Math.abs(t1.getCoefficient() - -0.6) <= 0.0001;
        System.out.println("p2(x) = " + p2);
        Polynomial p22 = new ListPolynomial(p21);
        t1 = p21.removeTerm(1);
        System.out.println("p21(x) = " + p21);
        assert p21.equals(p22);
        assert t1.getPower() == 1;
        assert t1.getCoefficient() == 0;
        try {
            Polynomial p5 = new ListPolynomial(-5, 4);
            assert false;
        } catch (Exception e) {
            // Exception expected
            assert true;
        }
        System.out.println("p2(x) + p3(x) = " + p2.add(p3));
        Polynomial result = p2.add(p3);
        assert result.degree() == 5;
        assert Math.abs(result.coefficient(5) - 0.4) <= 0.0001;;
        System.out.println("p2(x) - p3(x) = " +p2.subtract(p3));
        result = p2.subtract(p3);
        assert result.degree() == 5;
        assert Math.abs(result.coefficient(5) - -3.6) <= 0.0001;
        assert Math.abs(result.coefficient(0) - 7.1) <= 0.0001;
        System.out.println("p2(x) * p3(x) = " +p2.multiply(p3));
        result = p2.multiply(p3);
        assert result.degree() == 10;
        assert Math.abs(result.coefficient(10) - -3.2) <= 0.0001;
        assert Math.abs(result.coefficient(5) - 12.6) <= 0.0001;
        assert Math.abs(result.coefficient(0) - -12.4) <= 0.0001;
        assert Math.abs(p2.evaluate(1) * p3.evaluate(1) - result.evaluate(1)) <= 0.0001;
        result = result.divide(p3);
        System.out.println("p2(x) * p3(x) / p3(x) = " + result);
        assert result.degree() == p2.degree();
        assert Math.abs(result.coefficient(4) - p2.coefficient(4)) <= 0.0001;
        assert Math.abs(result.coefficient(3) - p2.coefficient(3)) <= 0.0001;
        assert Math.abs(result.coefficient(2) - p2.coefficient(2)) <= 0.0001;
        assert Math.abs(result.coefficient(1) - p2.coefficient(1)) <= 0.0001;
        assert Math.abs(result.coefficient(0) - p2.coefficient(0)) <= 0.0001;
    }
}