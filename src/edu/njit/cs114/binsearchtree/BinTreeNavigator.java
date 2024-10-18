package edu.njit.cs114.binsearchtree;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/1/2024
 */
public interface BinTreeNavigator<E> {

    public void visit(BinTreeNode<E> node);

    public void process(BinTreeNode<E> node);

}

