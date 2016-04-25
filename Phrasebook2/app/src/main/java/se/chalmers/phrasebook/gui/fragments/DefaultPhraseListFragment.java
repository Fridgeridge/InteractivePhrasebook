package se.chalmers.phrasebook.gui.fragments;

import android.os.Bundle;

import java.util.ArrayList;

import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.syntax.SyntaxTree;

/**
 * Created by Björn on 2016-04-25.
 */
public class DefaultPhraseListFragment extends PhraseListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phrases.clear();
        phrases.addAll(model.getDefaultSentences());
    }
}
