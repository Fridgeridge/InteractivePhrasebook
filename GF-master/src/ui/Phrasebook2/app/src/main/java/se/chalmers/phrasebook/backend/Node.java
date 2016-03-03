package se.chalmers.phrasebook.backend;

import java.util.List;

/**
 * Created by Björn on 2016-03-03.
 */
public class Node {
    private String data;
    private String desc;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public Node getSelectedChild() {
        return selectedChild;
    }

    public void setSelectedChild(Node selectedChild) {
        this.selectedChild = selectedChild;
    }

    private Node parent;
    private List<Node> children;
    private Node selectedChild;

    public Node(String data) {
        this.data = data;
    }

    public boolean setDescription(String desc) {
        if (desc == null || desc.isEmpty()) {
            return false;
        } else {
            this.desc = desc;
            return true;
        }
    }

    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof Node)) {
            return false;
        }
        Node n = (Node) o;

        return this.data.equals(n.data) && this.parent.equals(n.parent);
    }
}