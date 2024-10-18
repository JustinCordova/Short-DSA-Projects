package edu.njit.cs114.graphs;

import java.util.Iterator;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/21/2024
 */
public interface Graph {

    public class Edge {
        public final int from, to;
        public final int weight;

        public Edge(int i, int j, int weight) {
            this.from = i;
            this.to = j;
            this.weight = weight;
        }


        public String toString() {
            return "("+from+","+to+")";
        }
    }

    public boolean isDirected(); // is it a directed graph ?

    public void init(int n); // initialize with n vertices

    public int numVertices(); // number of vertices of graph

    public int numEdges(); // number of edges of graph

    public void addEdge(int u, int v, int weight) throws GraphException; // add edge

    public void addEdge(int u, int v) throws GraphException;

    public Iterator<Edge> getOutgoingEdges(int v); // get out edges from v

    public Edge delEdge(int u, int v); // delete edge from u to v and return it

    public boolean isEdge(int u, int v); // Is there an edge from u to v

    public int weight(int u, int v) throws GraphException; // get weight of edge from u to v

    public void setMark(int u, int val); // mark vertex u with value

    public int getMark(int u); // get value of mark for vertex u

}
