package se.chalmers.phrasebook.backend;

import java.util.ArrayList;

/**
 * Created by matilda on 15/03/16.
 */
public class TestSentence {

    private int nbrOptions;
    private ArrayList<String> choices;

    public TestSentence(int nbrOptions){
        this.nbrOptions = nbrOptions;

        choices = new ArrayList<>();

        for(int i = 0; i < 5; i++){
            choices.add("val " + i);
        }

    }

    public int getNbrOptions(){
        return nbrOptions;
    }

    public ArrayList getChoices(){
        return choices;
    }

}
