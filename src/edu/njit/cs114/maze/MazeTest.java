package edu.njit.cs114.maze;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;

/**
 * A class to test Maze.java.
 * @author Koffman and Wolfgang
 * Modified by Ravi Varadarajan for CS 114
 */
public class MazeTest extends JFrame {

    // data field
    private TwoDimGrid theGrid; // a 2-D grid of buttons

    public static void main(String[] args) {
        String reply = JOptionPane.showInputDialog("Enter number of rows");
        int nRows = Integer.parseInt(reply);
        reply = JOptionPane.showInputDialog("Enter number of columns");
        int nCols = Integer.parseInt(reply);
        TwoDimGrid aGrid = new TwoDimGrid(nRows, nCols);
        new MazeTest(aGrid);
    }

    // Builds the GUI
    private MazeTest(TwoDimGrid aGrid) {
        theGrid = aGrid;
        getContentPane().add(aGrid, BorderLayout.CENTER);
        JTextArea instruct = new JTextArea(2, 20);
        instruct.setText("Toggle a button to change its color"
                + "\nPress SOLVE or SOLVE_SHORTEST when ready");
        getContentPane().add(instruct, BorderLayout.NORTH);
        JButton solveButton = new JButton("SOLVE");
        solveButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                solve(false);
            }
        });
        JButton solveShortestButton = new JButton("SOLVE_SHORTEST");
        solveShortestButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                solve(true);
            }
        });
        JButton resetButton = new JButton("RESET");
        resetButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                (new Maze1(theGrid)).resetMaze();
            }
        });
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(solveButton);
        bottomPanel.add(solveShortestButton);
        bottomPanel.add(resetButton);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void solve(boolean shortestPath) {
        Maze1 m = new Maze1(theGrid);
        m.restoreMaze();
        String reply = JOptionPane.showInputDialog("Enter start cell as col,row (skip for 0,0)");
        reply = reply.trim();
        int startCol = 0;
        int startRow = 0;
        if (!reply.isEmpty()) {
            String[] toks = reply.split(",");
            startCol = Integer.parseInt(toks[0].trim());
            startRow = Integer.parseInt(toks[1].trim());
        }
        reply = JOptionPane.showInputDialog("Enter destination cell as col,row (skip for "+
                (theGrid.getNCols()-1) + "," + (theGrid.getNRows()-1)+")");
        reply = reply.trim();
        int destCol = theGrid.getNCols()-1;
        int destRow = theGrid.getNRows()-1;
        if (!reply.isEmpty()) {
            String[] toks = reply.split(",");
            destCol = Integer.parseInt(toks[0].trim());
            destRow = Integer.parseInt(toks[1].trim());
        }
        if (shortestPath) {
            boolean found = m.findMazeShortestPath(startCol, startRow, destCol, destRow);
            if (found) {
                JOptionPane.showMessageDialog(null, "Success ! shortest path shown in green; solve or reset maze for another trial");
            } else {
                JOptionPane.showMessageDialog(null, "No path - solve or reset maze and try again");
            }
        } else {
            boolean found = m.findMazePath(startCol, startRow, destCol, destRow);
            if (found) {
                JOptionPane.showMessageDialog(null, "Success ! path shown in green; solve or reset maze for another trial");
            } else {
                JOptionPane.showMessageDialog(null, "No path - reset maze and try again");
            }
        }
    }
}