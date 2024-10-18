package edu.njit.cs114.huffmantreecoder;

import edu.njit.cs114.binsearchtree.BinTreeNavigator;
import edu.njit.cs114.binsearchtree.BinTreeNode;

/**
 * Author: Ravi Varadarajan
 * Date created: 4/10/2024
 */
public class HuffmanTreeNavigator implements BinTreeNavigator<HuffmanTreeCoder.HuffmanTreeNodeData> {

    private int tabs = 0;
    public static final String newLine = System.getProperty("line.separator");

    private StringBuilder builder = new StringBuilder();

    @Override
    public void visit(BinTreeNode<HuffmanTreeCoder.HuffmanTreeNodeData> node) {
        if (node != null) {
            tabs += 1;
            visit(node.rightChild());
            tabs -= 1;
            process(node);
            tabs += 1;
            visit(node.leftChild());
            tabs -= 1;
        }
    }

    @Override
    public void process(BinTreeNode<HuffmanTreeCoder.HuffmanTreeNodeData> node) {
        for (int i=0; i < tabs; i++) {
            builder.append("    ");
        }
        HuffmanTreeCoder.HuffmanTreeNodeData nodeData = (HuffmanTreeCoder.HuffmanTreeNodeData) node.element();
        builder.append(nodeData.getWeight());
        if (node.isLeaf()) {
            builder.append("[" + nodeData.getCh() + "]");
        }
        builder.append(newLine);
    }

    public String toString() {
        return builder.toString();
    }
}
