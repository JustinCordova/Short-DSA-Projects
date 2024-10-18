package edu.njit.cs114.binsearchtree;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/1/2024
 */
public interface BinTreeNode<E> {

    /**
     * @return stored data
     */
    public E element();

    /**
     * Set data
     * @param element old data
     * @return
     */
    public E setElement(E element);

    /**
     * Get the left child node
     * @return
     */
    public BinTreeNode<E> leftChild();

    /**
     * Get the right child node
     * @return
     */
    public BinTreeNode<E> rightChild();


    /**
     * Is the node a leaf (external node) ?
     * @return
     */
    public boolean isLeaf();

    /**
     * Returns balance factor (height of left sub tree - height of right sub tree)
     * @return
     */
    public int balanceFactor();

}
