package edu.njit.cs114.graphs;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/21/2024
 */
public class GraphException extends Exception {

    public GraphException(String msg) {
        super(msg);
    }

    public GraphException(String msg, Throwable t) {
        super(msg, t);
    }
}
