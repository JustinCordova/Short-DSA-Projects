package edu.njit.cs114.maze;


import edu.njit.cs114.graphs.AdjListGraph;
import edu.njit.cs114.graphs.Graph;
import edu.njit.cs114.graphs.GraphSearch;

import java.awt.Color;
import java.util.List;

/**
 * Class that solves maze problems with backtracking.
 *
 * @originalcode Koffman and Wolfgang
 * Modified by Ravi Varadarajan for CS 114
 **/
public class Maze {

    public static Color PATH = Color.green;
    public static Color BACKGROUND = Color.white;
    public static Color NON_BACKGROUND = Color.red;
    public static Color TEMPORARY = Color.black;

    /** The maze */
    private TwoDimGrid maze;


    public Maze(TwoDimGrid m) {
        maze = m;
    }

    private static class Cell {
        public final int col, row;
        public Cell(int col, int row) {
            this.col = col;
            this.row = row;
        }
        public boolean equals(Object obj) {
            if (!(obj instanceof Cell)) {
                return false;
            }
            Cell other = (Cell) obj;
            return this.row == other.row && this.col == other.col;
        }

        public String toString() {
            return "(" + col + "," + row + ")";
        }
    }

    private int toVertexNum(int col, int row) {
        return maze.getNRows()*col + row;
    }

    private Cell toCell(int vertexNum) {
        return new Cell(vertexNum/maze.getNRows(), vertexNum % maze.getNRows());
    }

    private Graph createCellGraph() throws Exception {
        Graph cellGraph = new AdjListGraph(maze.getNCols()*maze.getNRows(), false);
        for (int col = 0; col < maze.getNCols(); col++) {
            for (int row = 0; row < maze.getNRows(); row++) {
                if (maze.getColor(col, row) == NON_BACKGROUND) {
                    // determine neighbors
                    int fromVertex = toVertexNum(col, row);
                    if (col+1 < maze.getNCols() && maze.getColor(col+1, row) == NON_BACKGROUND) {
                        cellGraph.addEdge(fromVertex, toVertexNum(col+1, row));
                    }
                    if (row+1 < maze.getNRows() && maze.getColor(col, row+1) == NON_BACKGROUND) {
                        cellGraph.addEdge(fromVertex, toVertexNum(col, row+1));
                    }
                }
            }
        }
        return cellGraph;
    }

    /**
     * Attempts to find a maze path from (startCol,startRow) to destination cell (destCol,destRow)
     *
     * @pre Possible path cells are initially in NON_BACKGROUND color
     * @post If a path is found, all cells on it are set to the PATH color; all
     *       cells that were visited but are not on the path are in the TEMPORARY
     *       color.
     * @param startCol
     *            The x-coordinate of start point
     * @param startRow
     *            The y-coordinate of start point
     * @param destCol
     *            The x-coordinate of destination cell
     * @param destRow
     *            The y-coordinate of destination cell
     * @return If a path through (x, y) is found, true; otherwise, false
     *         If a path is found, color the cells in the path with PATH color
     *         Also number cells in the order in which they are visited
     */
    public boolean findMazePath(int startCol, int startRow, int destCol, int destRow) {
        try {
            Graph cellGraph = createCellGraph();
            List<Integer> path = GraphSearch.graphPath(cellGraph, toVertexNum(startCol,startRow),
                    toVertexNum(destCol, destRow), false);
            if (path.size() == 0) {
                return false;
            }
            int cnt = 1;
            for (int vertex : path) {
                Cell cell = toCell(vertex);
                maze.recolor(cell.col, cell.row, PATH);
                maze.setLabel(cell.col, cell.row, "" + cnt);
                cnt++;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Attempts to find shortest maze path from (startCol,startRow) to destination cell (destCol,destRow)
     *
     * @pre Possible path cells are initially in NON_BACKGROUND color
     * @post If a path is found, all cells on it are set to the PATH color; all
     *       cells that were visited but are not on the path are in the TEMPORARY
     *       color.
     * @param startCol
     *            The x-coordinate of start point
     * @param startRow
     *            The y-coordinate of start point
     * @param destCol
     *            The x-coordinate of destination cell
     * @param destRow
     *            The y-coordinate of destination cell
     * @return If a path through (x, y) is found, true; otherwise, false
     *         If a path is found, color the cells in the path with PATH color
     *         Also number cells in the order in which they are visited
     */
    public boolean findMazeShortestPath(int startCol, int startRow, int destCol, int destRow) {
        try {
            Graph cellGraph = createCellGraph();
            List<Integer> path = GraphSearch.graphPath(cellGraph, toVertexNum(startCol,startRow),
                                                toVertexNum(destCol, destRow), true);
            if (path.size() == 0) {
                return false;
            }
            int cnt = 1;
            for (int vertex : path) {
                Cell cell = toCell(vertex);
                maze.recolor(cell.col, cell.row, PATH);
                maze.setLabel(cell.col, cell.row, "" + cnt);
                cnt++;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void resetTemp() {
        maze.recolor(TEMPORARY, BACKGROUND);
    }

    public void resetMaze() {
        resetTemp();
        maze.recolor(PATH, BACKGROUND);
        maze.recolor(NON_BACKGROUND, BACKGROUND);
        maze.clearLabels();
    }

    public void restoreMaze() {
        maze.recolor(PATH, NON_BACKGROUND);
        maze.recolor(TEMPORARY, NON_BACKGROUND);
        maze.clearLabels();
    }
}

