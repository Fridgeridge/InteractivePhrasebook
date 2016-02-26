package se.chalmers.phrasebook.backend;

/**
 * Created by David on 2016-02-24.
 */
public class DynamicSyntaxElement extends AbstractSyntaxElement {

    private String[] choice;//Shows all the possible choices for a syntax element. Are at the same hierarchical level, therefore seperated by a space " " char.
    private int currentChoice = 0;//Default item on the list
    private String question;

    public DynamicSyntaxElement(String data, AbstractSyntaxElement child, String[] choice, String question) {
        super(child, data);
        this.choice = choice;
        this.question = question;
    }


    public void setCurrentChoice(int i) {
        if (choice != null && i >= 0 && i < choice.length) {
            currentChoice = i;
        }
    }

    @Override
    public String outputSyntax() {
        if (this.getChild() == null) {

            if (choice == null || choice.length <= 0) {
                return this.getSyntaxData();
            } else {
                return this.getSyntaxData() + " " + choice[currentChoice];
            }
        } else {
            if (choice == null || choice.length <= 0) {
                return this.getSyntaxData() + " (" + this.getChild().outputSyntax() + ")";
            } else {
                return this.getSyntaxData() + " " + choice[currentChoice] + " (" + this.getChild().outputSyntax() + ")";
            }


        }
    }


}