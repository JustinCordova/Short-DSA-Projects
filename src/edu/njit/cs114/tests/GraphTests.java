package edu.njit.cs114.tests;

import edu.njit.cs114.graphs.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Author: Ravi Varadarajan
 * Date created: 4/24/2024
 */
public class GraphTests extends UnitTests {

    private static final String NEWLINE = System.getProperty("line.separator");

    public static class AdjMatrixGraph extends AbstractGraph {

        private class EdgeIterator implements Iterator<Edge> {

            private final int fromVertex;
            private int nextToVertex;

            public EdgeIterator(int fromVertex) {
                this.fromVertex = fromVertex;
                while ((nextToVertex < arr.length) && (arr[fromVertex][nextToVertex] == 0)) {
                    nextToVertex++;
                }
            }

            @Override
            public boolean hasNext() {
                return nextToVertex < arr.length;
            }

            @Override
            public Edge next() {
                if (nextToVertex == arr.length) {
                    return null;
                }
                Edge edge = new Edge(fromVertex,nextToVertex,arr[fromVertex][nextToVertex]);
                nextToVertex++;
                while ((nextToVertex < arr.length) && (arr[fromVertex][nextToVertex] == 0)) {
                    nextToVertex++;
                }
                return edge;
            }
        }

        private int [] [] arr;

        public AdjMatrixGraph(int n, boolean directed) {
            super(n, directed);
            init(n);
        }

        @Override
        protected void addGraphEdge(int u, int v, int weight) throws GraphException {
            arr[u][v] = weight;
        }

        @Override
        protected Edge delGraphEdge(int u, int v) {
            if (arr[u][v] > 0) {
                return new Edge(u,v,arr[u][v]);
            } else {
                return null;
            }
        }

        @Override
        public void init(int n) {
             arr = new int[n][n];
        }

        @Override
        public Iterator<Edge> getOutgoingEdges(int v) {
            return new EdgeIterator(v);
        }

        @Override
        public boolean isEdge(int u, int v) {
            return arr[u][v] > 0;
        }

        @Override
        public int weight(int u, int v) throws GraphException {
            return arr[u][v];
        }
    }

    @Test
    public void addEdgeDirectedGraphTest() {
        try {
            Graph g = new AdjListGraph(8, true);
            g.addEdge(0, 1);
            g.addEdge(0, 2);
            g.addEdge(1, 2);
            g.addEdge(1, 3);
            g.addEdge(2, 0);
            g.addEdge(3, 2);
            g.addEdge(4, 3);
            g.addEdge(4, 6);
            assertEquals(8,g.numEdges());
            assertEquals(true, g.isEdge(0,1));
            assertEquals(true, g.isEdge(0,2));
            assertEquals(true, g.isEdge(1,2));
            assertEquals(true, g.isEdge(1,3));
            assertEquals(true, g.isEdge(2,0));
            assertEquals(true, g.isEdge(3,2));
            assertEquals(true, g.isEdge(4,3));
            assertEquals(true, g.isEdge(4,6));
            totalScore += 2;
            assertEquals(false, g.isEdge(3,4));
            assertEquals(false, g.isEdge(0,4));
            totalScore += 1;
            try {
                g.addEdge(3,2);
                assertFalse(true);
            } catch (Exception e) {
                assertTrue(true);
                totalScore += 1;
            }
            success("addEdgeDirectedGraphTest");
        } catch(Exception e) {
            failure("addEdgeDirectedGraphTest", e);
        }
    }

    @Test
    public void addEdgeUnDirectedGraphTest() {
        try {
            Graph g = new AdjListGraph(8, false);
            g.addEdge(0, 1);
            g.addEdge(0, 2);
            g.addEdge(1, 2);
            g.addEdge(1, 3);
            g.addEdge(3, 2);
            g.addEdge(4, 3);
            g.addEdge(4, 6);
            assertEquals(7, g.numEdges());
            assertEquals(true, g.isEdge(0, 1));
            assertEquals(true, g.isEdge(0, 2));
            assertEquals(true, g.isEdge(1, 2));
            assertEquals(true, g.isEdge(1, 3));
            assertEquals(true, g.isEdge(3, 2));
            assertEquals(true, g.isEdge(4, 3));
            assertEquals(true, g.isEdge(4, 6));
            assertEquals(true, g.isEdge(1, 0));
            assertEquals(true, g.isEdge(2, 0));
            assertEquals(true, g.isEdge(2, 1));
            assertEquals(true, g.isEdge(3, 1));
            assertEquals(true, g.isEdge(2, 3));
            assertEquals(true, g.isEdge(3, 4));
            assertEquals(true, g.isEdge(6, 4));
            totalScore += 2;
            try {
                g.addEdge(3,4);
                assertFalse(true);
            } catch (Exception e) {
                assertTrue(true);
                totalScore += 1;
            }
            assertEquals(false, g.isEdge(0, 4));
            totalScore += 1;
            success("addEdgeUnDirectedGraphTest");
        } catch (Exception e) {
            failure("addEdgeUnDirectedGraphTest", e);
        }
    }

    @Test
    public void delEdgeDirectedGraphTest() {
        try {
            Graph g = new AdjListGraph(7, true);
            g.addEdge(0, 1);
            g.addEdge(2, 0);
            g.addEdge(3, 5);
            g.addEdge(4, 6);
            g.addEdge(5, 3);
            g.addEdge(1, 6);
            g.addEdge(2, 6);
            assertEquals(7,g.numEdges());
            g.delEdge(3,5);
            assertEquals(6,g.numEdges());
            totalScore += 1;
            assertEquals(false, g.isEdge(3,5));
            totalScore += 1;
            assertEquals(true, g.isEdge(5,3));
            totalScore += 1;
            success("delEdgeDirectedGraphTest");
        } catch(Exception e) {
            failure("delEdgeDirectedGraphTest", e);
        }
    }

    @Test
    public void delEdgeUnDirectedGraphTest() {
        try {
            Graph g = new AdjListGraph(7, true);
            g.addEdge(0, 1);
            g.addEdge(2, 0);
            g.addEdge(3, 5);
            g.addEdge(4, 6);
            g.addEdge(5, 4);
            g.addEdge(1, 6);
            g.addEdge(2, 6);
            assertEquals(7,g.numEdges());
            g.delEdge(3,5);
            assertEquals(6,g.numEdges());
            totalScore += 1;
            assertEquals(false, g.isEdge(3,5));
            totalScore += 1;
            assertEquals(false, g.isEdge(5,3));
            totalScore += 1;
            success("delEdgeUnDirectedGraphTest");
        } catch(Exception e) {
            failure("delEdgeUnDirectedGraphTest", e);
        }
    }

    @Test
    public void getEdgeDirectedGraphTest() {
        try {
            AdjListGraph g = new AdjListGraph(7, true);
            g.addEdge(0,1,2);
            g.addEdge(2,0,3);
            g.addEdge(3,5,4);
            g.addEdge(4,6,5);
            g.addEdge(5,4,6);
            g.addEdge(1,6,7);
            g.addEdge(2,6,8);
            Graph.Edge edge = g.getEdge(3,5);
            assertEquals(3, edge.from);
            assertEquals(5, edge.to);
            assertEquals(4, edge.weight);
            totalScore += 1;
            assertEquals(null, g.getEdge(4,1));
            totalScore += 1;
            success("getEdgeDirectedGraphTest");
        } catch(Exception e) {
            failure("getEdgeDirectedGraphTest", e);
        }
    }

    @Test
    public void getEdgeUnDirectedGraphTest() {
        try {
            AdjListGraph g = new AdjListGraph(7, false);
            g.addEdge(0,1,2);
            g.addEdge(2,0,3);
            g.addEdge(3,5,4);
            g.addEdge(4,6,5);
            g.addEdge(5,4,6);
            g.addEdge(1,6,7);
            g.addEdge(2,6,8);
            Graph.Edge edge = g.getEdge(4,5);
            assertEquals(4, edge.from);
            assertEquals(5, edge.to);
            assertEquals(6, edge.weight);
            totalScore += 1;
            assertEquals(null, g.getEdge(4,1));
            totalScore += 1;
            success("getEdgeUnDirectedGraphTest");
        } catch(Exception e) {
            failure("getEdgeUnDirectedGraphTest", e);
        }
    }

    private static boolean checkbfsOutput(String outStr,
                                          int startVertex,
                                          int [] marks,
                                          List<List<Integer>> vertexLists) {
        StringBuilder builder = new StringBuilder();
        builder.append("Start vertex : ");
        builder.append(startVertex);
        builder.append(NEWLINE);
        for (List<Integer> vertexList : vertexLists) {
            builder.append(vertexList.get(0)+ "("
                    +marks[vertexList.get(0)]+") ---> ");
            for (int index=1; index < vertexList.size(); index++) {
                builder.append(vertexList.get(index)+"("
                        +marks[vertexList.get(index)]+")");
                if (index != vertexList.size()-1) {
                    builder.append(",");
                }
            }
            builder.append(NEWLINE);
        }
        String str = builder.toString();
        return outStr.indexOf(str) > 0;
    }

    @Test
    public void bfsDirectedGraphTest() {
        PrintStream stdOut = System.out;
        try {
            AdjMatrixGraph g = new AdjMatrixGraph(6, true);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(1,3);
            g.addEdge(2,1);
            g.addEdge(2,4);
            g.addEdge(3,2);
            g.addEdge(3,4);
            g.addEdge(3,5);
            g.addEdge(4,5);
            //System.out.println(g.toString());
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(500);
            System.setOut(new PrintStream(outStr));
            GraphSearch.graphTraverseBFS(g);
            List<List<Integer>> bfsLists = new ArrayList<>();
            bfsLists.add(Arrays.asList(0,1,2));
            bfsLists.add(Arrays.asList(1,3));
            bfsLists.add(Arrays.asList(2,4));
            bfsLists.add(Arrays.asList(3,5));
            assertEquals(checkbfsOutput(outStr.toString(), 0,
                                       new int [] {1,2,2,3,3,4},bfsLists),
                                       true);
            totalScore += 5;
            System.setOut(stdOut);
            success("bfsDirectedGraphTest");
        } catch(Exception e) {
            //System.setOut(stdOut);
            failure("bfsDirectedGraphTest", e);
        }
    }

    @Test
    public void bfsUnDirectedGraphTest() {
        PrintStream stdOut = System.out;
        try {
            AdjListGraph g = new AdjListGraph(6, false);
            g.addEdge(0,2);
            g.addEdge(0,4);
            g.addEdge(1,2);
            g.addEdge(1,5);
            g.addEdge(2,3);
            g.addEdge(2,5);
            g.addEdge(3,5);
            g.addEdge(4,5);
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(500);
            System.setOut(new PrintStream(outStr));
            GraphSearch.graphTraverseBFS(g);
            List<List<Integer>> bfsLists = new ArrayList<>();
            bfsLists.add(Arrays.asList(0,2,4));
            bfsLists.add(Arrays.asList(2,1,3,5));
            assertEquals(checkbfsOutput(outStr.toString(),
                    0,
                    new int [] {1,3,2,3,2,3},
                    bfsLists), true);
            totalScore += 5;
            System.setOut(stdOut);
            success("bfsUnDirectedGraphTest");
        } catch(Exception e) {
            System.setOut(stdOut);
            failure("bfsUnDirectedGraphTest", e);
        }
    }

    private static boolean checkdfsOutput(String outStr,
                                          int startVertex,
                                          List<List<Integer>> vertexLists) {
        StringBuilder builder = new StringBuilder();
        builder.append("Start vertex : ");
        builder.append(startVertex);
        builder.append(NEWLINE);
        for (List<Integer> vertexList : vertexLists) {
            for (int index=0; index < vertexList.size(); index++) {
                if (index > 0) {
                    builder.append(" ---> ");
                }
                builder.append(vertexList.get(index));
            }
            builder.append(NEWLINE);
        }
        String str = builder.toString();
        return outStr.indexOf(str) > 0;
    }

    @Test
    public void dfsDirectedGraphTest() {
        PrintStream stdOut = System.out;
        try {
            AdjMatrixGraph g = new AdjMatrixGraph(6, true);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(1,3);
            g.addEdge(2,1);
            g.addEdge(2,4);
            g.addEdge(3,2);
            g.addEdge(3,4);
            g.addEdge(3,5);
            g.addEdge(4,5);
            //System.out.println(g.toString());
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(500);
            System.setOut(new PrintStream(outStr));
            GraphSearch.graphTraverseDFS(g);
            List<List<Integer>> dfsLists = new ArrayList<>();
            dfsLists.add(Arrays.asList(0,1,3,2,4,5));
            assertEquals(checkdfsOutput(outStr.toString(), 0, dfsLists),
                    true);
            totalScore += 4;
            System.setOut(stdOut);
            success("dfsDirectedGraphTest");
        } catch(Exception e) {
            //System.setOut(stdOut);
            failure("dfsDirectedGraphTest", e);
        }
    }

    @Test
    public void dfsUnDirectedGraphTest() {
        PrintStream stdOut = System.out;
        try {
            AdjListGraph g = new AdjListGraph(6, false);
            g.addEdge(0,2);
            g.addEdge(0,4);
            g.addEdge(1,2);
            g.addEdge(1,5);
            g.addEdge(2,3);
            g.addEdge(2,5);
            g.addEdge(3,5);
            g.addEdge(4,5);
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(500);
            System.setOut(new PrintStream(outStr));
            GraphSearch.graphTraverseDFS(g);
            List<List<Integer>> dfsLists = new ArrayList<>();
            dfsLists.add(Arrays.asList(0,2,1,5,3));
            dfsLists.add(Arrays.asList(5,4));
            assertEquals(checkdfsOutput(outStr.toString(),
                    0,
                    dfsLists), true);
            totalScore += 4;
            System.setOut(stdOut);
            success("dfsUnDirectedGraphTest");
        } catch(Exception e) {
            System.setOut(stdOut);
            failure("dfsUnDirectedGraphTest", e);
        }
    }

    private boolean isValidPath(Graph g, List<Integer> pathVertices, int start, int end) {
        if (pathVertices.size() == 0 ) {
            return false;
        }
        if (pathVertices.get(0) != start || pathVertices.get(pathVertices.size()-1) != end) {
            return false;
        }
        int lastVertex = -1;
        for (int vertex : pathVertices) {
            if (lastVertex < 0) {
                lastVertex = vertex;
            } else {
                if (!g.isEdge(lastVertex,vertex)) {
                    return false;
                }
                lastVertex = vertex;
            }
        }
        return true;
    }

    @Test
    public void graphPathDfsUnDirectedTest() {
        try {
            AdjListGraph g = new AdjListGraph(9, false);
            g.addEdge(0,4);
            g.addEdge(1,2);
            g.addEdge(1,6);
            g.addEdge(2,3);
            g.addEdge(2,5);
            g.addEdge(3,5);
            g.addEdge(5,0);
            g.addEdge(7,8);
            List<Integer> pathVertices = GraphSearch.graphPath(g,6,4,false);
            assertEquals(isValidPath(g,pathVertices,6,4), true);
            totalScore += 2;
            pathVertices = GraphSearch.graphPath(g,6,8,false);
            assertEquals(pathVertices.size(),0);
            assertEquals(isValidPath(g,pathVertices,6,8),false);
            totalScore += 1;
            success("graphPathDfsUnDirectedTest");
        } catch(Exception e) {
            failure("graphPathDfsUnDirectedTest", e);
        }
    }

    @Test
    public void graphPathBfsUnDirectedTest() {
        try {
            AdjListGraph g = new AdjListGraph(9, false);
            g.addEdge(0,4);
            g.addEdge(1,2);
            g.addEdge(1,6);
            g.addEdge(2,3);
            g.addEdge(2,5);
            g.addEdge(3,5);
            g.addEdge(5,0);
            g.addEdge(7,8);
            List<Integer> pathVertices = GraphSearch.graphPath(g,3,4,true);
            assertEquals(pathVertices.size(),4);
            assertEquals(isValidPath(g,pathVertices,3,4), true);
            totalScore += 3;
            pathVertices = GraphSearch.graphPath(g,0,8,true);
            assertEquals(pathVertices.size(),0);
            assertEquals(isValidPath(g,pathVertices,0,8),false);
            totalScore += 1;
            success("graphPathBfsUnDirectedTest");
        } catch(Exception e) {
            failure("graphPathBfsUnDirectedTest", e);
        }
    }

    @Test
    public void graphPathBfsDirectedTest() {
        try {
            AdjMatrixGraph g = new AdjMatrixGraph(6, true);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(1,3);
            g.addEdge(2,1);
            g.addEdge(2,4);
            g.addEdge(3,2);
            g.addEdge(3,4);
            g.addEdge(3,5);
            g.addEdge(4,5);
            List<Integer> pathVertices = GraphSearch.graphPath(g,0,5,true);
            assertEquals(pathVertices.size(),4);
            assertEquals(isValidPath(g,pathVertices,0,5), true);
            totalScore += 3;
            pathVertices = GraphSearch.graphPath(g,5,1,true);
            assertEquals(pathVertices.size(),0);
            assertEquals(isValidPath(g,pathVertices,5,1),false);
            totalScore += 1;
            success("graphPathBfsDirectedTest");
        } catch(Exception e) {
            failure("graphPathBfsDirectedTest", e);
        }
    }

    @Test
    public void graphPathDfsDirectedTest() {
        try {
            AdjMatrixGraph g = new AdjMatrixGraph(6, true);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(1,3);
            g.addEdge(2,1);
            g.addEdge(2,4);
            g.addEdge(3,2);
            g.addEdge(3,4);
            g.addEdge(4,5);
            List<Integer> pathVertices = GraphSearch.graphPath(g,0,5,false);
            assertEquals(isValidPath(g,pathVertices,0,5), true);
            totalScore += 2;
            pathVertices = GraphSearch.graphPath(g,5,1,false);
            assertEquals(pathVertices.size(),0);
            assertEquals(isValidPath(g,pathVertices,5,1),false);
            totalScore += 1;
            success("graphPathDfsDirectedTest");
        } catch(Exception e) {
            failure("graphPathDfsDirectedTest", e);
        }
    }

    @Test
    public void cycleTest() {
        try {
            Graph g = new AdjMatrixGraph(15, false);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(1,3);
            g.addEdge(1,4);
            g.addEdge(3,2);
            g.addEdge(3,4);
            g.addEdge(4,5);
            g.addEdge(6,5);
            g.addEdge(5,7);
            g.addEdge(7,6);
            g.addEdge(8,9);
            g.addEdge(9,10);
            g.addEdge(9,11);
            g.addEdge(11,12);
            g.addEdge(12,13);
            g.addEdge(13,14);
            assertEquals(true, GraphSearch.cycleExists(g));
            totalScore += 7;
            g = new AdjListGraph(10, false);
            g.addEdge(0,1);
            g.addEdge(1,2);
            g.addEdge(2,3);
            g.addEdge(3,4);
            g.addEdge(3,5);
            g.addEdge(6,7);
            g.addEdge(7,8);
            g.addEdge(6,9);
            assertEquals(false, GraphSearch.cycleExists(g));
            totalScore += 7;
            success("cycleTest");
        } catch(Exception e) {
            failure("cycleTest", e);
        }
    }

    @Test
    public void oddCycleTest() {
        try {
            Graph g = new AdjListGraph(14, false);
            g.addEdge(0,1);
            g.addEdge(1,2);
            g.addEdge(2,3);
            g.addEdge(3,1);
            g.addEdge(3,4);
            g.addEdge(4,5);
            g.addEdge(5,6);
            g.addEdge(6,3);
            g.addEdge(0,6);
            g.addEdge(7,8);
            g.addEdge(8,9);
            g.addEdge(9,10);
            g.addEdge(10,7);
            g.addEdge(11,12);
            g.addEdge(12,13);
            assertEquals(true, GraphSearch.oddCycleExists(g));
            totalScore += 10;
            g = new AdjListGraph(10, false);
            g.addEdge(0,1);
            g.addEdge(1,2);
            g.addEdge(2,3);
            g.addEdge(3,4);
            g.addEdge(4,5);
            g.addEdge(5,0);
            g.addEdge(6,7);
            g.addEdge(7,8);
            g.addEdge(8,9);
            g.addEdge(9,6);
            assertEquals(false, GraphSearch.oddCycleExists(g));
            totalScore += 8;
            success("oddCycleTest");
        } catch(Exception e) {
            failure("oddCycleTest", e);
        }
    }

    @Test
    public void diameterTest() {
        try {
            Graph tree = new AdjListGraph(14, false);
            tree.addEdge(0,1);
            tree.addEdge(1,2);
            tree.addEdge(2,3);
            tree.addEdge(3,4);
            tree.addEdge(2,5);
            tree.addEdge(5,6);
            tree.addEdge(4,8);
            assertEquals(true, GraphSearch.diameter(tree) == 5);
            totalScore += 4;
            tree = new AdjListGraph(10, false);
            tree.addEdge(0,1);
            tree.addEdge(1,2);
            tree.addEdge(2,3);
            tree.addEdge(3,4);
            tree.addEdge(4,5);
            tree.addEdge(6,2);
            tree.addEdge(7,8);
            tree.addEdge(8,9);
            tree.addEdge(9,6);
            assertEquals(false, GraphSearch.diameter(tree) == 6);
            totalScore += 4;
            success("diameterTest");
        } catch(Exception e) {
            failure("diameterTest", e);
        }
    }

    //@Test
    public void cycleExistsDirectTest() {
        try {
            Graph g = new AdjMatrixGraph(8,true);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(0,3);
            g.addEdge(1,2);
            g.addEdge(1,3);
            g.addEdge(2,0);
            g.addEdge(3,2);
            g.addEdge(4,3);
            g.addEdge(4,6);
            g.addEdge(5,7);
            g.addEdge(6,5);
            g.addEdge(7,5);
            g.addEdge(7,6);
            assertEquals(true, GraphSearch.cycleExistsDirect(g));
            totalScore += 6;
            g = new AdjListGraph(6,true);
            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(2,1);
            g.addEdge(1,3);
            g.addEdge(2,3);
            g.addEdge(2,4);
            g.addEdge(3,4);
            g.addEdge(3,5);
            g.addEdge(4,5);
            assertEquals(false, GraphSearch.cycleExistsDirect(g));
            totalScore += 4;
            success("cycleExistsDirectTest");
        } catch(Exception e) {
            failure("cycleExistsDirectTest", e);
        }
    }


}
