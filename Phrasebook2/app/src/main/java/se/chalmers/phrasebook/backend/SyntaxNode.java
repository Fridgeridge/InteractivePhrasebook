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

    public SyntaxNode(String data) {
        syntaxNodes = new ArrayList<SyntaxNodeList>();
        this.data = data;
    }

    public void setSelectedChild(int listIndex, SyntaxNode newChild) {
        syntaxNodes.get(listIndex).setSelectedChild(newChild);
    }

    public ArrayList<SyntaxNodeList> getSyntaxNodes() {
        return syntaxNodes;
    }

    public String getData() {
        return data;
    }

    public boolean isModular() {
        System.out.println(this.desc);
        if(syntaxNodes.size() > 0 && syntaxNodes.get(0).getChildren().size() > 1) {
            System.out.println("Is modular");
            return true;
        }
        System.out.println("Is NOT modular");
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