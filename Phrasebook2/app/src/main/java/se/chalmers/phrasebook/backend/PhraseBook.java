package se.chalmers.phrasebook.backend;

import java.io.Serializable;
import java.util.ArrayList;

import se.chalmers.phrasebook.backend.syntax.SyntaxTree;

/**
 * A class representing a phrasebook containing sentences
 * Created by David on 2016-02-19.
 */
public class PhraseBook implements Serializable {

    private String title;
    private final ArrayList<SyntaxTree> phrases;
    private final boolean isEditable;

    public PhraseBook(String name, boolean editable) {
        this.isEditable = editable;
        title = name;
        phrases = new ArrayList<>();
    }

    public boolean getEditable() {
        return isEditable;
    }

    public void addPhrase(SyntaxTree phrase) {
        return phrases.add(phrase);
    }

    public boolean removePhrase(SyntaxTree phrase) {
            return phrases.remove(phrase);
    }

    //When each phrase has unique ID (default, favorite)
    public void removePhrase(String id) {
        for(int i = 0; i < phrases.size(); i++) {
            if(phrases.get(i).getId().equals(id)) {
                phrases.remove(i);
                return;
            }
        }
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
