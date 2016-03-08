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
    private String[] languageKeys;
    private String originLanguage;
    private String targetLanguage;

    private Model() {
        languages = new ArrayList<Language>();
        phrasebooks = new ArrayList<PhraseBook>();
    }

    private Model(String origin, String target) {
        this();
        originLanguage = origin;
        targetLanguage = target;
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

}
