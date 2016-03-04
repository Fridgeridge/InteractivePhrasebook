package se.chalmers.phrasebook.backend;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static se.chalmers.phrasebook.backend.FilePaths.PHRASEBOOK;

/**
 * Created by Björn on 2016-03-03.
 */
public class Model {

    private static Model model = new Model();

    private ArrayList<PhraseBook> phrasebooks;
    private String[] languageKeys;
    private Translator translator;
    private String originLanguage;
    private String targetLanguage;

    private String currentPhrasebook;

    private Model() {
        phrasebooks = new ArrayList<PhraseBook>();

        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream(PHRASEBOOK.getPath());//FIXME Utilize Context.getResources instead
            translator = new Translator(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        languageKeys = translator.getLanguages();

    }

    private Model(String origin, String target) {
        this();
        originLanguage = origin;
        targetLanguage = target;

        currentPhrasebook = "";
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

}
