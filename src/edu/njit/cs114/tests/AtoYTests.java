package edu.njit.cs114.tests;

import edu.njit.cs114.recursivebacktracksearch.AtoY;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Author: Ravi Varadarajan
 * Date created: 2/6/2024
 */
public class AtoYTests extends UnitTests {

    private static final String NEWLINE = System.getProperty("line.separator");

    private static class PairInt {
        int x, y;
        public PairInt(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public boolean adjacent(PairInt cell, boolean allowDiagonalNeghbors) {
            if (allowDiagonalNeghbors) {
                return ((Math.abs(cell.x - x) == 1 && (cell.y == y)) ||
                        (Math.abs(cell.y - y) == 1 && (cell.x == x)) ||
                        (Math.abs(cell.x - x) == 1 && Math.abs(cell.y - y) == 1));
            } else {
                return ((Math.abs(cell.x - x) == 1 && (cell.y == y)) ||
                        (Math.abs(cell.y - y) == 1 && (cell.x == x)));
            }
        }
    }

    private static ByteArrayInputStream setInput(char [] [] arr,
                                                 boolean allowDiagonalNeighbors) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i < arr.length; i++) {
            for (int j=0; j < arr[i].length; j++) {
                builder.append(arr[i][j] == 0 ? 'z' : arr[i][j]);
            }
            builder.append("\n");
        }
        builder.append(""+allowDiagonalNeighbors+"\n");
        return new ByteArrayInputStream(builder.toString().getBytes());
    }

    private static boolean getOutput(String outStr, boolean allowDiagonalNeighbors) {
        String str1 = outStr;
        if (str1.startsWith("Enter")) {
            str1 = str1.substring(str1.indexOf("\n")+1);
        }
        if (str1.startsWith("Are diagonal neighbors")) {
            str1 = str1.substring(str1.indexOf("\n")+1);
        }
        if (str1.startsWith("Printing the solution")) {
            str1 = str1.substring(str1.indexOf("\n")+1);
            PairInt [] posArr = new PairInt[25];
            int idx = 0;
            String atoy = "abcdefghijklmnopqrstuvwxy";
            char ch = NEWLINE.charAt(0);
            for (int i=0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    while (NEWLINE.indexOf(ch) >= 0) {
                        ch = str1.charAt(idx++);
                    }
                    if (atoy.indexOf(ch) < 0) {
                        return false;
                    }
                    posArr[(int) ch - (int) ('a')] = new PairInt(i, j);
                    ch = str1.charAt(idx++);
                }
            }
            // check array
            for (int i=1; i < posArr.length; i++) {
                if (!posArr[i].adjacent(posArr[i-1],allowDiagonalNeighbors)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Test
    public void Test1() {
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;
        try {
            char[][] inArr = new char[5][5];
            inArr[1][1] = 'u';
            inArr[1][2] = 't';
            inArr[1][3] = 'a';
            inArr[2][1] = 'v';
            inArr[2][3] = 'r';
            inArr[3][1] = 'w';
            inArr[3][2] = 'x';
            inArr[3][3] = 'y';
            System.setIn(setInput(inArr,false));
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            assertEquals(getOutput(outStr.toString(),false), true);
            totalScore += 8;
            success("Test1");
        } catch (Exception e) {
            System.setOut(stdOut);
            failure("Test1", e);
        } finally {
            System.setIn(stdIn);
        }
    }

    @Test
    public void Test2() {
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;
        try {
            char[][] inArr = new char[5][5];
            inArr[0][4] = 'm';
            inArr[2][1] = 'f';
            inArr[3][3] = 'a';
            inArr[4][4] = 'u';
            System.setIn(setInput(inArr,false));
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            assertEquals(getOutput(outStr.toString(),false), true);
            totalScore += 10;
            success("Test2");
        } catch (Exception e) {
            System.setOut(stdOut);
            failure("Test2", e);
        } finally {
            System.setIn(stdIn);
        }
    }

    @Test
    public void Test3() {
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;
        try {
            char[][] inArr = new char[5][5];
            inArr[0][0] = 'a';
            inArr[0][2] = 'c';
            inArr[0][4] = 'e';
            inArr[1][4] = 'f';
            inArr[1][2] = 'h';
            inArr[1][1] = 'i';
            inArr[1][0] = 'j';
            inArr[2][2] = 'm';
            inArr[3][2] = 'r';
            inArr[4][4] = 'y';
            System.setIn(setInput(inArr,false));
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            assertEquals(getOutput(outStr.toString(),false), true);
            totalScore += 8;
            success("Test3");
        } catch (Exception e) {
            System.setOut(stdOut);
            failure("Test3", e);
        } finally {
            System.setIn(stdIn);
        }
    }

    @Test
    public void Test4() {
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;
        try {
            char[][] inArr = new char[5][5];
            inArr[2][2] = 'a';
            System.setIn(setInput(inArr,false));
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            //  no solution
            assertEquals(getOutput(outStr.toString(),false), true);
            totalScore += 5;
            success("Test4");
        } catch (Exception e) {
            System.setOut(stdOut);
            failure("Test4", e);
        } finally {
            System.setIn(stdIn);
        }
    }


    @Test
    public void Test5() {
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;
        try {
            char[][] inArr = new char[5][5];
            inArr[0][0] = 'a';
            inArr[1][0] = 'b';
            inArr[1][1] = 'c';
            inArr[2][1] = 'd';
            inArr[2][2] = 'e';
            inArr[3][2] = 'f';
            inArr[3][3] = 'g';
            inArr[4][3] = 'h';
            System.setIn(setInput(inArr,false));
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            //  no solution
            assertEquals(getOutput(outStr.toString(),false), false);
            totalScore += 5;
            System.setIn(setInput(inArr,true));
            outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            // no solution even if diagonal neighbors are allowed
            assertEquals(getOutput(outStr.toString(),true), false);
            totalScore += 3;
            success("Test5");
        } catch (Exception e) {
            failure("Test5", e);
        } finally {
            System.setIn(stdIn);
        }
    }


    @Test
    public void Test6() {
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;
        try {
            char[][] inArr = new char[5][5];
            inArr[3][3] = 'a';
            inArr[3][2] = 'b';
            inArr[3][1] = 'c';
            inArr[2][1] = 'd';
            inArr[1][1] = 'e';
            inArr[1][2] = 'f';
            inArr[1][3] = 'g';
            inArr[2][3] = 'h';
            System.setIn(setInput(inArr,false));
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            //  no solution
            assertEquals(getOutput(outStr.toString(),false), false);
            totalScore += 5;
            System.setIn(setInput(inArr,true));
            outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            // no solution even if diagonal neighbors are allowed
            assertEquals(getOutput(outStr.toString(),true), false);
            totalScore += 3;
            success("Test6");
        } catch (Exception e) {
            System.setOut(stdOut);
            failure("Test6", e);
        } finally {
            System.setIn(stdIn);
        }
    }

    @Test
    public void Test7() {
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;
        try {
            char[][] inArr = new char[5][5];
            inArr[0][2] = 'c';
            inArr[0][4] = 'e';
            inArr[1][4] = 'f';
            inArr[1][2] = 'h';
            inArr[1][1] = 'i';
            inArr[1][0] = 'j';
            inArr[3][2] = 'r';
            inArr[4][4] = 'y';
            System.setIn(setInput(inArr,false));
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            assertEquals(getOutput(outStr.toString(),false), true);
            totalScore += 9;
            System.setIn(setInput(inArr,true));
            outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            assertEquals(getOutput(outStr.toString(),true), true);
            totalScore += 5;
            success("Test7");
        } catch (Exception e) {
            System.setOut(stdOut);
            failure("Test7", e);
        } finally {
            System.setIn(stdIn);
        }
    }

    @Test
    public void Test8() {
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;
        try {
            char[][] inArr = new char[5][5];
            inArr[0][1] = 'b';
            inArr[1][1] = 'c';
            inArr[1][2] = 'd';
            inArr[2][2] = 'e';
            inArr[2][3] = 'f';
            inArr[3][3] = 'g';
            inArr[3][4] = 'h';
            System.setIn(setInput(inArr,false));
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            assertEquals(getOutput(outStr.toString(),false), false);
            totalScore += 9;
            success("Test8");
        } catch (Exception e) {
            failure("Test8", e);
        } finally {
            System.setIn(stdIn);
        }
    }

    @Test
    public void Test9() {
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;
        try {
            char[][] inArr = new char[5][5];
            inArr[1][1] = 'u';
            inArr[1][2] = 't';
            inArr[1][3] = 'a';
            inArr[2][1] = 'v';
            inArr[2][3] = 'y';
            inArr[3][1] = 'w';
            inArr[3][2] = 'x';
            inArr[3][3] = 'r';
            System.setIn(setInput(inArr,false));
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            // no solution if no diagonal neighbors allowed
            assertEquals(getOutput(outStr.toString(),false), false);
            System.setIn(setInput(inArr,true));
            outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            // solution if diagonal neighbors allowed
            assertEquals(getOutput(outStr.toString(),true), true);
            totalScore += 10;
            success("Test9");
        } catch (Exception e) {
            System.setOut(stdOut);
            failure("Test9", e);
        } finally {
            System.setIn(stdIn);
        }
    }

    @Test
    public void Test10() {
        InputStream stdIn = System.in;
        PrintStream stdOut = System.out;
        try {
            char[][] inArr = new char[5][5];
            inArr[1][3] = 'c';
            inArr[2][1] = 'g';
            inArr[2][2] = 'h';
            inArr[2][3] = 'd';
            System.setIn(setInput(inArr,false));
            ByteArrayOutputStream outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            // no solution if no diagonal neighbors allowed
            assertEquals(getOutput(outStr.toString(),false), false);
            System.setIn(setInput(inArr,true));
            outStr = new ByteArrayOutputStream(100);
            System.setOut(new PrintStream(outStr));
            AtoY.main(new String[0]);
            System.setIn(stdIn);
            System.setOut(stdOut);
            // solution if diagonal neighbors allowed
            assertEquals(getOutput(outStr.toString(),true), true);
            totalScore += 10;
            success("Test10");
        } catch (Exception e) {
            System.setOut(stdOut);
            failure("Test10", e);
        } finally {
            System.setIn(stdIn);
        }
    }



}
