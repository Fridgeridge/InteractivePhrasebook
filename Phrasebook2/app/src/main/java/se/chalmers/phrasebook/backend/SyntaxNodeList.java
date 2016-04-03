package se.chalmers.phrasebook.backend;

import java.util.ArrayList;

/**
 * Created by David on 2016-04-01.
 */
public class SyntaxNodeList {
    private SyntaxNode selectedChild;
    private ArrayList<SyntaxNode> altChildren;

    private String question;


    public SyntaxNodeList() {
       altChildren = new ArrayList<SyntaxNode>();
    }


    public boolean add(SyntaxNode object) {
        if (selectedChild == null)
            selectedChild = object;

        if(!altChildren.contains(object))
            return altChildren.add(object);
        return false;
    }

    public SyntaxNode getSelectedChild() {
        return selectedChild;
    }

    public void setSelectedChild(SyntaxNode selectedChild) {
        this.selectedChild = selectedChild;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


}
