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
        root.children = new ArrayList<Node>();
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
        } else if (node.selectedChild == null) {
            return " "+node.data;
        } else {
            return node.data + " (" + getSentenceSyntax(node.selectedChild) + ")";
        }

    }


    //TODO Fix method
    public Node findNode(Node node, String syntax) {
        if (node == null) {
            return null;
        }
        if (node.data.equals(syntax)) {
            return node;
        } else if (node.children.isEmpty()) {
            return null;//Unsure
        } else {
            for (Node n : node.children) {
                return findNode(n, syntax);
            }
        }

        return node;
    }

    public boolean addChild(Node node, Node child) {
        if (node.children.isEmpty())
            root.selectedChild = child;

        child.parent = node;
        return node.children.add(child);
    }

    public boolean removeChild(Node node, Node child) {
        boolean status = node.children.remove(child);
        if (status && node.children.isEmpty()) {
            node.selectedChild = null;
        }

        return status;
    }

    public boolean setSelectedChild(Node node, Node child) {
        boolean status = node.children.contains(child);
        if (status)
            node.selectedChild = child;
        return status;
    }

    public List<Node> getChildren(Node node) {
        return node.children;
    }

    public boolean isNodeModular(Node node) {
        return node.parent.children.size() > 1;
    }

    public static class Node {
        private String data;
        private Node parent;
        private List<Node> children;
        private Node selectedChild;

        public Node(String data) {
            this.data = data;
        }

        public boolean equals(Object o) {

            if(this == o){
                return true;
            }
            if (!(o instanceof Node)) {
                return false;
            }
            Node n = (Node) o;

            return this.data.equals(n.data) && this.parent.equals(n.parent);
        }
    }
}