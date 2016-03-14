package se.chalmers.phrasebook.backend;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Björn on 2016-03-03.
 */
public class Model {

    private static Model model = new Model();

    private ArrayList<PhraseBook> phrasebooks;
    private String[] languageKeys;
    private String originLanguage;
    private String targetLanguage;

    private String currentPhrasebook;
    private String currentPhrase;

    private Model() {
        phrasebooks = new ArrayList<PhraseBook>();
    }

    private Model(String origin, String target) {
        this();
        originLanguage = origin;
        targetLanguage = target;

        currentPhrasebook = "";
        currentPhrase = "";
    }

    public ArrayList<String> getLanguages() {
        ArrayList<String> list = new ArrayList<String>();
        for (String key : languageKeys) {
            if (Langs.getEngName(key) != null)
                list.add(Langs.getEngName(key));
        }
        return list;

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
