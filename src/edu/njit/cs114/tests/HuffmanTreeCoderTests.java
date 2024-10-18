package edu.njit.cs114.tests;

import edu.njit.cs114.huffmantreecoder.HuffmanTreeCoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/16/2024
 */
public class HuffmanTreeCoderTests extends UnitTests {

    private int nComps = 0;
    private HuffmanTestComparator comp = new HuffmanTestComparator();

    private Field rootFld;

    private class HuffmanTestComparator extends HuffmanTreeCoder.HuffmanNodeComparator {
        @Override
        public int compare(HuffmanTreeCoder.HuffmanTreeNode t1, HuffmanTreeCoder.HuffmanTreeNode t2) {
            nComps++;
            return super.compare(t1,t2);
        }
    }

    @BeforeEach
    public void init() throws Exception {
        nComps = 0;
        Class cl = Class.forName("edu.njit.cs114.huffmantreecoder.HuffmanTreeCoder");
        rootFld = cl.getDeclaredField("root");
        rootFld.setAccessible(true);
        cl = Class.forName("edu.njit.cs114.huffmantreecoder.HuffmanTreeCoder$HuffmanTreeNode");
    }

    private int subtreeHeight(HuffmanTreeCoder.HuffmanTreeNode localRoot) {
        if (localRoot == null) {
            return 0;
        }
        return 1 + Math.max(subtreeHeight(localRoot.leftChild()),
                            subtreeHeight(localRoot.rightChild()));
    }

    private int getTreeHeight(HuffmanTreeCoder coder) {
        try {
            Object root = rootFld.get(coder);
            return subtreeHeight((HuffmanTreeCoder.HuffmanTreeNode) root);
        } catch(Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Test
    public void buildTreeTest1() {
        try {
            Map<Character,Double> freqMap = new TreeMap<>();
            freqMap.put('a', 20d);
            freqMap.put('b', 25d);
            HuffmanTreeCoder coder = new HuffmanTreeCoder(comp, freqMap);
            assertEquals(1, nComps);
            totalScore += 3;
            assertEquals(2, getTreeHeight(coder));
            totalScore += 2;
            success("buildTreeTest1");
        } catch (Exception e) {
            failure("buildTreeTest1", e);
        }
    }

    @Test
    public void buildTreeTest2() {
        try {
            Map<Character,Double> freqMap = new TreeMap<>();
            freqMap.put('C', 32d);
            freqMap.put('D', 42d);
            freqMap.put('E', 120d);
            freqMap.put('K', 7d);
            freqMap.put('L', 42d);
            freqMap.put('M', 24d);
            freqMap.put('U', 37d);
            freqMap.put('Z', 2d);
            HuffmanTreeCoder coder = new HuffmanTreeCoder(comp, freqMap);
            //System.out.println("nComps="+nComps);
            assertEquals(7, getTreeHeight(coder));
            totalScore += 5;
            assertEquals(46, nComps);
            totalScore += 3;
            success("buildTreeTest2");
        } catch (Exception e) {
            failure("buildTreeTest2", e);
        }
    }

    @Test
    public void buildTreeTest3() {
        try {
            Map<Character,Double> freqMap = new TreeMap<>();
            int [] freqArr = new int [] {64, 13, 22, 32, 103, 21, 15, 47, 57, 1, 5, 32, 20, 57, 63, 15,
                    1, 48, 51, 80, 23, 8, 18, 1, 16, 1, 186};
            for (int i = (int) 'a'; i < (int) 'z'; i++) {
                freqMap.put((char) i, (double) freqArr[i - (int) 'a']);
            }
            freqMap.put(' ', 186d);
            HuffmanTreeCoder coder = new HuffmanTreeCoder(comp, freqMap);
            assertEquals(11, getTreeHeight(coder));
            totalScore += 6;
            //System.out.println("nComps="+nComps);
            assertEquals(true, nComps <= 350);
            totalScore += 3;
            success("buildTreeTest3");
        } catch (Exception e) {
            failure("buildTreeTest3", e);
        }
    }

    private boolean checkPrefix(String code1, String code2) {
        int i=0, j=0;
        String shortCode = code1.length() <= code2.length() ? code1 : code2;
        String longCode = code1.length() > code2.length() ? code1 : code2;
        while (i < shortCode.length()) {
            if (shortCode.charAt(i) == longCode.charAt(j)) {
                i++;
                j++;
            } else {
                return true;
            }
        }
        return false;
    }

    private boolean checkPrefixCode(String [] codes) {
        for (int i=0; i < codes.length; i++) {
            for (int j=i+1; j < codes.length; j++) {
                if (!checkPrefix(codes[i], codes[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkPrefixCodes(HuffmanTreeCoder coder, Set<Character> set) {
        String [] encodings = new String[set.size()];
        int i=0;
        for (Character ch : set) {
            encodings[i++] = coder.encodeBitString(ch.toString());
        }
        return checkPrefixCode(encodings);
    }

    private int maxCodeLength(HuffmanTreeCoder coder, Set<Character> set) {
        int maxCodeLen = 0;
        for (Character ch : set) {
            String code = coder.encodeBitString(ch.toString());
            if (code.length() > maxCodeLen) {
                maxCodeLen = code.length();
            }
        }
        return maxCodeLen;
    }

    private double averageCodeLength(HuffmanTreeCoder coder,
                                        Map<Character,Double> freqMap) {
         double avgCodeLength = 0.0;
         for (Map.Entry<Character, Double> entry : freqMap.entrySet()) {
            String code = coder.encodeBitString(entry.getKey().toString());
            avgCodeLength += (code.length() * entry.getValue());
        }
         return avgCodeLength / freqMap.size();
    }

    @Test
    public void encodeCharsTest1() {
        try {
            Map<Character,Double> freqMap = new TreeMap<>();
            freqMap.put('C', 32d);
            freqMap.put('D', 42d);
            freqMap.put('E', 120d);
            freqMap.put('K', 7d);
            freqMap.put('L', 42d);
            freqMap.put('M', 24d);
            freqMap.put('U', 37d);
            freqMap.put('Z', 2d);
            HuffmanTreeCoder coder = new HuffmanTreeCoder(freqMap);
            assertEquals(true, checkPrefixCodes(coder, freqMap.keySet()));
            totalScore += 2;
            assertEquals(6, maxCodeLength(coder, freqMap.keySet()));
            totalScore += 4;
            //System.out.println(averageCodeLength(coder, freqMap));
            assertEquals(approxEquals(98.125, averageCodeLength(coder, freqMap)), true);
            totalScore += 3;
            success("encodeCharsTest1");
        } catch (Exception e) {
            failure("encodeCharsTest1", e);
        }
    }

    @Test
    public void encodeCharsTest2() {
        try {
            Map<Character,Double> freqMap = new TreeMap<>();
            int [] freqArr = new int [] {64, 13, 22, 32, 103, 21, 15, 47, 57, 1, 5, 32, 20, 57, 63, 15,
                    1, 48, 51, 80, 23, 8, 18, 1, 16, 1, 186};
            for (int i = (int) 'a'; i <= (int) 'z'; i++) {
                freqMap.put((char) i, (double) freqArr[i - (int) 'a']);
            }
            freqMap.put(' ', 186d);
            HuffmanTreeCoder coder = new HuffmanTreeCoder(freqMap);
            assertEquals(true, checkPrefixCodes(coder, freqMap.keySet()));
            totalScore += 2;
            assertEquals(10, maxCodeLength(coder, freqMap.keySet()));
            totalScore += 4;
            //System.out.println(averageCodeLength(coder, freqMap));
            assertEquals(approxEquals(152.7407, averageCodeLength(coder, freqMap)), true);
            totalScore += 2;
            success("encodeCharsTest2");
        } catch (Exception e) {
            failure("encodeCharsTest2", e);
        }
    }

    @Test
    public void decodeBitStringTest1() {
        try {
            Map<Character,Double> freqMap = new TreeMap<>();
            int [] freqArr = new int [] {64, 13, 22, 32, 103, 21, 15, 47, 57, 1, 5, 32, 20, 57, 63, 15,
                    1, 48, 51, 80, 23, 8, 18, 1, 16, 1};
            for (int i = (int) 'a'; i <= (int) 'z'; i++) {
                freqMap.put((char) i, (double) freqArr[i - (int) 'a']);
            }
            freqMap.put(' ', 186d);
            freqMap.put('-', 45d);
            HuffmanTreeCoder coder = new HuffmanTreeCoder(freqMap);
            String str = "abracadabra";
            assertEquals(str, coder.decodeBitString(coder.encodeBitString(str)));
            totalScore += 5;
            str = "this course is interesting";
            assertEquals(str, coder.decodeBitString(coder.encodeBitString(str)));
            totalScore += 5;
            str = "how razorback-jumping frogs can level six piqued gymnasts";
            assertEquals(str, coder.decodeBitString(coder.encodeBitString(str)));
            totalScore += 5;
            success("decodeBitStringTest1");
        } catch (Exception e) {
            failure("decodeBitStringTest1", e);
        }
    }

    @Test
    public void decodeBitStringTest2() {
        try {
            Map<Character,Double> freqMap = new TreeMap<>();
            Random random = new Random();
            for (int i=1; i < 1025; i++) {
                freqMap.put((char) i, (double) random.nextInt(500));
            }
            HuffmanTreeCoder coder = new HuffmanTreeCoder(freqMap);
            StringBuilder builder = new StringBuilder();
            for (int k=0; k < 312; k++) {
                builder.append((char) 1+random.nextInt(1024));
            }
            String str = builder.toString();
            assertEquals(str, coder.decodeBitString(coder.encodeBitString(str)));
            totalScore += 8;
            builder.setLength(0);
            for (int k=1; k < 1025; k++) {
                builder.append((char) 1+random.nextInt(1024));
            }
            str = builder.toString();
            assertEquals(str, coder.decodeBitString(coder.encodeBitString(str)));
            totalScore += 8;
            success("decodeBitStringTest2");
        } catch (Exception e) {
            failure("decodeBitStringTest2", e);
        }
    }

    private boolean sameContents(String file1, String file2) {
        char [] buf1 = new char[1024];
        char [] buf2 = new char[1024];
        FileReader rdr1 = null;
        FileReader rdr2 = null;
        int nBytesRead1 = 0;
        int nBytesRead2 = 0;
        int totBytesRead1 = 0;
        int totBytesRead2 = 0;
        try {
            rdr1 = new FileReader(file1);
            rdr2 = new FileReader(file2);
            while (true) {
                nBytesRead1 = rdr1.read(buf1);
                nBytesRead2 = rdr2.read(buf2);
                if (nBytesRead1 <= 0 || nBytesRead2 <= 0) {
                    break;
                }
                totBytesRead1 += nBytesRead1;
                totBytesRead2 += nBytesRead2;
                String str1 = new String(buf1,0,nBytesRead1);
                String str2 = new String(buf2,0,nBytesRead2);
                if (!str1.equals(str2)) {
                    return false;
                }
            }
            if (nBytesRead1 != nBytesRead2) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (rdr1 != null) {
                try {
                    rdr1.close();
                } catch(Exception e) {}
            }
            if (rdr2 != null) {
                try {
                    rdr2.close();
                } catch(Exception e) {}
            }
        }
    }

    @Test
    public void decodeBitStringFromRdrTest1() {
        String inputFile = "testHuffman.txt";
        String encodedFile = "huffmancoderTest_encoded.txt";
        String decodedFile = "huffmancoderTest_decoded.txt";
        BufferedReader rdr = null;
        FileWriter writer = null;
        try {
            Map<Character,Double> freqMap = new TreeMap<>();
            HuffmanTreeCoder coder = new HuffmanTreeCoder(HuffmanTreeCoder.getFreqMap(inputFile));
            coder.compress(inputFile, encodedFile);
            rdr = new BufferedReader(new FileReader(encodedFile));
            String str = coder.decodeBitString(rdr);
            writer = new FileWriter(decodedFile);
            writer.append(str);
            writer.close();
            assertEquals(true, sameContents(decodedFile, inputFile));
            totalScore += 10;
            success("decodeBitStringFromRdrTest1");
        } catch (Exception e) {
            failure("decodeBitStringFromRdrTest1", e);
        } finally {
            if (rdr != null) {
                try {
                    rdr.close();
                } catch(Exception e) {}
            }
        }
    }

    @Test
    public void decodeBitStringFromRdrTest2() {
        String inputFile = "src/edu/njit/cs114/HuffmanTreeCoder.java";
        String encodedFile = "huffmancoderTest_encoded.txt";
        String decodedFile = "huffmancoderTest_decoded.txt";
        BufferedReader rdr = null;
        FileWriter writer = null;
        try {
            Map<Character,Double> freqMap = new TreeMap<>();
            HuffmanTreeCoder coder = new HuffmanTreeCoder(HuffmanTreeCoder.getFreqMap(inputFile));
            coder.compress(inputFile, encodedFile);
            rdr = new BufferedReader(new FileReader(encodedFile));
            String str = coder.decodeBitString(rdr);
            writer = new FileWriter(decodedFile);
            writer.append(str);
            writer.close();
            assertEquals(true, sameContents(decodedFile, inputFile));
            totalScore += 10;
            success("decodeBitStringFromRdrTest2");
        } catch (Exception e) {
            failure("decodeBitStringFromRdrTest2", e);
        } finally {
            if (rdr != null) {
                try {
                    rdr.close();
                } catch(Exception e) {}
            }
        }
    }

}
