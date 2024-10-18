package edu.njit.cs114.graphs;

import java.util.Iterator;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/21/2024
 */
public abstract class AbstractGraph implements Graph {

    protected final boolean directed;
    protected int [] marks;
    protected int nEdges;
    protected int nVertices;

    public AbstractGraph(int n, boolean directed) {
        this.nVertices = n;
        this.directed = directed;
        marks = new int[n];
    }

    @Override
    public boolean isDirected() {
        return directed;
    }

    @Override
    public int numVertices() {
        return nVertices;
    }

    @Override
    public int numEdges() {
        return nEdges;
    }

    /**
     * Add edge for specific graph implementation
     * @param u
     * @param v
     * @param weight
     * @throws GraphException
     */
    protected abstract void addGraphEdge(int u, int v, int weight) throws GraphException;

    @Override
    public final void addEdge(int u, int v) throws GraphException {
        addEdge(u,v,1);
    }

    @Override
    public final void addEdge(int u, int v, int weight) throws GraphException {
        if (u < 0 || v < 0 || u >= nVertices || v >= nVertices) {
            throw new GraphException("Invalid vertices for the edge");
        }
        addGraphEdge(u,v,weight);
        if (!directed) {
            addGraphEdge(v,u,weight);
        }
        nEdges++;
    }

    /**
     * Delete edge for specific graph implementation
     * @param u
     * @param v
     * @return deleted edge if it exists else null
     */
    protected abstract Edge delGraphEdge(int u, int v);

    @Override
    public final Edge delEdge(int u, int v) {
        Edge edge = delGraphEdge(u,v);
        if (!directed) {
            delGraphEdge(v,u);
        }
        if (edge != null) {
            nEdges--;
        }
        return edge;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Printing the edges of " + (directed ? "directed" : "undirected")
                + " graph:\n");
        for (int i=0; i < nVertices; i++) {
            builder.append("Outgoing edges from " + i+":");
            Iterator<Edge> iter = getOutgoingEdges(i);
            while (iter.hasNext()) {
                builder.append(iter.next()+",");
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public void setMark(int u, int val) {
        if (u < 0 || u >= nVertices) {
            throw new IllegalArgumentException("Invalid vertex "+u);
        }
        marks[u] = val;
    }

    @Override
    public int getMark(int u) {
        if (u < 0 || u >= nVertices) {
            throw new IllegalArgumentException("Invalid vertex "+u);
        }
        return marks[u];
    }

}
