package se.chalmers.phrasebook.backend;


import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import se.chalmers.phrasebook.App;
import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.syntax.SyntaxNode;
import se.chalmers.phrasebook.backend.syntax.SyntaxNodeList;
import se.chalmers.phrasebook.backend.syntax.SyntaxTree;

/**
 * Created by Björn on 2016-03-03.
 */
public class Model {

    private static Model model;
    private App instance;

    SharedPreferences sharedPref;

    private Translator translator;
    private XMLParser parser;
    private TTSHandler ttsHandler;
    private PhraseBookHolder myPhrasebooks;
    private PhraseBookHolder defaultPhrasebooks;
    private Langs origin, target;
    private PhraseBook currentPhrasebook;
    private SyntaxTree currentPhrase;
    private ArrayList<SyntaxNodeList> optionsList;

    private Model() {
        instance = App.get();

        sharedPref = instance.getSharedPreferences(instance.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        String originKeyDef = (instance.getString(R.string.saved_origin_language_default));
        String targetKeyDef = (instance.getString(R.string.saved_target_language_default));


        try {
            InputStream phrasesPath = instance.getAssets().open(instance.getResources().getString(R.string.phrases_path));
            InputStream gfPath = instance.getAssets().open(instance.getResources().getString(R.string.grammatical_framework_path));

            parser = new XMLParser(phrasesPath);
            translator = new Translator(gfPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ttsHandler = new TTSHandler(instance);

        //Do not move up
        setTargetLanguage(sharedPref.getString(instance.getString(R.string.saved_target_language), targetKeyDef));
        setOriginLanguage(sharedPref.getString(instance.getString(R.string.saved_origin_language), originKeyDef));

        myPhrasebooks = FileWriter.readFromFile(instance);

        //If the reading of saved phrasebooks does not work...
        if (myPhrasebooks == null)
            myPhrasebooks = new PhraseBookHolder();

        defaultPhrasebooks = new PhraseBookHolder();

        //Hardcoded default testing phrasebook
        PhraseBook tourism = new PhraseBook("Default", false);
        for (String s : parser.getSentencesData().keySet()) {
            tourism.addPhrase(translator.translateToOrigin(parser.getSyntaxTree(s).getAdvSyntax())
                    , parser.getSyntaxTree(s));
        }
        defaultPhrasebooks.addPhraseBook(tourism);
    }

    public static Model getInstance() {
        if (model == null) model = getSync();
        return model;
    }

    //Requires unique name
    public boolean addPhrasebook(String name, boolean editable) {
        for (PhraseBook book : myPhrasebooks.getPhraseBooks()) {
            if (book.getTitle().equals(name)) {
                return false;
            }
        }
        PhraseBook pb = new PhraseBook(name, editable);
        myPhrasebooks.addPhraseBook(pb);
        FileWriter.saveToFile(instance, myPhrasebooks);
        return true;
    }

    public PhraseBook getPhrasebookByTitle(String title) {
        for (PhraseBook book : myPhrasebooks.getPhraseBooks()) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }

        for (PhraseBook book : defaultPhrasebooks.getPhraseBooks()) {
            if (book.getTitle().equals(title)) {
                return book;
            }
        }

        return null;
    }

    private synchronized static Model getSync() {
        if (model == null) model = new Model();
        return model;
    }


    public void savePreferences() {
        SharedPreferences.Editor editor = sharedPref.edit();

        if (origin != null)
            editor.putString(instance.getString(R.string.saved_origin_language), origin.getKey());

        if (target != null)
            editor.putString(instance.getString(R.string.saved_target_language), target.getKey());

        editor.apply();
    }

    public void deleteAllPreferences() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.apply();
    }

    public ArrayList<String> getMyPhrasebookTitles() {
        ArrayList<String> names = new ArrayList<String>();
        for (PhraseBook book : myPhrasebooks.getPhraseBooks()) {
            names.add(book.getTitle());
        }
        return names;
    }

    public ArrayList<String> getDefaultPhrasebookTitles() {
        ArrayList<String> names = new ArrayList<String>();
        for (PhraseBook book : defaultPhrasebooks.getPhraseBooks()) {
            names.add(book.getTitle());
        }
        return names;
    }

    public HashMap<String, String> getSentences() {
        return parser.getSentencesData();
    }

    public void update(int optionIndex, SyntaxNodeList target, int childIndex, boolean isAdvanced) {
        currentPhrase.setSelectedChild(optionIndex, target, childIndex, isAdvanced);
    }

    public void playCurrentTargetPhrase() {
        ttsHandler.playSentence(translateToTarget());
    }

    public ArrayList<String> getSentencesInCurrentPhrasebook() {
        ArrayList<String> phrases = new ArrayList<String>();
        for (int i = 0; i < currentPhrasebook.getPhrases().size(); i++) {
            if(currentPhrasebook.getEditable()) {
                phrases.add(translator.translateToOrigin(currentPhrasebook.
                        getPhrases().get(i).getAdvSyntax()));
            } else {
                phrases.add(getDescFromPos(i));
            }
        }
        return phrases;
    }

    public ArrayList<String> getDefaultSentences() {
        ArrayList<String> phrases = new ArrayList<String>();
        for (SyntaxTree tree : currentPhrasebook.getPhrases()) {
            phrases.add(tree.getId());
        }
        return phrases;
    }

    public String translateToOrigin() {
        return translator.translateToOrigin(getCurrentPhrase().getAdvSyntax());
    }

    public String translateToTarget() {
        return translator.translateToTarget(getCurrentPhrase().getAdvSyntax());
    }

    public void setOriginLanguage(String originLanguageKey) {
        origin = Langs.getLang(originLanguageKey);
        translator.setOriginLanguage(originLanguageKey);
        savePreferences();
    }

    public void setTargetLanguage(String targetLanguageKey) {
        target = Langs.getLang(targetLanguageKey);
        translator.setTargetLanguage(targetLanguageKey);
        ttsHandler.setTargetTTSLanguage(target);
        savePreferences();
    }

    public void setCurrentPhrasebook(PhraseBook phrasebook) {
        currentPhrasebook = phrasebook;
    }

    public void setCurrentPhrase(int position) {
        currentPhrase = (SyntaxTree)currentPhrasebook.getPhrases().toArray()[position];
    }

    public String getDescFromPos(int pos) {
        return parser.getSentencesData()
                .get((String) (parser.getSentencesData().keySet().toArray()[pos]));
    }

    public void setNumeralCurrentPhrase() {
        for (int i = 0; i < parser.getSentencesData().values().size(); i++) {
            if ((parser.getSentencesData().keySet().toArray()[i]).equals("NNumeral")) {
                currentPhrase = parser.getSyntaxTree((String)parser.getSentencesData()
                        .keySet().toArray()[i]);
            }
        }
    }

    public Langs getOriginLang() {
        return origin;
    }

    public Langs getTargetLang() {
        return target;
    }

    public boolean removePhrasebook(PhraseBook phraseBook){
        boolean status = myPhrasebooks.removePhraseBook(phraseBook);
        FileWriter.saveToFile(instance,myPhrasebooks);
        return status;
    }

    public PhraseBook getCurrentPhrasebook() {
        return currentPhrasebook;
    }

    public SyntaxTree getCurrentPhrase() {
        return currentPhrase;
    }


}

