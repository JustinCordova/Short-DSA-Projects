package edu.njit.cs114.maze;


import java.awt.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Class that solves maze problems with backtracking.
 *
 * @author Koffman and Wolfgang
 * Modified by Ravi Varadarajan for CS 114
 **/
public class Maze1 {

    public static Color PATH = Color.green;
    public static Color BACKGROUND = Color.white;
    public static Color NON_BACKGROUND = Color.red;
    public static Color TEMPORARY = Color.black;

    /** The maze */
    private TwoDimGrid maze;

    private int cnt;

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

    private static boolean inStack(Stack<Cell> pathStack, int col, int row) {
        Cell target = new Cell(col, row);
        return pathStack.contains(target);
    }

    public Maze1(TwoDimGrid m) {
        maze = m;
    }

    /** Wrapper method. */
    public boolean findMazePath(int startCol, int startRow, int destCol, int destRow) {
        cnt = 0; // keeps track of order of cell visits
        return findMazePathAux(startCol, startRow, destCol, destRow);
    }

    /**
     * Attempts to find a maze path recursively through point (x, y) to destination cell (destCol,destRow)
     *
     * @pre Possible path cells are initially in NON_BACKGROUND color
     * @post If a path is found, all cells on it are set to the PATH color; all
     *       cells that were visited but are not on the path are in the TEMPORARY
     *       color.
     * @param x
     *            The x-coordinate of current point
     * @param y
     *            The y-coordinate of current point
     * @param destCol
     *            The x-coordinate of destination cell
     * @param destRow
     *            The y-coordinate of destination cell
     * @return If a path through (x, y) is found, true; otherwise, false
     *         If a path is found, color the cells in the path with PATH color
     *         Also number cells in the order in which they are visited
     */
    public boolean findMazePathAux(int x, int y, int destCol, int destRow) {
        if (y >= maze.getNRows() || x >= maze.getNCols() || x < 0 || y < 0) {
            // no path from (x,y) to destination
            return false;
        }
        /*
         * To BE COMPLETED (base and recursion cases)
         */
        //check color of (x,y) if it is BACKGROUND or TEMPORARY, return false
        Color CC = maze.getColor(x, y);
        if (CC.equals(BACKGROUND) || CC.equals(TEMPORARY)) {
            return false;
        }

        //Always increment cnt before setting the label
        cnt++;
        maze.setLabel(x, y, Integer.toString(cnt));

        // if (x,y) = (destCol, destRow), color(x,y) PATH (use maze.recolor() method), return true
        if (x == destCol && y == destRow) {
            maze.recolor(x, y, PATH);
            return true;
        }

        //color (x,y) temporary color
        maze.recolor(x, y, TEMPORARY);

        //make a recursive call for neighbor (x,y-1) and check result...
        if (findMazePathAux(x, y - 1, destCol, destRow)) {  // Up
            // Change color of (x, y) to PATH and return true
            maze.recolor(x, y, PATH);
            return true;
        }

        //If true, change color of (x,y) to PATH and return true
        //If false, try the 3 other neighboring cells (x,y+1), (x-1,y), (x+1,y)
        // findMazePathAux(x,y-1, destCol, destRow)
        //If for all neighbors return false, return false.
        if (findMazePathAux(x, y + 1, destCol, destRow) ||
                findMazePathAux(x - 1, y, destCol, destRow) ||
                findMazePathAux(x + 1, y, destCol, destRow)) {
            maze.recolor(x, y, PATH);
            return true;
        }
        return false;
    }

    /* Wrapper method for finding shortest path **/
    public boolean findMazeShortestPath(int startCol, int startRow, int destCol, int destRow) {
        Stack<Cell> pathStack = new Stack<>();
        pathStack.push(new Cell(startCol,startRow));
        ArrayList<Cell> shortestPath = findMazeShortestPathAux(startCol, startRow,
                destCol, destRow, pathStack);
        int cnt = 0;
        if (!shortestPath.isEmpty()) {
            /**
             * TO BE COMPLETED (color shortest path green with labels indicating
             *    the order of cells in this path)
             */
            for (Cell cell : shortestPath) {
                maze.recolor(cell.col, cell.row, PATH);
                maze.setLabel(cell.col, cell.row, Integer.toString(++cnt));
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to find shortest path through point (x, y).
     *
     * @pre Possible path cells are initially in NON_BACKGROUND color
     * @post If a path is found, all cells on it are set to the PATH color; all
     *       cells that were visited but are not on the path are in the TEMPORARY
     *       color.
     * @param x
     *            The x-coordinate of current point
     * @param y
     *            The y-coordinate of current point
     * @param pathStack
     *            Stack that contains the current path found so far
     * @return If a path through (x, y) is found return shortest path; otherwise, return empty path
     */
    public ArrayList<Cell> findMazeShortestPathAux(int x, int y, int destCol, int destRow,
                                                   Stack<Cell> pathStack) {
        if (y >= maze.getNRows() || x >= maze.getNCols() || x < 0 || y < 0 ||
                maze.getColor(x, y) == BACKGROUND) {
            return new ArrayList<>();
        }
        /**
         * TO BE COMPLETED (base case)
         */
        // Base case
        if (x == destCol && y == destRow) {
            return new ArrayList<>(pathStack);
        }

        maze.recolor(x, y, TEMPORARY);
        ArrayList<Cell> shortestPath = new ArrayList<>();
        /**
         * To BE COMPLETED (recursion cases)
         */
        // Recursion
        for (int[] moves : move) {
            int ax = x + moves[0];
            int ay = y + moves[1];

            // Check if cell is valid
            if (val(ax, ay, pathStack)) {
                pathStack.push(new Cell(ax, ay));

                // Recursive (Push before/Pop after)
                ArrayList<Cell> nPath = findMazeShortestPathAux(ax, ay, destCol, destRow, pathStack);
                pathStack.pop();
                // Update shortest path
                if (!nPath.isEmpty() && (shortestPath.isEmpty() || nPath.size() < shortestPath.size())) {
                    shortestPath.clear();
                    shortestPath.addAll(nPath);
                }
            }
        }
        maze.recolor(x, y, TEMPORARY);
        return shortestPath;
    }

    private boolean val(int bx, int by, Stack<Cell> pathStack) {
        return bx >= 0 && bx < maze.getNCols() && by >= 0 && by < maze.getNRows() &&
                maze.getColor(bx, by) != BACKGROUND && !inStack(pathStack, bx, by);
    }
    private static final int[][] move = {
            {0, 1},
            {1, 0},
            {0, -1},
            {-1, 0}
    };


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