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
    private List<SyntaxNode> children = new ArrayList<SyntaxNode>();
    private List<SyntaxNode> selectedChildren = new ArrayList<SyntaxNode>();
    private List<String> questions = new ArrayList<String>();
    private int nmbrOfSelectedChildren = 0;

    public SyntaxNode(String data) {
        this.data = data;
    }

    public boolean addChild(SyntaxNode node) {
        if (node != null) {
            if(selectedChildren.isEmpty()) {
                selectedChildren.add(node);
            } else if(nmbrOfSelectedChildren > selectedChildren.size()) {
                selectedChildren.add(node);
            }
            children.add(node);
            return true;
        } else {
            return false;
        }
    }

    public boolean addQuestion(String question) {
        if(questions.add(question)) {
            return true;
        }
        return false;
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

    public String getData() {
        return data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<SyntaxNode> getChildren() {
        return children;
    }

    public List<SyntaxNode> getSelectedChildren() {
        return selectedChildren;
    }

    //Replaces the specified previous child with the new updated selected child
    public boolean setSelectedChild(String question, SyntaxNode updated) {
        System.out.println(question);
        System.out.println(updated.getData());
        if(questions.contains(question) && children.contains(updated)) {
            int index = questions.indexOf(question);
            System.out.println(selectedChildren.get(index).getData());
            selectedChildren.set(index, updated);
            System.out.println(selectedChildren.get(index).getData());
            return true;
        }
        return false;
    }


    public boolean removeSelectedChild(SyntaxNode selectedChild){
        return selectedChildren.remove(selectedChild);
    }

    public int getNmbrOfSelectedChildren() {
        return nmbrOfSelectedChildren;
    }

    public void setNmbrOfSelectedChildren(int nmbrOfSelectedChildren) {
        this.nmbrOfSelectedChildren = nmbrOfSelectedChildren;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (!(o instanceof SyntaxNode)) {
            return false;
        }
        SyntaxNode n = (SyntaxNode) o;

        return this.data.equals(n.data);
    }

}