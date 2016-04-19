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
    private ArrayList<PhraseBook> phrasebooks;
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


        phrasebooks = new ArrayList<>();
        //Hardcoded default testing phrasebook
        PhraseBook tourism = new PhraseBook("Tourism");
        for (String s : parser.getSentencesData().keySet()) {
            tourism.addPhrase(translator.translateToOrigin(parser.buildSyntaxTree(parser.getSentence(s)).getSyntax())
                    , parser.buildSyntaxTree(parser.getSentence(s)));
        }
        phrasebooks.add(tourism);
    }

    public static Model getInstance() {
        if (model == null) model = getSync();
        return model;
    }

    //Requires unique name
    public boolean addPhrasebook(String name) {
        for (PhraseBook book : phrasebooks) {
            if (book.getTitle().equals(name)) {
                return false;
            }
        }
        PhraseBook pb = new PhraseBook(name);
        phrasebooks.add(pb);
        return true;
    }

    public PhraseBook getPhrasebookByTitle(String title) {
        for (PhraseBook book : phrasebooks) {
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
        editor.commit();
    }

    public ArrayList<String> getPhrasebookTitles() {
        ArrayList<String> names = new ArrayList<String>();
        for (PhraseBook book : phrasebooks) {
            names.add(book.getTitle());
        }
        return names;
    }

    public HashMap<String, String> getSentences() {
        return parser.getSentencesData();
    }


    //TODO Remove method and find suitable abstraction layer
    public XMLParser getParser() {
        return parser;
    }


    //TODO Consider moving to a separate class
    public static String getKey(String name, Map<String, String> map) {
        for (Map.Entry<String, String> e : map.entrySet()) {
            if (e.getValue().equals(name)) return e.getKey();
        }
        return null;
    }

    public void update(int optionIndex, SyntaxNodeList target, int childIndex) {
        currentPhrase.setSelectedChild(optionIndex, target, childIndex);
        playCurrentTargetPhrase();
    }

    public boolean isNodeSelected(SyntaxNode node, LinkedHashMap options) {
        Iterator iterate = options.entrySet().iterator();
        if (iterate.hasNext()) {
            Map.Entry entry = (Map.Entry) iterate.next();
            SyntaxNode parent = (SyntaxNode) entry.getValue();
            for (int i = 0; i < parent.getSyntaxNodes().size(); i++) {
                if (parent.getSyntaxNodes().get(i).getSelectedChild() == node) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public ArrayList<String> getNodeOptions(int index) {
        optionsList = currentPhrase.getOptions();
        ArrayList<String> guiOptions = new ArrayList<>();

        for (SyntaxNodeList l : optionsList) {
            if (l.getQuestion() != null && !l.getQuestion().isEmpty())
                guiOptions.add(l.getQuestion());
        }

        return guiOptions;
    }


    public void playCurrentTargetPhrase() {
        ttsHandler.playSentence(translateToTarget());
    }

    public ArrayList<String> getSentencesInCurrentPhrasebook() {
        ArrayList<String> phrases = new ArrayList<String>();
        for (SyntaxTree tree : currentPhrasebook.getPhrases()) {
            phrases.add(translator.translateToOrigin(tree.getSyntax()));
        }
        return phrases;
    }

    public String translateToOrigin() {
        return translator.translateToOrigin(getCurrentPhrase().getSyntax());
    }

    public String translateToTarget() {
        return translator.translateToTarget(getCurrentPhrase().getSyntax());
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
        currentPhrase = parser.buildSyntaxTree(parser.getSentence
                ((String) (parser.getSentencesData().keySet().toArray()[position])));

    }

    public Langs getOriginLang() {
        return origin;
    }

    public Langs getTargetLang() {
        return target;
    }

    public PhraseBook getCurrentPhrasebook() {
        return currentPhrasebook;
    }

    public SyntaxTree getCurrentPhrase() {
        return currentPhrase;
    }


}

