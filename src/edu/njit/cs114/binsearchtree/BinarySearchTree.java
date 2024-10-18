package edu.njit.cs114.binsearchtree;

import java.util.*;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/1/2024
 */
public class BinarySearchTree<K extends Comparable<K>,V> {

    private BSTNode<K,V> root;
    private int size;

    public static class BSTNodeData<K extends Comparable<K>,V> {
        private K key;
        private V value;

        public BSTNodeData(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public String toString() {
            return key + "," + value;
        }
    }

    public static class BSTNode<K extends Comparable<K>,V> implements
                            BinTreeNode<BSTNodeData<K,V>> {

        private BSTNodeData<K,V> nodeData;
        private int height;
        // number of keys (including the key in this node) in the subtree rooted at this node
        private int size;
        private BSTNode<K, V> left, right;

        public BSTNode(K key, V value, BSTNode<K, V> left, BSTNode<K, V> right) {
            this.nodeData = new BSTNodeData<>(key, value);
            this.left = left;
            this.right = right;
            /**
             * Complete code to store height and size  (for the lab)
             */
            setAugmentedInfo();
        }

        private void setAugmentedInfo() {
        int leftHeight = (left == null) ? 0 : left.height;
        int rightHeight = (right == null) ? 0 : right.height;
        int leftSize = (left == null) ? 0 : left.size;
        int rightSize = (right == null) ? 0 : right.size;

        // Set current node height
        height = Math.max(leftHeight, rightHeight) + 1;

        // Set current node size
        size = leftSize + rightSize + 1;
        }

        public BSTNode(K key, V value) {
            this(key, value, null, null);
        }

        public K getKey() {
            return nodeData.key;
        }

        public V getValue() {
            return nodeData.value;
        }

        @Override
        public BSTNodeData<K, V> element() {
            return nodeData;
        }

        @Override
        public BSTNodeData<K, V> setElement(BSTNodeData<K, V> element) {
            BSTNodeData<K, V> oldValue = nodeData;
            this.nodeData = new BSTNodeData<>(element.key, element.value);
            return oldValue;
        }

        @Override
        public BSTNode<K, V> leftChild() {
            return left;
        }

        @Override
        public BSTNode<K, V> rightChild() {
            return right;
        }

        @Override
        public boolean isLeaf() {
            return (left == null && right == null);
        }

        private void setLeftChild(BSTNode<K, V> node) {
            this.left = node;
            /**
             * Complete code to store height and size  (for the lab)
             */
            setAugmentedInfo();
        }

        private void setRightChild(BSTNode<K, V> node) {
            this.right = node;
            /**
             * Complete code to store height and size  (for the lab)
             */
            setAugmentedInfo();
        }

        private void setValue(V value) { this.nodeData.value = value; };

        /**
         * Returns height of right subtree - height of left subtree
         * @return
         */
        @Override
        public int balanceFactor() {
            /**
             * Complete code for the lab
             *
             */
            int leftHeight = (left == null) ? 0 : left.height;
            int rightHeight = (right == null) ? 0 : right.height;
            return rightHeight - leftHeight;
        }
    }

    public BSTNode<K,V> getRoot() {
        return root;
    }

    private V getValueAux(BSTNode<K,V> localRoot, K key) {
        /**
         * Complete code for the lab
         */
        if (localRoot == null) {
            return null;
        }

        int z = key.compareTo(localRoot.nodeData.key);
        if (z == 0) {
            return localRoot.nodeData.value;
        } else if (z < 0) {
            return getValueAux(localRoot.left, key);
        } else {
            return getValueAux(localRoot.right, key);
        }
    }

    /**
     * Get value for the key
     * @param key
     * @return value, null if key does not exist
     */
    public V getValue(K key) {
        return getValueAux(root, key);
    }

    /**
     * Rotate left or right the child node depending on whether child is a right or
     * left child of localRoot
     * @param localRoot root of subtree involved in rotation
     * @param child child of localRoot
     * @return the new root of the subtree
     */
    private BSTNode<K,V> singleRotate(BSTNode<K,V> localRoot, BSTNode<K,V> child) {
        /**
         * Complete code that will be useful for homework
         */
        // Case (a)
        if (child == localRoot.left) {
            // Right rotation of child (B)
            BSTNode<K, V> rightChildOfChild = child.rightChild();
            child.setRightChild(localRoot);
            localRoot.setLeftChild(rightChildOfChild);
            return child;
        }
        // Case (b)
        else {
            // Left rotation of child (B)
            BSTNode<K, V> leftChildOfChild = child.leftChild();
            child.setLeftChild(localRoot);
            localRoot.setRightChild(leftChildOfChild);
            return child;
        }
    }

    /**
     * Rotate grandchild node left and then right if child is left child of localRoot and grandChild is
     * right child of child.
     * Rotate grandchild node right and then left if child is right child of localRoot and grandChild is
     * left child of child.
     * @param localRoot root of subtree involved in rotation
     * @param child child node of localRoot
     * @param grandChild child node of child
     * @return the new root of the subtree
     */
    private BSTNode<K,V> doubleRotate(BSTNode<K,V> localRoot, BSTNode<K,V> child,
                                      BSTNode<K,V> grandChild) {
        /**
         * Complete code that will be useful for homework
         */
            if (child == localRoot.left && grandChild == child.right) {
            // Case (a)
            child.setRightChild(grandChild.leftChild()); // (i)
            localRoot.setLeftChild(grandChild.rightChild()); // (ii)
            grandChild.setLeftChild(child); // (iii)
            grandChild.setRightChild(localRoot); // (iv)
            return grandChild; // Return grandChild (node C)
        } else if (child == localRoot.right && grandChild == child.left) {
            // Case (b): Right-left rotation
            child.setLeftChild(grandChild.rightChild()); // (i)
            localRoot.setRightChild(grandChild.leftChild()); // (ii)
            grandChild.setRightChild(child); // (iii)
            grandChild.setLeftChild(localRoot); // (iv)
            return grandChild; // Return grandChild (node C)
        }
        return localRoot;
    }

    private BSTNode<K,V> balance(BSTNode<K,V> localRoot) {
        /**
         * Complete code for the homework
         * (call singleRotate() or doubleRotate as the case may be)
         */
        // return localRoot if it is already balanced or null
        if (localRoot == null || Math.abs(localRoot.balanceFactor()) <= 1) {
            return localRoot;
        }

        int bf = localRoot.balanceFactor();

        // Case 1: bf < -1
        if (bf < -1) {
            if (localRoot.left != null) {
                // If balance factor of left child <= 0, perform single rotation
                if (localRoot.left.balanceFactor() <= 0) {
                    return singleRotate(localRoot, localRoot.left);
                } else { // balance factor of left child > 0, perform double rotation
                    return doubleRotate(localRoot, localRoot.left, localRoot.left.right);
                }
            }
        }
        // Case 2: bf > 1
        else if (bf > 1) {
            if (localRoot.right != null) {
                // If balance factor of right child >= 0, perform single rotation
                if (localRoot.right.balanceFactor() >= 0) {
                    return singleRotate(localRoot, localRoot.right);
                } else { // balance factor of right child < 0, perform double rotation
                    return doubleRotate(localRoot, localRoot.right, localRoot.right.left);
                }
            }
        }

        // Return local Root if balance factor is in valid range or children are null
        return localRoot;
        }

    public BSTNode<K,V> insertAux(BSTNode<K,V> localRoot, K key, V value) {
        if (localRoot == null) {
            return new BSTNode<K,V>(key, value);
        }
        int result = key.compareTo(localRoot.nodeData.key);
        if (result < 0) {
            localRoot.setLeftChild(insertAux(localRoot.left, key, value));
        } else if (result > 0){
            localRoot.setRightChild(insertAux(localRoot.right, key, value));
        } else {
            localRoot.setValue(value);
        }
        /**
         * Complete code to set height and size of localRoot for the lab
         */
         // Balance the tree if necessary
        localRoot.setAugmentedInfo(); // Setting size and height of LocalRoot
        return balance(localRoot);
    }

    /**
     * Insert/Replace (key,value) association or mapping in the tree
     * @param key
     * @param value value to insert or replace
     */
    public void insert(K key, V value) {
        this.root = insertAux(root, key, value);
    }

    // Extra credit problem for homework
    /**
     * Delete the value associated with the key if it exists
     * Note you need to set height and size properly
     * in the nodes of the subtrees affected and also balance the tree
     * @param key
     * @return value deleted if key exists else null
     */
    public V delete(K key) {
        return null;
    }

    public int height() {
        return (root == null ? 0 : root.height);
    }

    public int size() {
        return (root == null ? 0 : root.size);
    }

    private boolean isBalanced(BSTNode<K,V> localRoot) {
        /**
         * Complete code here for the lab
         */
         if (localRoot == null) {
                return true;
            }

            int leftHeight = (localRoot.left == null) ? 0 : localRoot.left.height;
            int rightHeight = (localRoot.right == null) ? 0 : localRoot.right.height;

            return Math.abs(leftHeight - rightHeight) <= 1 && isBalanced(localRoot.left) && isBalanced(localRoot.right);
    }

    /**
     * Is the tree balanced ?
     * For every node, height of left and right subtrees differ by at most 1
     * @return
     */
    public boolean isBalanced() {
        return isBalanced(root);
    }

    /**
     * Get level ordering of nodes; keys in a level must be in descending order
     * @return a map which associates a level with list of nodes at that level
     */
    public Map<Integer, List<BSTNode<K,V>>> getNodeLevels() {
        Map<Integer, List<BSTNode<K,V>>> nodeLevels = new HashMap<>();
        /**
         * Complete code for the lab
         */
        if (root == null) {
            return nodeLevels;
        }

        List<BSTNode<K, V>> currentLevel = new ArrayList<>();
        currentLevel.add(root);
        nodeLevels.put(0, currentLevel);

        int level = 1;
        while (!currentLevel.isEmpty()) {
            List<BSTNode<K, V>> nextLevel = new ArrayList<>();
            for (BSTNode<K, V> node : currentLevel) {
                if (node.left != null) {
                    nextLevel.add(node.left);
                }
                if (node.right != null) {
                    nextLevel.add(node.right);
                }
            }
            if (!nextLevel.isEmpty()) {
                Collections.sort(nextLevel, (n1, n2) -> n2.nodeData.key.compareTo(n1.nodeData.key));
                nodeLevels.put(level, nextLevel);
                currentLevel = nextLevel;
                level++;
            } else {
                break;
            }
        }

        return nodeLevels;
    }


    /**
     * Return list of nodes whose keys are greater than or equal to key1
     *   and smaller than or equal to key2
     * @param key1
     * @param key2
     * @return
     */
    public List<BSTNode<K,V>> getRange(K key1, K key2) {
        /**
         * Complete code for homework (define a recursive aux function to be called from here)
         */
        return rangeAux(root, key1, key2);
    }

    private List<BSTNode<K,V>> rangeAux(BSTNode<K,V> localRoot, K key1, K key2) {
        List<BSTNode<K,V>> nodeList = new ArrayList<>();
        if (localRoot == null) {
            return nodeList; // Base case
        }

        int cmp2 = key2.compareTo(localRoot.getKey());

        if (cmp2 <= 0) {
            // key2 is <= to localRoot's key
            if (cmp2 == 0) {
                // key2 == localRoot's key
                nodeList.add(localRoot); // Add localRoot node to nodeList
            }
            nodeList.addAll(rangeAux(localRoot.leftChild(), key1, key2));
        } else {
            // key2 > localRoot's key
            int cmp1 = key1.compareTo(localRoot.getKey());
            if (cmp1 >= 0) {
                // key1 >= localRoot's key
                if (cmp1 == 0) {
                    // key1 == localRoot's key
                    nodeList.add(localRoot);
                }
                nodeList.addAll(rangeAux(localRoot.rightChild(), key1, key2));
            } else {
                // key1 < localRoot's key
                nodeList.addAll(rangeAux(localRoot.leftChild(), key1, key2));
                nodeList.add(localRoot);
                nodeList.addAll(rangeAux(localRoot.rightChild(), key1, key2));
            }
        }
        return nodeList;
    }

    /**
     * Find number of keys smaller than or equal to the specified key
     * @param key
     * @return
     */
    public int rank(K key) {
        /**
         * Complete code for homework (define a recursive aux function to be called from here)
         */
        return rankAux(root, key);
    }

    private int rankAux(BSTNode<K,V> localRoot, K key) {
        if (localRoot == null) {
            return 0; // Base case
        }

        int cmp = key.compareTo(localRoot.getKey());

        if (cmp < 0) {
            // Find rank in the left subtree
            return rankAux(localRoot.leftChild(), key);
        } else if (cmp > 0) {
            // Find rank in the right subtree
            // Add the size of left subtree and 1 (current node) to the rank in the right subtree
            return size(localRoot.leftChild()) + 1 + rankAux(localRoot.rightChild(), key);
        } else {
            // Key == key of localRoot
            return size(localRoot.leftChild()) + 1;
        }
    }

    // Get the size of the subtree rooted at a given node
    private int size(BSTNode<K,V> node) {
        return node == null ? 0 : node.size;
    }



    public static void main(String [] args) {
        BinarySearchTree<Integer, String> bst = new BinarySearchTree<>();
        bst.insert(25,"a");
        bst.insert(15,"b");
        bst.insert(30,"c");
        bst.insert(5,"d");
        bst.insert(27,"e");
        bst.insert(36,"f");
        bst.insert(40,"g");
        bst.insert(10,"k");
        bst.insert(52,"l");
        System.out.println("Printing tree bst..");
        new BinTreeInorderNavigator<BSTNodeData<Integer,String>>().visit(bst.getRoot());
        int key = 36;
        String value = bst.getValue(key);
        if (value != null) {
            System.out.println("Value for key "+ key + "=" + value);
        } else {
            System.out.println("Key " + key + " does not exist");
        }
        key = 20;
        value = bst.getValue(key);
        if (value != null) {
            System.out.println("Value for key "+ key + "=" + value);
        } else {
            System.out.println("Key " + key + " does not exist");
        }
        bst.insert(40,"m");
        System.out.println("Printing tree bst..");
        new BinTreeInorderNavigator<BSTNodeData<Integer,String>>().visit(bst.getRoot());
//        System.out.println("Value for deleted key 5 = " + bst.delete(5));
//        System.out.println("Printing tree bst..");
//        new BinTreeInorderNavigator<BSTNodeData<Integer,String>>().visit(bst.getRoot());
//        System.out.println("Value for deleted key 30 = " + bst.delete(30));
//        System.out.println("Printing tree bst..");
//        new BinTreeInorderNavigator<BSTNodeData<Integer,String>>().visit(bst.getRoot());
        System.out.println("size of bst=" + bst.size());
        System.out.println("height of bst=" + bst.height());
        System.out.println("Is bst an AVL tree ? " + bst.isBalanced());
        Map<Integer, List<BSTNode<Integer,String>>> nodeLevels = bst.getNodeLevels();
        for (int level : nodeLevels.keySet()) {
            System.out.print("Keys at level " + level + " :");
            for (BSTNode<Integer,String> node : nodeLevels.get(level)) {
                System.out.print(" " + node.getKey());
            }
            System.out.println("");
        }
        BinarySearchTree<Integer, Integer> bst1 = new BinarySearchTree<>();
        bst1.insert(44,1);
        bst1.insert(17,2);
        bst1.insert(78,3);
        bst1.insert(50,4);
        bst1.insert(62,5);
        bst1.insert(88,6);
        bst1.insert(48,7);
        bst1.insert(32,8);
        System.out.println("Printing tree bst1..");
        new BinTreeInorderNavigator<BSTNodeData<Integer,Integer>>().visit(bst1.getRoot());
        System.out.println("size of bst1=" + bst1.size());
        System.out.println("height of bst1=" + bst1.height());
        System.out.println("Is bst1 an AVL tree ? " + bst1.isBalanced());
        Map<Integer, List<BSTNode<Integer,Integer>>> nodeLevels1 = bst1.getNodeLevels();
        for (int level : nodeLevels1.keySet()) {
            System.out.print("Keys at level " + level + " :");
            for (BSTNode<Integer,Integer> node : nodeLevels1.get(level)) {
                System.out.print(" " + node.getKey());
            }
            System.out.println("");
        }
        System.out.println("rank of key 10 in bst=" + bst.rank(10)); // should be 2
        System.out.println("rank of key 30 in bst=" + bst.rank(30)); // should be 6
        System.out.println("rank of key 3 in bst=" + bst.rank(3)); // should be 0
        System.out.println("rank of key 55 in bst=" + bst.rank(55)); // should be 9
        List<BSTNode<Integer,Integer>> rangeNodes = bst1.getRange(32,62);
        System.out.print("Keys in the range : [32,62] are:");
        // should get 32,44,48,50,62,
        for (BSTNode<Integer,Integer> node : rangeNodes) {
            System.out.print(node.getKey() + ",");
        }
        System.out.println("");
        rangeNodes = bst1.getRange(10,50);
        System.out.print("Keys in the range : [10,50] are:");
        // should get 17,32,44,48,50,
        for (BSTNode<Integer,Integer> node : rangeNodes) {
            System.out.print(node.getKey() + ",");
        }
        System.out.println("");
        rangeNodes = bst1.getRange(90,100);
        System.out.print("Keys in the range : [90,100] are:");
        // should get empty list
        for (BSTNode<Integer,Integer> node : rangeNodes) {
            System.out.print(node.getKey() + ",");
        }
        System.out.println("");
    }

}
