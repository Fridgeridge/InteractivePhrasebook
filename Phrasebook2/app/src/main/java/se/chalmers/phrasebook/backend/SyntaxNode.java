package se.chalmers.phrasebook.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Björn on 2016-03-03.
 */
public class SyntaxNode {
    private String data;
    private String desc;
    private SyntaxNode parent;
    private List<SyntaxNode> children = new ArrayList<SyntaxNode>();
    private int nmbrOfSelectedChildren = 1;
    private SyntaxNode[] selectedChild;

    public SyntaxNode(String data) {
        this.data = data;
    }

    public boolean addChild(SyntaxNode node, SyntaxNode parent) {
        if (node != null) {
            node.setParent(parent);
            if(selectedChild == null) {
                selectedChild = new SyntaxNode[1];
            }
            for(int i = 0; i < selectedChild.length; i++) {
                if(selectedChild[i] == null) {
                    selectedChild[i] = node;
                    break;
                }
            }
        }
        children.add(node);
        return true;
    }

    public boolean hasChildren() {
       return !this.children.isEmpty();
    }

    public boolean isModular() {
        return children.size() > 1;
    }

    public boolean setDescription(String desc) {
        if (desc == null || desc.isEmpty()) {
            return false;
        } else {
            this.desc = desc;
            return true;
        }
    }

    public int getNmbrOfSelectedChildren() {return nmbrOfSelectedChildren;}

    public void setNmbrOfSelectedChildren(int nmbrOfSelectedChildren) {
        this.nmbrOfSelectedChildren = nmbrOfSelectedChildren;
        selectedChild = new SyntaxNode[nmbrOfSelectedChildren];
    }

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

    public void setChildren(List<SyntaxNode> children) {
        this.children = children;
    }

    public SyntaxNode[] getSelectedChild() {
        return selectedChild;
    }

    //Replaces the specified previous child with the new updated selected child
    public void setSelectedChild(SyntaxNode previous, SyntaxNode updated) throws IOException {
        int position = 0;
        boolean contains = false;
        for(int i = 0; i < selectedChild.length; i++) {
            if(selectedChild[i].equals(previous)) {
                contains = true;
                position = i;
                break;
            }
        }
        if(!contains) {
            throw new IOException("previous node not a selected child");
        }
       contains = false;
        for(SyntaxNode c: children) {
            if(c.equals(updated)) {
                contains = true;
                break;
            }
        }
        if(!contains) {
            throw new IOException("new node is not a child");
        }
        selectedChild[position] = updated;
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