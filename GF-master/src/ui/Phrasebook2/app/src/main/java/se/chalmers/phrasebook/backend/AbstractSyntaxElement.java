package se.chalmers.phrasebook.backend;

/**
 * Created by David on 2016-02-24.
 */
public abstract class AbstractSyntaxElement {

    private AbstractSyntaxElement child;
    private String syntaxData;

    public AbstractSyntaxElement(AbstractSyntaxElement syntaxElement, String syntaxData) {
        this.child = syntaxElement;
        this.syntaxData = syntaxData;
    }

    public void setChild(AbstractSyntaxElement syntaxElement) {
        this.child = syntaxElement;
    }

    public AbstractSyntaxElement getChild() {
        return this.child;
    }

    public String getSyntaxData() {
        return this.syntaxData;
    }

    public abstract String outputSyntax();


}
