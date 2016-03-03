package se.chalmers.phrasebook.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Björn on 2016-03-03.
 */
public class Model {

    private static Model model = new Model();

    private List<Language> languages;
    private ArrayList<PhraseBook> phrasebooks;

    private String originLanguage;
    private String targetLanguage;

    private Model(){
        languages = new ArrayList<Language>();
        phrasebooks = new ArrayList<PhraseBook>();
    }

    private Model(String origin, String target) {
        originLanguage = origin;
        targetLanguage = target;

        languages = new ArrayList<Language>();
        phrasebooks = new ArrayList<PhraseBook>();
    }

    public static Model getInstance(){
        return model;
    }

    public String getOrginLanguage() {
        return originLanguage;
    }

    public void setOrginLanguage(String originLanguage) {
        this.originLanguage = originLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public List<Language> getLanguages(){
        return languages;
    }

}
