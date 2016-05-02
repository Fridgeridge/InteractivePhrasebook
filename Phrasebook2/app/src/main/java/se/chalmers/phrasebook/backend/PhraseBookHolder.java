package se.chalmers.phrasebook.backend;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by David on 2016-05-02.
 */
public class PhraseBookHolder implements Serializable{
    private ArrayList<PhraseBook> phraseBooks;


    public PhraseBookHolder(){
        phraseBooks = new ArrayList<PhraseBook>();
    }

    public ArrayList<PhraseBook> getPhraseBooks() {
        return phraseBooks;
    }

    public boolean removePhraseBook(PhraseBook phraseBook){
       return phraseBooks.remove(phraseBook);
    }

    public void addPhraseBook(PhraseBook phraseBook) {
        phraseBooks.add(phraseBook);
    }


}
