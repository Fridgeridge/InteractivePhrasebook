package se.chalmers.phrasebook.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Björn on 2016-03-03.
 */
public class SyntaxNode {
    private String data = null;
    private String desc = null;
    private SyntaxNode parent = null;
    private List<SyntaxNode> children = new ArrayList<SyntaxNode>();
    private SyntaxNode selectedChild = null;

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

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent = parent;
    }

    public List<SyntaxNode> getChildren() {
        return children;
    }

    public boolean addChild(SyntaxNode node) {
        if (node != null)
            return children.add(node);
        return false;
    }

    public void setChildren(List<SyntaxNode> children) {
        this.children = children;
    }

    public SyntaxNode getSelectedChild() {
        return selectedChild;
    }

    public void setSelectedChild(SyntaxNode selectedChild) {
        this.selectedChild = selectedChild;
    }

    public SyntaxNode(String data) {
        this.data = data;
    }

    public boolean hasChildren() {
        if (children.isEmpty()) {
            return true;
        }
        return false;
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
        if (!(o instanceof SyntaxNode)) {
            return false;
        }
        SyntaxNode n = (SyntaxNode) o;

        return this.data.equals(n.data) && this.parent.equals(n.parent);
    }
}