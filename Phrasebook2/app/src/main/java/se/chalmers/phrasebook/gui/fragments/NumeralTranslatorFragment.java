package se.chalmers.phrasebook.gui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Björn on 2016-04-25.
 */
public class NumeralTranslatorFragment extends TranslatorFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.model.setNumeralCurrentPhrase();
        super.onCreate(savedInstanceState);
        super.floatingActionButton.detach();
    }
}
