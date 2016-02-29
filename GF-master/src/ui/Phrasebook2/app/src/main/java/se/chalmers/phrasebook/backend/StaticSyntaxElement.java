package se.chalmers.phrasebook.backend;

/**
 * A syntax element which presents a static structure of the sentence
 * Created by David on 2016-02-19.
 */
public class StaticSyntaxElement extends AbstractSyntaxElement {


    public StaticSyntaxElement(String data, AbstractSyntaxElement child) {
        super(child, data);
    }

    public String outputSyntax() {
        if (getChild() == null) {
            return getSyntaxData();
        } else {
            return getSyntaxData() + " (" + getChild().outputSyntax() + ") ";
        }

    }


}
