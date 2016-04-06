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

    public PhraseBook(String name) {
        title = name;
        phrases = new ArrayList<>();
    }

    public boolean addPhrase(String desc, SyntaxTree phrase) {
        return phrases.add(phrase);
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
