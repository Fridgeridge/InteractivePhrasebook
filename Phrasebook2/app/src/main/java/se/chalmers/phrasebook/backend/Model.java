package se.chalmers.phrasebook.backend;



import java.util.ArrayList;

import se.chalmers.phrasebook.App;



/**
 * Created by Björn on 2016-03-03.
 */
public class Model {

    private static Model model;
    private App instance;

    private ArrayList<PhraseBook> phrasebooks;
    private String originLanguage;
    private String targetLanguage;

    private String currentPhrasebook;
    private String currentPhrase;

    private Model() {
        instance = App.get();
    }


    public static Model getInstance() {
        if (model == null) model = getSync();
        return model;
    }

    private synchronized static Model getSync() {
        if (model == null) model = new Model();
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

    public void setCurrentPhrasebook(String phrasebook) {
        currentPhrasebook = phrasebook;
    }

    public String getCurrentPhrasebook() {
        return currentPhrasebook;
    }

    public String getCurrentPhrase() {
        return currentPhrase;
    }

    public void setCurrentPhrase(String phrase) {
        currentPhrase = phrase;
    }

}
