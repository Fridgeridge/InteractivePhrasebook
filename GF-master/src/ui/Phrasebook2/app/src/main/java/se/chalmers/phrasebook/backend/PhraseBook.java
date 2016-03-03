package se.chalmers.phrasebook.backend;

import org.grammaticalframework.pgf.Expr;

import java.util.HashSet;

/**
 * A class representing a phrasebook containing sentences
 * Created by David on 2016-02-19.
 */
public class PhraseBook {

    private String title;

    public PhraseBook(String name, Expr[] sentences) {
        title = name;

    }


}
