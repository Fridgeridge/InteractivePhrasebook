package se.chalmers.phrasebook.backend;

/**
 * Created by David on 2016-02-24.
 */
public class DynamicSyntaxElement extends AbstractSyntaxElement {

    private DynamicSyntaxElement[] choice;//Shows all the possible choices for a syntax element. Are at the same hierarchical level, therefore seperated by a space " " char.
    private int currentChoice;
    private String question;

    public DynamicSyntaxElement(String data, AbstractSyntaxElement child){
        super(child, data);
    }


    public void setCurrentChoice(int i){
        if(i>= 0 && i<= choice.length){
            currentChoice = i;
        }
    }

    @Override
    public String outputSyntax() {
        if(this.getChild()==null){


        }
        return "";
    }
}
