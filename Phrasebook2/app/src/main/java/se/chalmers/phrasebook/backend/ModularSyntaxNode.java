package se.chalmers.phrasebook.backend;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by Björn on 2016-04-01.
 */
public class ModularSyntaxNode extends SyntaxNode {

    private LinkedHashMap<String, List<SyntaxNode>> questionToChildren = new LinkedHashMap<>();

    public ModularSyntaxNode(String data) {
        super(data);
    }

    public void linkQuestionToChildren(String question, List<SyntaxNode> children) {
        questionToChildren.put(question, children);
    }

    public LinkedHashMap<String, List<SyntaxNode>> getQuestionToChildren() {
        return questionToChildren;
    }

    public boolean setSelectedChild(String question, SyntaxNode updated) {
        if(questionToChildren.keySet().contains(question) &&
                questionToChildren.get(question).contains(updated)) {
         //   int index = super.questions.indexOf(question);
        //    super.selectedChildren.set(index, updated);
            return true;
        }
        return false;
    }
}
