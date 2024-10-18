package edu.njit.cs114.tests;

import edu.njit.cs114.binsearchtree.BinarySearchTree;
import edu.njit.cs114.binsearchtree.BinTreeInorderNavigator;
import static edu.njit.cs114.binsearchtree.BinarySearchTree.BSTNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/10/2024
 */
public class BalancedBinarySearchTreeTests extends UnitTests {

    private Field sizeFld, heightFld, leftFld, rightFld;
    private Method setLeftMethod, setRightMethod;

    private static class MyKey<K extends Comparable<K>> implements Comparable<MyKey<K>> {

        public static int nComparisons;

        private final K key;

        public MyKey(K key) {
            this.key = key;
        }

        @Override
        public int compareTo(MyKey<K> other) {
            nComparisons++;
            return key.compareTo(other.key);
        }

        public String toString() {
            return key.toString();
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof MyKey)) {
                return false;
            }
            return ((MyKey) obj).key.equals(this.key);
        }

        public static void resetCompCount() {
            nComparisons = 0;
        }

    }


    @BeforeEach
    public void initForEachTest() throws Exception {
        Class cl = Class.forName("edu.njit.cs114.binsearchtree.BinarySearchTree$BSTNode");
        sizeFld = cl.getDeclaredField("size");
        sizeFld.setAccessible(true);
        heightFld = cl.getDeclaredField("height");
        heightFld.setAccessible(true);
        leftFld = cl.getDeclaredField("left");
        leftFld.setAccessible(true);
        rightFld = cl.getDeclaredField("right");
        rightFld.setAccessible(true);
        setLeftMethod = cl.getDeclaredMethod("setLeftChild", new Class [] { cl });
        setLeftMethod.setAccessible(true);
        setRightMethod = cl.getDeclaredMethod("setRightChild", new Class [] { cl });
        setRightMethod.setAccessible(true);
        MyKey.resetCompCount();
    }

    private int getSize(Object node) {
        try {
            return (int) sizeFld.get(node);
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private int getHeight(Object node) throws Exception {
        try {
            return (int) heightFld.get(node);
        } catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    @Test
    public void bstNodeConstructorSizeTest() {
        try {
            BinarySearchTree<MyKey<String>,Integer> bst =
                     new BinarySearchTree<MyKey<String>,Integer>();
            assertEquals(0, bst.size());
            assertEquals(0, MyKey.nComparisons);
            totalScore += 1;
            BinarySearchTree<MyKey<String>,Integer> bst1 =
                     new BinarySearchTree<MyKey<String>,Integer>();
            bst1.insert(new MyKey("gamma"), 10);
            BSTNode<MyKey<String>,Integer> root = bst1.getRoot();
            assertEquals(1, getSize(root));
            totalScore += 1;
            bst1.insert(new MyKey("beta"), 15);
            bst1.insert(new MyKey("pi"), 30);
            assertEquals(3, getSize(root));
            assertEquals(1, getSize(root.leftChild()));
            assertEquals(1, getSize(root.rightChild()));
            totalScore += 1;
            bst1.insert(new MyKey("delta"), 25);
            assertEquals(4, getSize(root));
            assertEquals(2, getSize(root.leftChild()));
            assertEquals(1, getSize(root.leftChild().rightChild()));
            assertEquals(1, getHeight(root.rightChild()));
            totalScore += 1;
            int nComparisons = MyKey.nComparisons;
            assertEquals(4, bst1.size());
            assertEquals(nComparisons, MyKey.nComparisons);
            totalScore += 1;
            success("bstNodeConstructorSizeTest");
        } catch (Exception e) {
            failure("bstNodeConstructorSizeTest", e);
        }
    }

    @Test
    public void bstNodeSetSiblingSizeTest() {
        try {
            BinarySearchTree<String,Integer> bst1 = new BinarySearchTree<>();
            bst1.insert("delta", 45);
            assertEquals(bst1.size(), 1);
            totalScore += 1;
            BinarySearchTree<String,Integer> bst2 = new BinarySearchTree<>();
            bst2.insert("pi", 30);
            bst2.insert("tau", 30);
            bst2.insert("omega", 67);
            assertEquals(bst2.size(), 3);
            BinarySearchTree<String,Integer> bst3 = new BinarySearchTree<>();
            bst3.insert("alpha", 25);
            bst3.insert("gamma", 25);
            assertEquals(bst3.size(), 2);
            setLeftMethod.invoke(bst1.getRoot(), bst3.getRoot());
            assertEquals(bst1.size(), 3);
            totalScore += 1;
            setRightMethod.invoke(bst1.getRoot(), bst2.getRoot());
            assertEquals(bst1.size(), 6);
            totalScore += 1;
            success("bstNodeSetSiblingSizeTest");
        } catch (Exception e) {
            failure("bstNodeSetSiblingSizeTest", e);
        }
    }

    @Test
    public void bstNodeConstructorHeightTest() {
        try {
            BinarySearchTree<MyKey<String>,Integer> bst =
                      new BinarySearchTree<MyKey<String>,Integer>();
            assertEquals(0, bst.height());
            assertEquals(0, MyKey.nComparisons);
            totalScore += 1;
            BinarySearchTree<MyKey<String>,Integer> bst1 =
                    new BinarySearchTree<MyKey<String>,Integer>();
            bst1.insert(new MyKey("gamma"), 10);
            BSTNode<MyKey<String>,Integer> root = bst1.getRoot();
            assertEquals(1, getHeight(root));
            totalScore += 1;
            bst1.insert(new MyKey("beta"), 15);
            assertEquals(2, getHeight(root));
            assertEquals(1, getHeight(root.leftChild()));
            bst1.insert(new MyKey("pi"), 30);
            assertEquals(2, getHeight(root));
            assertEquals(1, getHeight(root.leftChild()));
            assertEquals(1, getHeight(root.rightChild()));
            totalScore += 1;
            bst1.insert(new MyKey("delta"), 25);
            assertEquals(3, getHeight(root));
            assertEquals(2, getHeight(root.leftChild()));
            assertEquals(1, getHeight(root.leftChild().rightChild()));
            assertEquals(1, getHeight(root.rightChild()));
            totalScore += 1;
            int nComparisons = MyKey.nComparisons;
            assertEquals(3, bst1.height());
            assertEquals(nComparisons, MyKey.nComparisons);
            totalScore += 1;
            success("bstNodeConstructorHeightTest");
        } catch (Exception e) {
            failure("bstNodeConstructorHeightTest", e);
        }
    }

    @Test
    public void bstNodeSetSiblingHeightTest() {
        try {
            BinarySearchTree<String,Integer> bst1 = new BinarySearchTree<>();
            bst1.insert("delta", 45);
            assertEquals(bst1.height(), 1);
            totalScore += 1;
            BinarySearchTree<String,Integer> bst2 = new BinarySearchTree<>();
            bst2.insert("pi", 30);
            bst2.insert("tau", 30);
            bst2.insert("omega", 67);
            assertEquals(bst2.height(), 2);
            BinarySearchTree<String,Integer> bst3 = new BinarySearchTree<>();
            bst3.insert("alpha", 25);
            bst3.insert("gamma", 25);
            assertEquals(bst3.height(), 2);
            setLeftMethod.invoke(bst1.getRoot(), bst3.getRoot());
            assertEquals(bst1.height(), 3);
            totalScore += 1;
            setRightMethod.invoke(bst1.getRoot(), bst2.getRoot());
            assertEquals(bst1.height(), 3);
            totalScore += 1;
            success("bstNodeSetSiblingHeightTest");
        } catch (Exception e) {
            failure("bstNodeSetSiblingHeightTest", e);
        }
    }

    @Test
    public void balanceFactorTest() {
        try {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<Integer, Integer>();
            bst.insert(18,18);
            BSTNode<Integer,Integer> root = bst.getRoot();
            assertEquals(0, root.balanceFactor());
            totalScore += 1;
            bst.insert(10,10);
            assertEquals(-1, root.balanceFactor());
            assertEquals(0, root.leftChild().balanceFactor());
            totalScore += 1;
            bst.insert(22,22);
            assertEquals(0, root.balanceFactor());
            assertEquals(0, root.leftChild().balanceFactor());
            assertEquals(0, root.rightChild().balanceFactor());
            bst.insert(5,5);
            assertEquals(-1, root.balanceFactor());
            assertEquals(-1, root.leftChild().balanceFactor());
            bst.insert(12,12);
            assertEquals(-1, root.balanceFactor());
            assertEquals(0, root.leftChild().balanceFactor());
            totalScore += 1;
            bst.insert(25,25);
            assertEquals(0, root.balanceFactor());
            assertEquals(1, root.rightChild().balanceFactor());
            bst.insert(20,20);
            assertEquals(0, root.balanceFactor());
            assertEquals(0, root.rightChild().balanceFactor());
            totalScore += 1;
            BinarySearchTree<Integer, Integer> bst1 = new BinarySearchTree<Integer, Integer>();
            bst1.insert(14,14);
            bst1.insert(15,15);
            setRightMethod.invoke(root.leftChild().rightChild(), bst1.getRoot());
            setRightMethod.invoke(root.leftChild(), root.leftChild().rightChild());
            setLeftMethod.invoke(root, root.leftChild());
            assertEquals(-2, root.balanceFactor());
            assertEquals(2, root.leftChild().balanceFactor());
            assertEquals(2, root.leftChild().rightChild().balanceFactor());
            totalScore += 2;
            success("balanceFactorTest");
        } catch (Exception e) {
            failure("balanceFactorTest", e);
        }
    }

    @Test
    public void isBalancedTest1() {
        try {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<Integer, Integer>();
            bst.insert(18,18);
            bst.insert(10,10);
            bst.insert(22,22);
            bst.insert(5,5);
            bst.insert(12,12);
            bst.insert(25,25);
            bst.insert(20,20);
            BSTNode<Integer,Integer> root = bst.getRoot();
            BinarySearchTree<Integer, Integer> bst1 = new BinarySearchTree<Integer, Integer>();
            bst1.insert(14,14);
            bst1.insert(15,15);
            setRightMethod.invoke(root.leftChild().rightChild(), bst1.getRoot());
            setRightMethod.invoke(root.leftChild(), root.leftChild().rightChild());
            setLeftMethod.invoke(root, root.leftChild());
            assertEquals(false, bst.isBalanced());
            totalScore += 3;
            success("isBalancedTest1");
        } catch (Exception e) {
            failure("isBalancedTest1", e);
        }
    }

    @Test
    public void isBalancedTest2() {
        try {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<Integer, Integer>();
            bst.insert(18,18);
            bst.insert(10,10);
            bst.insert(22,22);
            bst.insert(5,5);
            bst.insert(14,14);
            bst.insert(12,12);
            bst.insert(20,20);
            bst.insert(25,25);
            bst.insert(15,15);
            assertEquals(true, bst.isBalanced());
            totalScore += 3;
            success("isBalancedTest2");
        } catch (Exception e) {
            failure("isBalancedTest2", e);
        }
    }

    private <K extends Comparable<K>,V> boolean equalKeys(List<K> targetKeyList,
                                    List<BSTNode<K,V>> nodeList) {
        if (targetKeyList.size() != nodeList.size()) {
            return false;
        }
        for (int i=0; i < targetKeyList.size(); i++) {
            if (targetKeyList.get(i).compareTo(nodeList.get(i).getKey()) != 0) {
                return false;
            }
        }
        return true;
    }

    private <K extends Comparable<K>,V> boolean equalMyKeys(List<K> targetKeyList,
                                                          List<BSTNode<MyKey<K>,V>> nodeList) {
        if (targetKeyList.size() != nodeList.size()) {
            return false;
        }
        for (int i=0; i < targetKeyList.size(); i++) {
            if (targetKeyList.get(i).compareTo(nodeList.get(i).getKey().key) != 0) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void getLevelsTest() {
        try {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<Integer, Integer>();
            bst.insert(18,18);
            bst.insert(10,10);
            bst.insert(22,22);
            bst.insert(5,5);
            bst.insert(12,12);
            bst.insert(25,25);
            bst.insert(20,20);
            BSTNode<Integer,Integer> root = bst.getRoot();
            BinarySearchTree<Integer, Integer> bst1 = new BinarySearchTree<Integer, Integer>();
            bst1.insert(14,14);
            bst1.insert(15,15);
            setRightMethod.invoke(root.leftChild().rightChild(), bst1.getRoot());
            setRightMethod.invoke(root.leftChild(), root.leftChild().rightChild());
            setLeftMethod.invoke(root, root.leftChild());
            Map<Integer, List<BinarySearchTree.BSTNode<Integer,Integer>>> map = bst.getNodeLevels();
            assertEquals(5, map.size());
            totalScore += 1;
            assertEquals(true, equalKeys(Arrays.asList(18), map.get(0)));
            totalScore += 1;
            assertEquals(true, equalKeys(Arrays.asList(22,10), map.get(1)));
            totalScore += 2;
            assertEquals(true, equalKeys(Arrays.asList(25,20,12,5), map.get(2)));
            totalScore += 2;
            assertEquals(true, equalKeys(Arrays.asList(14), map.get(3)));
            totalScore += 1;
            assertEquals(true, equalKeys(Arrays.asList(15), map.get(4)));
            totalScore += 1;
            success("getLevelsTest");
        } catch (Exception e) {
            failure("getLevelsTest", e);
        }
    }

    @Test
    public void checkSingleRotationOnLeft() {
        try {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<Integer, Integer>();
            bst.insert(60, 1);
            bst.insert(40, 2);
            bst.insert(30, 3);
            assertEquals(bst.isBalanced(), true);
            assertEquals(bst.getRoot().getKey().equals(40), true);
            assertEquals(bst.getRoot().leftChild().getKey().equals(30), true);
            assertEquals(bst.getRoot().rightChild().getKey().equals(60), true);
            totalScore += 3;
            success("checkSingleRotationOnLeft");
        } catch (Exception e) {
            failure("checkSingleRotationOnLeft", e);
        }
    }

    @Test
    public void checkDoubleRotationOnRight() {
        try {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<Integer, Integer>();
            bst.insert(40, 1);
            bst.insert(30, 2);
            bst.insert(60, 3);
            bst.insert(20, 4);
            bst.insert(37, 5);
            bst.insert(75, 6);
            bst.insert(35, 7);
            bst.insert(38, 8);
            assertEquals(bst.getRoot().balanceFactor(), -1);
            BSTNode<Integer,Integer> nodeA = bst.getRoot().leftChild();
            BSTNode<Integer,Integer> nodeB = nodeA.rightChild();
            assertEquals(nodeA.balanceFactor(), +1);
            assertEquals(nodeB.balanceFactor(), 0);
            totalScore += 1;
            bst.insert(32, 9);
            assertEquals(bst.isBalanced(), true);
            assertEquals(bst.getRoot().getKey().equals(40), true);
            assertEquals(bst.getRoot().leftChild().getKey().equals(35), true);
            assertEquals(bst.getRoot().rightChild().getKey().equals(60), true);
            assertEquals(bst.getRoot().leftChild().leftChild().getKey().equals(30), true);
            assertEquals(bst.getRoot().leftChild().rightChild().getKey().equals(37), true);
            assertEquals(bst.getRoot().leftChild().leftChild().leftChild().getKey().equals(20), true);
            assertEquals(bst.getRoot().leftChild().leftChild().rightChild().getKey().equals(32), true);
            assertEquals(bst.getRoot().leftChild().rightChild().rightChild().getKey().equals(38), true);
            totalScore += 4;
            success("checkDoubleRotationOnRight");
        } catch (Exception e) {
            failure("checkDoubleRotationOnRight", e);
        }
    }

    @Test
    public void checkSingleRotationOnRight() {
        try {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<Integer, Integer>();
            bst.insert(40, 1);
            bst.insert(35, 2);
            bst.insert(60, 3);
            bst.insert(30, 4);
            bst.insert(37, 5);
            bst.insert(75, 6);
            bst.insert(20, 7);
            bst.insert(32, 8);
            bst.insert(38, 9);
            BSTNode<Integer,Integer> nodeA = bst.getRoot().leftChild().rightChild();
            BSTNode<Integer,Integer> nodeB = nodeA.rightChild();
            assertEquals(bst.isBalanced(), true);
            assertEquals(bst.getRoot().getKey().equals(40), true);
            assertEquals(nodeA.getKey().equals(37), true);
            assertEquals(nodeB.getKey().equals(38), true);
            assertEquals(nodeA.balanceFactor(), 1);
            assertEquals(nodeB.balanceFactor(), 0);
            totalScore += 2;
            bst.insert(39, 10);
            assertEquals(bst.isBalanced(), true);
            assertEquals(bst.getRoot().getKey().equals(40), true);
            assertEquals(bst.getRoot().leftChild().getKey().equals(35), true);
            assertEquals(bst.getRoot().rightChild().getKey().equals(60), true);
            assertEquals(bst.getRoot().leftChild().leftChild().getKey().equals(30), true);
            assertEquals(bst.getRoot().leftChild().rightChild().getKey().equals(38), true);
            assertEquals(bst.getRoot().leftChild().leftChild().leftChild().getKey().equals(20), true);
            assertEquals(bst.getRoot().leftChild().leftChild().rightChild().getKey().equals(32), true);
            assertEquals(bst.getRoot().leftChild().rightChild().leftChild().getKey().equals(37), true);
            assertEquals(bst.getRoot().leftChild().rightChild().rightChild().getKey().equals(39), true);
            totalScore += 4;
            success("checkSingleRotationOnLeft");
        } catch (Exception e) {
            failure("checkSingleRotationOnLeft", e);
        }
    }

    @Test
    public void checkDoubleRotationOnLeft() {
        try {
            BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<Integer, Integer>();
            bst.insert(40, 1);
            bst.insert(35, 2);
            bst.insert(60, 3);
            bst.insert(30, 4);
            bst.insert(38, 5);
            bst.insert(75, 6);
            bst.insert(20, 7);
            bst.insert(32, 8);
            bst.insert(37, 9);
            bst.insert(39, 10);
            BSTNode<Integer,Integer> nodeA = bst.getRoot();
            BSTNode<Integer,Integer> nodeB = nodeA.leftChild();
            assertEquals(nodeA.balanceFactor(), -1);
            assertEquals(nodeB.balanceFactor(), 0);
            totalScore += 1;
            bst.insert(36, 11);
            assertEquals(bst.isBalanced(), true);
            assertEquals(bst.getRoot().getKey().equals(38), true);
            assertEquals(bst.getRoot().leftChild().getKey().equals(35), true);
            assertEquals(bst.getRoot().rightChild().getKey().equals(40), true);
            assertEquals(bst.getRoot().leftChild().leftChild().getKey().equals(30), true);
            assertEquals(bst.getRoot().leftChild().rightChild().getKey().equals(37), true);
            assertEquals(bst.getRoot().rightChild().leftChild().getKey().equals(39), true);
            assertEquals(bst.getRoot().rightChild().rightChild().getKey().equals(60), true);
            totalScore += 4;
            success("checkDoubleRotationOnLeft");
        } catch (Exception e) {
            failure("checkDoubleRotationOnLeft", e);
        }
    }


    @Test
    public void rankEqualsKeyTest() {
        try {
            MyKey.resetCompCount();
            BinarySearchTree<MyKey<Integer>, Integer> bst = new BinarySearchTree<>();
            bst.insert(new MyKey(18), 18);
            bst.insert(new MyKey(10), 10);
            bst.insert(new MyKey(22), 22);
            bst.insert(new MyKey(5), 5);
            bst.insert(new MyKey(12), 12);
            bst.insert(new MyKey(25), 25);
            bst.insert(new MyKey(20), 20);
            bst.insert(new MyKey(14), 14);
            bst.insert(new MyKey(15), 15);
            MyKey.resetCompCount();
            assertEquals(6, bst.rank(new MyKey(18)));
            totalScore += 2;
            //System.out.println("number of comparisons="+MyKey.nComparisons);
            int nComparisons1 = MyKey.nComparisons;
            MyKey.resetCompCount();
            assertEquals(7, bst.rank(new MyKey(20)));
            totalScore += 2;
            //System.out.println("number of comparisons="+MyKey.nComparisons);
            int nComparisons2 = MyKey.nComparisons;
            assertEquals(true, nComparisons1 <= 2);
            totalScore += 2;
            assertEquals(true, nComparisons2 <= 3);
            totalScore += 2;
            success("rankEqualsKeyTest");
        }  catch (Exception e) {
            failure("rankEqualsKeyTest", e);
        }
    }

    @Test
    public void rankBetweenKeysTest() {
        try {
            MyKey.resetCompCount();
            BinarySearchTree<MyKey<Integer>, Integer> bst = new BinarySearchTree<>();
            bst.insert(new MyKey(18), 18);
            bst.insert(new MyKey(10), 10);
            bst.insert(new MyKey(22), 22);
            bst.insert(new MyKey(5), 5);
            bst.insert(new MyKey(12), 12);
            bst.insert(new MyKey(25), 25);
            bst.insert(new MyKey(20), 20);
            bst.insert(new MyKey(14), 14);
            bst.insert(new MyKey(15), 15);
            MyKey.resetCompCount();
            assertEquals(6, bst.rank(new MyKey(18)));
            totalScore += 2;
            //System.out.println("number of comparisons="+MyKey.nComparisons);
            int nComparisons1 = MyKey.nComparisons;
            assertEquals(true, nComparisons1 <= 2);
            totalScore += 2;
            success("rankBetweenKeysTest");
        }  catch (Exception e) {
            failure("rankBetweenKeysTest", e);
        }
    }

    @Test
    public void rankBiggerThanAllKeysTest() {
        try {
            MyKey.resetCompCount();
            BinarySearchTree<MyKey<Integer>, Integer> bst = new BinarySearchTree<>();
            bst.insert(new MyKey(18), 18);
            bst.insert(new MyKey(10), 10);
            bst.insert(new MyKey(22), 22);
            bst.insert(new MyKey(5), 5);
            bst.insert(new MyKey(12), 12);
            bst.insert(new MyKey(25), 25);
            bst.insert(new MyKey(20), 20);
            bst.insert(new MyKey(14), 14);
            bst.insert(new MyKey(15), 15);
            MyKey.resetCompCount();
            assertEquals(9, bst.rank(new MyKey(35)));
            totalScore += 2;
            //System.out.println("number of comparisons="+MyKey.nComparisons);
            int nComparisons1 = MyKey.nComparisons;
            assertEquals(true, nComparisons1 <= 3);
            totalScore += 3;
            success("rankBiggerThanAllKeysTest");
        }  catch (Exception e) {
            failure("rankBiggerThanAllKeysTest", e);
        }
    }

    @Test
    public void rangeTestEqualsKeys() {
        try {
            MyKey.resetCompCount();
            BinarySearchTree<MyKey<Integer>, Integer> bst =
                    new BinarySearchTree<MyKey<Integer>, Integer>();
            bst.insert(new MyKey(18), 1);
            bst.insert(new MyKey(14), 2);
            bst.insert(new MyKey(22), 3);
            bst.insert(new MyKey(10), 4);
            bst.insert(new MyKey(15), 5);
            bst.insert(new MyKey(20), 6);
            bst.insert(new MyKey(25), 7);
            bst.insert(new MyKey(35), 8);
            bst.insert(new MyKey(30), 9);
            bst.insert(new MyKey(40), 10);
            bst.insert(new MyKey(5), 11);
            bst.insert(new MyKey(12), 12);
            bst.insert(new MyKey(16), 13);
            MyKey.resetCompCount();
            assertEquals(true, equalMyKeys(Arrays.asList(14,15,16,18,20),
                    bst.getRange(new MyKey(14),new MyKey(20))));
            //System.out.println("number of comparisons="+MyKey.nComparisons);
            int nComparisons1 = MyKey.nComparisons;
            assertEquals(true, nComparisons1 <= 25);
            totalScore += 3;
            MyKey.resetCompCount();
            assertEquals(true, equalMyKeys(Arrays.asList(10,12,14,15,16,18),
                    bst.getRange(new MyKey(10),new MyKey(19))));
            //System.out.println("number of comparisons="+MyKey.nComparisons);
            int nComparisons2 = MyKey.nComparisons;
            assertEquals(true, nComparisons2 <= 25);
            totalScore += 3;
            MyKey.resetCompCount();
            assertEquals(true, equalMyKeys(Arrays.asList(18,20,22),
                    bst.getRange(new MyKey(17),new MyKey(22))));
            //System.out.println("number of comparisons="+MyKey.nComparisons);
            int nComparisons3 = MyKey.nComparisons;
            assertEquals(true, nComparisons3 <= 25);
            totalScore += 3;
            success("rangeTestEqualsKeys");
        } catch (Exception e) {
            failure("rangeTestEqualsKeys", e);
        }
    }


    @Test
    public void rangeTestBetweenKeys() {
        try {
            MyKey.resetCompCount();
            BinarySearchTree<MyKey<Integer>, Integer> bst =
                    new BinarySearchTree<MyKey<Integer>, Integer>();
            bst.insert(new MyKey(18), 1);
            bst.insert(new MyKey(14), 2);
            bst.insert(new MyKey(22), 3);
            bst.insert(new MyKey(10), 4);
            bst.insert(new MyKey(15), 5);
            bst.insert(new MyKey(20), 6);
            bst.insert(new MyKey(25), 7);
            bst.insert(new MyKey(5), 8);
            bst.insert(new MyKey(12), 9);
            bst.insert(new MyKey(16), 10);
            MyKey.resetCompCount();
            assertEquals(true, equalMyKeys(Arrays.asList(14,15,16,18,20),
                    bst.getRange(new MyKey(13),new MyKey(21))));
            //System.out.println("number of comparisons="+MyKey.nComparisons);
            assertEquals(true, MyKey.nComparisons <= 30);
            totalScore += 4;
            success("rangeTestBetweenKeys");
        } catch (Exception e) {
            failure("rangeTestBetweenKeys", e);
        }
    }

    @Test
    public void rangeTestAllKeys() {
        try {
            BinarySearchTree<MyKey<Integer>, Integer> bst =
                    new BinarySearchTree<MyKey<Integer>, Integer>();
            bst.insert(new MyKey(18), 1);
            bst.insert(new MyKey(14), 2);
            bst.insert(new MyKey(22), 3);
            bst.insert(new MyKey(10), 4);
            bst.insert(new MyKey(15), 5);
            bst.insert(new MyKey(20), 6);
            bst.insert(new MyKey(25), 7);
            bst.insert(new MyKey(5), 8);
            bst.insert(new MyKey(12), 9);
            bst.insert(new MyKey(16), 10);
            MyKey.resetCompCount();
            assertEquals(true, equalMyKeys(Arrays.asList(5,10,12,14,15,16,18,20,22,25),
                    bst.getRange(new MyKey(1),new MyKey(30))));
            //System.out.println("number of comparisons="+MyKey.nComparisons);
            assertEquals(true, MyKey.nComparisons <= 30);
            totalScore += 5;
            success("rangeTestAllKeys");
        } catch (Exception e) {
            failure("rangeTestAllKeys", e);
        }
    }

    //@Test
    public void deleteKey1() {
        try {
            BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
            bst.insert(18, "A");
            bst.insert(10, "B");
            bst.insert(22, "C");
            bst.insert(5, "D");
            bst.insert(12, "E");
            bst.insert(25, "F");
            bst.insert(20, "G");
            bst.insert(14, "H");
            bst.insert(27, "K");
            //System.out.println("Printing tree bst..");
            //new BinTreeInorderNavigator<BinarySearchTree.BSTNodeData<Integer,String>>().visit(bst.getRoot());
            assertEquals(bst.delete(20), "G");
            System.out.println("Printing tree bst..");
            new BinTreeInorderNavigator<BinarySearchTree.BSTNodeData<Integer,String>>().visit(bst.getRoot());
            totalScore += 2;
            assertEquals(bst.size(), 8);
            totalScore += 2;
            assertEquals(bst.isBalanced(), true);
            totalScore += 2;
          //System.out.println("Printing tree bst..");
          //new BinTreeInorderNavigator<BinarySearchTree.BSTNodeData<Integer,String>>().visit(bst.getRoot());
            success("deleteKey1");
        } catch (Exception e) {
            failure("deleteKey1", e);
        }
    }

    //@Test
    public void deleteKey2() {
        try {
            BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
            bst.insert(25,"a");
            bst.insert(10,"b");
            bst.insert(12,"t");
            bst.insert(30,"c");
            bst.insert(5,"d");
            bst.insert(28,"e");
            bst.insert(27,"e");
            bst.insert(35,"f");
            bst.insert(33,"n");
            bst.insert(31,"p");
            bst.insert(40,"g");
            bst.insert(50,"l");
            int rank1 = bst.rank(30);
            assertEquals(bst.delete(25), "a");
            int rank2 = bst.rank(30);
            assertEquals(true, rank1 - rank2 == 1);
//            System.out.println("Printing tree bst..");
//            new BinTreeInOrderNavigator<Integer,String>().visit(bst.getRoot());
            totalScore += 4;
            assertEquals(bst.size(),11);
            totalScore += 1;
            assertEquals(bst.isBalanced(),true);
            totalScore += 1;
            success("deleteKey2");
        } catch (Exception e) {
            failure("deleteKey2", e);
        }
    }


}
