package se.chalmers.phrasebook.backend;

/**
 * A class representing an abstract syntax sentence
 * Created by David on 2016-02-19.
 */
public class SyntaxSentence {

    private String id;
    private String description;
    private StaticSyntaxElement head;

    public SyntaxSentence(StaticSyntaxElement head, String id, String description){

    }

    public String outputSentenceSyntax(){
        return head.outputSyntax();
    }

    @Override
    public String toString() {
        return "ID:"+id+"\t"+"Description:"+description;
    }

}
