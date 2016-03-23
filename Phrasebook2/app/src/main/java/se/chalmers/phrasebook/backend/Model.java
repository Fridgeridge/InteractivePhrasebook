package se.chalmers.phrasebook.backend;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import se.chalmers.phrasebook.App;
import se.chalmers.phrasebook.R;

/**
 * Created by Björn on 2016-03-03.
 */
public class Model {

    private static Model model;
    private App instance;

    private TestSentence testSentence;

    private Translator translator;
    private XMLParser parser;
    
    private String originLanguage;
    private String targetLanguage;

    private ArrayList<PhraseBook> phrasebooks;
    private String currentPhrasebook;
    private SyntaxTree currentPhrase;
    private ArrayList<LinkedHashMap> optionsList;

    private Model() {
        instance = App.get();

        try {
            InputStream phrasesPath = instance.getAssets().open(instance.getResources().getString(R.string.phrases_path));
            InputStream gfPath = instance.getAssets().open(instance.getResources().getString(R.string.grammatical_framework_path));

            parser = new XMLParser(phrasesPath);
            translator = new Translator(gfPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Model getInstance() {
        if (model == null) model = getSync();
        return model;
    }

    private synchronized static Model getSync() {
        if (model == null) model = new Model();
        return model;
    }

    public HashMap<String,String> getSentences(){
        return parser.getSentencesData();
    }


    //TODO Remove method and find suitable abstraction layer
    public XMLParser getParser(){
        return parser;
    }


    //TODO Consider moving to a separate class
    public static String getKey(String name,Map<String,String> map){
        for(Map.Entry<String,String> e:map.entrySet()){
            if(e.getValue().equals(name)) return e.getKey();
        }
        return null;
    }

    public void update(int listIndex, String parent, String oldOption, String newOption){
        currentPhrase.setSelectedChild(((SyntaxNode)currentPhrase.getOptions().get(listIndex).get(parent)),
                ((SyntaxNode)currentPhrase.getOptions().get(listIndex).get(oldOption)),
                ((SyntaxNode)(currentPhrase.getOptions().get(listIndex).get(newOption))));
    }


    public ArrayList<String> getNodeOptions(int index){
        optionsList = currentPhrase.getOptions();

        LinkedHashMap map = optionsList.get(index);

        Iterator entries = map.entrySet().iterator();

        ArrayList<String> guiOptions = new ArrayList<>();

        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            Object key = thisEntry.getKey();
            Object value = thisEntry.getValue();

            if(((SyntaxNode)value).getIsSelected()){
                if(guiOptions.size() > 1) {
                    guiOptions.add(1, (String) key);
                }else{
                    guiOptions.add((String)key);
                }

            }else {
                guiOptions.add((String) key);
            }

        }
        return guiOptions;

    }


    public String translateToOrigin(){
        return translator.translateToOrigin(getCurrentPhrase().getSyntax());
    }

    public String translateToTarget(){
        return translator.translateToTarget(getCurrentPhrase().getSyntax());
    }

    public void setOriginLanguage(String originLanguage) {
        this.originLanguage = originLanguage;
        translator.setOriginLanguage(originLanguage);
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
        translator.setTargetLanguage(targetLanguage);
    }

    public void setCurrentPhrasebook(String phrasebook) {
        currentPhrasebook = phrasebook;
    }

    public void setCurrentPhrase(String phraseDescription) {

        String id = getKey(phraseDescription, this.getSentences());

        currentPhrase = parser.buildSyntaxTree(parser.getSentence(id));
    }

    public String getOriginLanguage() {
        return originLanguage;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public String getCurrentPhrasebook() {
        return currentPhrasebook;
    }

    public SyntaxTree getCurrentPhrase() {
        return currentPhrase;
    }

    public TestSentence getTestSentence() {
        return testSentence;
    }

    public void setTestSentence(TestSentence testSentence) {
        this.testSentence = testSentence;
    }


}
