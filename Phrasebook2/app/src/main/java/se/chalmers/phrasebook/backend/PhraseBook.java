package se.chalmers.phrasebook.backend;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import se.chalmers.phrasebook.backend.syntax.SyntaxTree;

/**
 * A class representing a phrasebook containing sentences
 * Created by David on 2016-02-19.
 */
public class PhraseBook {

    private String title;
    private ArrayList<SyntaxTree> phrases;
    private LinkedHashMap<String, SyntaxTree> syntaxToTree = new LinkedHashMap<>();

    public PhraseBook(String name) {
        title = name;
        phrases = new ArrayList<>();
    }

    public SyntaxTree getPhrase(String syntax) {
        System.out.println(syntax + " : this is the syntax");
        System.out.println(syntaxToTree.keySet().toString());
        if(syntaxToTree.get(syntax) != null) {
            return syntaxToTree.get(syntax);
        }
        return phrases.get(0);
    }

    public void setSyntaxToTree(LinkedHashMap<String, SyntaxTree> map) {
        syntaxToTree = map;
    }

    public boolean addPhrase(String desc, SyntaxTree phrase) {
        if(phrases.add(phrase)) {
            syntaxToTree.put(desc, phrase);
            return true;
        }
        return false;
    }

    public boolean removePhrase(SyntaxTree phrase) {
            return phrases.remove(phrase);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean updatePhrase(SyntaxTree updated, SyntaxTree old) {
        if(phrases.contains(old)) {
            phrases.add(phrases.indexOf(old), updated);
            return true;
        }
        return false;
    }

    public ArrayList<SyntaxTree> getPhrases() {
        return phrases;
    }
}
