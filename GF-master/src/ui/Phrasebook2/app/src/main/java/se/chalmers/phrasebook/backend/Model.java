package se.chalmers.phrasebook.backend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Björn on 2016-03-03.
 */
public class Model {

    private List<Language> languages;
    private ArrayList<PhraseBook> phrasebooks;

    private Language orginLanguage;
    private Language targetLanguage;

    public Model(Language orgin, Language target) {
        orginLanguage = orgin;
        targetLanguage = target;
        languages = new ArrayList<Language>();
        phrasebooks = new ArrayList<PhraseBook>();
    }

    public Language getOrginLanguage() {
        return orginLanguage;
    }

    public void setOrginLanguage(Language orginLanguage) {
        this.orginLanguage = orginLanguage;
    }

    public Language getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(Language targetLanguage) {
        this.targetLanguage = targetLanguage;
    }
}
