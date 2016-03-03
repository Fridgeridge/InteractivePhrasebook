package se.chalmers.phrasebook.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Björn on 2016-02-26.
 */
public class SyntaxTree {
    private Node root;

    public SyntaxTree(String rootData) {
        root = new Node(rootData);
    }

    /**
     * Build recursively from root node to parse syntax
     *
     * @param node The current node
     * @return A String representing the syntax of the sentence
     */
    public String getSentenceSyntax(Node node) {
        if (node == null) {
            return "";
        } else if (node.getSelectedChild() == null) {
            return " " + node.getData();
        } else {
            return node.getData() + " (" + getSentenceSyntax(node.getSelectedChild()) + ")";
        }

    }


    //TODO Fix method
    public Node findNode(Node node, String syntax) {
        if (node == null) {
            return null;
        }
        if (node.getData().equals(syntax)) {
            return node;
        } else if (node.getChildren().isEmpty()) {
            return null;//Unsure
        } else {
            for (Node n : node.getChildren()) {
                return findNode(n, syntax);
            }
        }

        return node;
    }

    public void addChild(Node node, Node child) {
        if (node.getChildren().isEmpty())
            root.setSelectedChild(child);

        child.setParent(node);
        node.getChildren().add(child);
    }

    public boolean removeChild(Node node, Node child) {
        boolean status = node.getChildren().remove(child);
        if (status && node.getChildren().isEmpty()) {
            node.setSelectedChild(null);
        }

        return status;
    }

    public boolean setSelectedChild(Node node, Node child) {
        boolean status = node.getChildren().contains(child);
        if (status)
            node.setSelectedChild(child);
        return status;
    }

    public boolean isNodeModular(Node node) {
        return node.getParent().getChildren().size() > 1;
    }


}