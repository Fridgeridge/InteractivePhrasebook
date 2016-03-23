package se.chalmers.phrasebook.backend;

import org.grammaticalframework.pgf.Expr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

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

    public void addPhrase(SyntaxTree phrase) {
        phrases.add(phrase);
    }

    public void removePhrase(SyntaxTree phrase) throws IOException{
        if(phrases.contains(phrase)) {
            phrases.remove(phrase);
        } else {
            throw new IOException("phrase does not exist in phrasebook");
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<SyntaxTree> getPhrases() {
        return phrases;
    }
}
