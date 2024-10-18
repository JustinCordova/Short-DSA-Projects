package edu.njit.cs114.tests;

import edu.njit.cs114.polynomial.ArrayPolynomial;
import edu.njit.cs114.polynomial.Polynomial;
import edu.njit.cs114.polynomial.PolynomialTerm;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 1/30/2024
 */
public class ArrayPolynomialTests extends UnitTests {

    private static class MockPolynomial implements Polynomial {

        @Override
        public double coefficient(int power) {
            if (power == 0) {
                return -6.7;
            }
            if (power == 1) {
                return -5.4;
            }
            if (power == 3) {
                return 2.8;
            }
            return 0;
        }

        @Override
        public int degree() {
            return 3;
        }

        @Override
        public void addTerm(int power, double coefficient) throws Exception {

        }

        @Override
        public PolynomialTerm removeTerm(int power) {
            return new PolynomialTerm(0.0,0);
        }

        @Override
        public double evaluate(double point) {
            return 0;
        }


        @Override
        public Polynomial add(Polynomial p) {
            return null;
        }

        @Override
        public Polynomial subtract(Polynomial p) {
            return null;
        }

        @Override
        public Polynomial multiply(Polynomial p) {
            return null;
        }

        @Override
        public Polynomial divide(Polynomial p) throws Exception {
            return null;
        }

        @Override
        public Polynomial compose(Polynomial p) {
            return null;
        }

        @Override
        public Iterator<PolynomialTerm> getIterator() {
            return null;
        }

        @Override
        public String toString() {
            return "2.800x^3 - 5.400x - 6.700";
        }

    }

    @Test
    public void singleTermConstructorTest() {
        try {
            Polynomial p = new ArrayPolynomial(0, -3.6);
            assertEquals(p.toString(), "-3.600");
            totalScore += 2;
            p = new ArrayPolynomial(4, 2.8);
            assertEquals(p.toString(), "2.800x^4");
            totalScore += 3;
            success("singleTermConstructorTest");
        } catch (Exception e) {
            failure("singleTermConstructorTest", e);
        }
    }

    @Test
    public void copyConstructorTest1() {
        try {
            Polynomial p1 = new ArrayPolynomial(5, -3.6);
            p1.addTerm(3, 1.9);
            Polynomial p2 = new ArrayPolynomial(p1);
            assertEquals(p2.toString(), "-3.600x^5 + 1.900x^3");
            totalScore += 3;
            p1.addTerm(0, 3.2);
            assertEquals(p2.toString(), "-3.600x^5 + 1.900x^3");
            totalScore += 1;
            success("copyConstructorTest1");
        } catch (Exception e) {
            failure("copyConstructorTest1", e);
        }
    }

    @Test
    public void copyConstructorTest2() {
        try {
            Polynomial p1 = new MockPolynomial();
            Polynomial p2 = new ArrayPolynomial(p1);
            assertEquals(p2.toString(), "2.800x^3 - 5.400x - 6.700");
            totalScore += 2;
            success("copyConstructorTest2");
        } catch (Exception e) {
            failure("copyConstructorTest2", e);
        }
    }

    @Test
    public void addTermTest1() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(-4, 1.9);
            failure("addTermTest1", new Exception("Invalid power not checked"));
        } catch (ArrayIndexOutOfBoundsException e) {
            failure("addTermTest1", e);
        }
        catch (Exception e) {
            success("addTermTest1");
            totalScore += 1;
        }
    }


    @Test
    public void addTermTest2() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(3, 1.9);
            assertEquals(p1.toString(), "1.900x^3");
            totalScore += 1;
            p1.addTerm(7, 3.6);
            assertEquals(p1.toString(), "3.600x^7 + 1.900x^3");
            totalScore += 1;
            p1.addTerm(0, -1.4);
            assertEquals(p1.toString(), "3.600x^7 + 1.900x^3 - 1.400");
            totalScore += 2;
            p1.addTerm(3, -2.5);
            assertEquals(p1.toString(), "3.600x^7 - 0.600x^3 - 1.400");
            totalScore += 1;
            success("addTermTest2");
        } catch (Exception e) {
            failure("addTermTest2", e);
        }
    }

    @Test
    public void removeTermTest() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(3, 1.9);
            p1.addTerm(7, 3.6);
            assertEquals(p1.toString(), "3.600x^7 + 1.900x^3");
            PolynomialTerm term = p1.removeTerm(7);
            assertEquals(term.getCoefficient(), 3.6);
            assertEquals(term.getPower(), 7);
            assertEquals(p1.toString(), "1.900x^3");
            totalScore += 2;
            term = p1.removeTerm(4);
            assertEquals(term.getCoefficient(), 0);
            assertEquals(term.getPower(), 4);
            assertEquals(p1.toString(), "1.900x^3");
            totalScore += 2;
            term = p1.removeTerm(3);
            assertEquals(term.getCoefficient(), 1.9);
            assertEquals(term.getPower(), 3);
            assertEquals(p1.toString(), "0.000");
            totalScore += 2;
            success("removeTermTest");
        } catch (Exception e) {
            failure("removeTermTest", e);
        }
    }

    @Test
    public void degreeTest() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            assertEquals(p1.degree(), 0);
            totalScore += 1;
            p1.addTerm(3, 1.9);
            p1.addTerm(7, 3.6);
            assertEquals(p1.degree(), 7);
            totalScore += 1;
            success("degreeTest");
        } catch (Exception e) {
            failure("degreeTest", e);
        }
    }

    @Test
    public void coefficientTest() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(3, 1.9);
            p1.addTerm(7, 3.6);
            assertEquals(p1.coefficient(7), 3.6);
            assertEquals(p1.coefficient(3), 1.9);
            assertEquals(p1.coefficient(2), 0);
            totalScore += 2;
            success("coefficientTest");
        } catch (Exception e) {
            failure("coefficientTest", e);
        }
    }

    @Test
    public void evaluateTest() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(0, -5);
            assertEquals(p1.evaluate(4), -5);
            totalScore += 1;
            p1.addTerm(3, -1.8);
            p1.addTerm(7, 3.6);
            double expectedVal = 3.6 * Math.pow(2, 7) - 1.8 * Math.pow(2, 3) - 5;
            assertTrue(approxEquals(p1.evaluate(2), expectedVal));
            totalScore += 4;
            success("evaluateTest");
        } catch (Exception e) {
            failure("evaluateTest", e);
        }
    }

    @Test
    public void addTest1() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(3, 3.6);
            Polynomial p2 = new ArrayPolynomial();
            p2.addTerm(0, -1.8);
            p2.addTerm(2, 3.6);
            p2.addTerm(5, -7.1);
            Polynomial p3 = p1.add(p2);
            assertEquals(p1.toString(), "3.600x^3 - 1.800x^2");
            totalScore += 2;
            assertEquals(p3.degree(), 5);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(5), -7.1));
            assertTrue(approxEquals(p3.coefficient(3), 3.6));
            assertTrue(approxEquals(p3.coefficient(2), 1.8));
            assertTrue(approxEquals(p3.coefficient(0), -1.8));
            totalScore += 4;
            success("addTest1");
        } catch (Exception e) {
            failure("addTest1", e);
        }
    }

    @Test
    public void addTest2() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(3, 3.6);
            Polynomial p2 = new MockPolynomial();
            Polynomial p3 = p1.add(p2);
            assertEquals(p3.degree(), 3);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(3), 3.6 + p2.coefficient(3)));
            assertTrue(approxEquals(p3.coefficient(2), -1.8 + p2.coefficient(2)));
            assertTrue(approxEquals(p3.coefficient(1), p2.coefficient(1)));
            assertTrue(approxEquals(p3.coefficient(0), p2.coefficient(0)));
            totalScore += 4;
            success("addTest2");
        } catch (Exception e) {
            failure("addTest2", e);
        }
    }

    @Test
    public void subtractTest1() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(3, 3.6);
            Polynomial p2 = new ArrayPolynomial();
            p2.addTerm(0, -1.8);
            p2.addTerm(2, 5.4);
            p2.addTerm(3, 3.6);
            Polynomial p3 = p1.subtract(p2);
            assertEquals(p1.toString(), "3.600x^3 - 1.800x^2");
            totalScore += 2;
            assertEquals(p3.degree(), 2);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(3), 0));
            assertTrue(approxEquals(p3.coefficient(2), -7.2));
            assertTrue(approxEquals(p3.coefficient(1), 0));
            assertTrue(approxEquals(p3.coefficient(0), 1.8));
            totalScore += 3;
            success("subtractTest1");
        } catch (Exception e) {
            failure("subtractTest1", e);
        }
    }

    @Test
    public void subtractTest2() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(1, 3.6);
            Polynomial p2 = new MockPolynomial();
            Polynomial p3 = p1.subtract(p2);
            assertEquals(p3.degree(), 3);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(3), -p2.coefficient(3)));
            assertTrue(approxEquals(p3.coefficient(2), -1.8 - p2.coefficient(2)));
            assertTrue(approxEquals(p3.coefficient(1), 3.6 - p2.coefficient(1)));
            assertTrue(approxEquals(p3.coefficient(0), -p2.coefficient(0)));
            totalScore += 3;
            success("subtractTest2");
        } catch (Exception e) {
            failure("subtractTest2", e);
        }
    }

    @Test
    public void multiplyTest1() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(2, -1.8);
            p1.addTerm(3, 3.6);
            assertEquals(p1.multiply(new ArrayPolynomial()), new ArrayPolynomial());
            totalScore += 2;
            Polynomial p2 = new ArrayPolynomial();
            p2.addTerm(0, -1.8);
            p2.addTerm(2, 5.4);
            p2.addTerm(3, 3.6);
            Polynomial p3 = p1.multiply(p2);
            assertEquals(p1.toString(), "3.600x^3 - 1.800x^2");
            totalScore += 2;
            assertEquals(p3.degree(), 6);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(6), 3.6 * 3.6));
            assertTrue(approxEquals(p3.coefficient(5), -1.8 * 3.6 + 3.6 * 5.4));
            assertTrue(approxEquals(p3.coefficient(4), -1.8 * 5.4));
            assertTrue(approxEquals(p3.coefficient(3), -1.8 * 3.6));
            assertTrue(approxEquals(p3.coefficient(2), -1.8 * -1.8));
            assertTrue(approxEquals(p3.coefficient(1), 0));
            assertTrue(approxEquals(p3.coefficient(0), 0));
            totalScore += 6;
            success("multiplyTest1");
        } catch (Exception e) {
            failure("multiplyTest1", e);
        }
    }

    @Test
    public void multiplyTest2() {
        try {
            Polynomial p1 = new ArrayPolynomial();
            p1.addTerm(4, -1.8);
            p1.addTerm(1, 3.6);
            assertEquals(p1.multiply(new ArrayPolynomial()), new ArrayPolynomial());
            Polynomial p2 = new MockPolynomial();
            Polynomial p3 = p1.multiply(p2);
            assertEquals(p3.degree(), 7);
            totalScore += 2;
            assertTrue(approxEquals(p3.coefficient(7), -1.8 * p2.coefficient(3)));
            assertTrue(approxEquals(p3.coefficient(6), 0));
            assertTrue(approxEquals(p3.coefficient(5), -1.8 * p2.coefficient(1)));
            assertTrue(approxEquals(p3.coefficient(4),
                    -1.8 * p2.coefficient(0)
                            + 3.6 * p2.coefficient(3)));
            assertTrue(approxEquals(p3.coefficient(3), 0));
            assertTrue(approxEquals(p3.coefficient(2), 3.6 * p2.coefficient(1)));
            assertTrue(approxEquals(p3.coefficient(1), 3.6 * p2.coefficient(0)));
            totalScore += 5;
            success("multiplyTest2");
        } catch (Exception e) {
            failure("multiplyTest2", e);
        }
    }

    @Test
    public void divideTest() {
        try {
            Polynomial p1 = new ArrayPolynomial(4, 3.6);
            p1.addTerm(0, 3.6);
            p1.addTerm(3, 2.5);
            p1.addTerm(2, -2.5);
            Polynomial p2 = new MockPolynomial();
            Polynomial p3 = p1.divide(p2);
            assertEquals(p3.degree(), 1);
            totalScore += 1;
            assertTrue(approxEquals(p3.coefficient(1) * p2.coefficient(3), p1.coefficient(4)));
            assertTrue(approxEquals(p3.coefficient(0) * p2.coefficient(3), p1.coefficient(3)));
            assertTrue(approxEquals(p3.coefficient(5), 0));
            assertTrue(approxEquals(p3.coefficient(4), 0));
            assertTrue(approxEquals(p3.coefficient(3), 0));
            assertTrue(approxEquals(p3.coefficient(2), 0));
            totalScore += 3;
            Polynomial p4 = new ArrayPolynomial(5, 3.4);
            assertEquals(p1.divide(p4), new ArrayPolynomial());
            totalScore += 2;
            Polynomial p5 = p1.multiply(p2).add(new ArrayPolynomial(2,-2.4)).divide(p2);
            assertTrue(approxEquals(p5.coefficient(5), p1.coefficient(5)));
            assertTrue(approxEquals(p5.coefficient(4), p1.coefficient(4)));
            assertTrue(approxEquals(p5.coefficient(3), p1.coefficient(3)));
            assertTrue(approxEquals(p5.coefficient(2), p1.coefficient(2)));
            assertTrue(approxEquals(p5.coefficient(1), p1.coefficient(1)));
            assertTrue(approxEquals(p5.coefficient(0), p1.coefficient(0)));
            totalScore += 2;
            success("divideTest");
        } catch (Exception e) {
            failure("divideTest", e);
        }

    }

    //@Test
    // Uncomment for extra-credit
    public void composeTest() {
        try {
            Polynomial p1 = new ArrayPolynomial(0, 3.6);
            Polynomial p2 = new ArrayPolynomial(3, 1.5);
            Polynomial result = p1.compose(p2); // p2(p1(x))
            assertEquals(result.toString(), "69.984");
            totalScore += 2;
            result = p2.compose(p1); // p1(p2(x))
            assertEquals(result.toString(), "3.600");
            totalScore += 2;
            Polynomial p3 = new ArrayPolynomial(2, -1.5);
            p3.addTerm(1, 2.5);
            result = p2.compose(p3); // p3(p2(x))
            assertEquals(result.toString(), "-3.375x^6 + 3.750x^3");
            totalScore += 2;
            result = p3.compose(p2); // p2(p3(x))
            assertEquals(result,p3.multiply(p3).multiply(p3).multiply(new ArrayPolynomial(0,1.5)));
            totalScore += 4;
            success("composeTest");
        } catch (Exception e) {
            failure("composeTest", e);
        }

    }
}