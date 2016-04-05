package se.chalmers.phrasebook.backend;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Björn on 2016-03-03.
 */

public class SyntaxNode {
    private String data;
    private String desc;
    private ArrayList<SyntaxNodeList> syntaxNodes;
    private LinkedHashMap<String, List<SyntaxNode>> questionToChildren = new LinkedHashMap<>();

    public SyntaxNode(String data) {
        syntaxNodes = new ArrayList<SyntaxNodeList>();
        this.data = data;
    }

    public ArrayList<SyntaxNodeList> getSyntaxNodes() {
        return syntaxNodes;
    }

    public void linkQuestionToChildren(String question, List<SyntaxNode> children) {
        questionToChildren.put(question, children);
    }

    public LinkedHashMap<String, List<SyntaxNode>> getQuestionToChildren() {
        return questionToChildren;
    }

    public String getData() {
        return data;
    }

    public boolean isModular() {
        if(questionToChildren.keySet() != null) {
            return true;
        }
        return false;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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