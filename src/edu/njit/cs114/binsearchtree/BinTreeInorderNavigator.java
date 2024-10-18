package edu.njit.cs114.binsearchtree;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/1/2024
 */
public class BinTreeInorderNavigator<E> implements BinTreeNavigator<E> {

    private int tabs = 2;
    @Override
    public void visit(BinTreeNode<E> node) {
        if (node != null) {
            tabs += 1;
            visit(node.leftChild());
            tabs -= 1;
            process(node);
            tabs += 1;
            visit(node.rightChild());
            tabs -= 1;
        }
    }

    @Override
    public void process(BinTreeNode<E> node) {
        StringBuilder builder = new StringBuilder();
        for (int i=0; i < tabs; i++) {
            builder.append("      ");
        }
        builder.append("("+node.element()+")");
        builder.append("(BF="+node.balanceFactor()+")");
        System.out.println(builder.toString());
    }
}
