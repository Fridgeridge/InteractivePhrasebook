package se.chalmers.phrasebook.backend;



import java.util.ArrayList;



/**
 * Created by Björn on 2016-03-03.
 */
public class Model {

    private static Model model = new Model();

    private ArrayList<PhraseBook> phrasebooks;
    private String originLanguage;
    private String targetLanguage;

    private String currentPhrasebook;
    private String currentPhrase;

    private Model() {
        phrasebooks = new ArrayList<PhraseBook>();

        originLanguage = "";
        targetLanguage = "";

        currentPhrasebook = "";
        currentPhrase = "";

    }


    public static Model getInstance() {
        return model;
    }

    public String getOriginLanguage() {
        return originLanguage;
    }

    public void setOriginLanguage(String originLanguage) {
        this.originLanguage = originLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public void setCurrentPhrasebook(String phrasebook){
        currentPhrasebook = phrasebook;
    }

    public String getCurrentPhrasebook(){
        return currentPhrasebook;
    }

    public String getCurrentPhrase(){
        return currentPhrase;
    }

    public void setCurrentPhrase(String phrase){
        currentPhrase = phrase;
    }

}
