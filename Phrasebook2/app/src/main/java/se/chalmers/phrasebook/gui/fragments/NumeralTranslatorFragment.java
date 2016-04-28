package se.chalmers.phrasebook.gui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.chalmers.phrasebook.backend.Model;

/**
 * Created by Björn on 2016-04-25.
 */
public class NumeralTranslatorFragment extends TranslatorFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.getInstance();
        System.out.println("Does it even create");
        super.model.setNumeralCurrentPhrase();
        if(floatingActionButton != null) {
            floatingActionButton.detach();
            System.out.println("Detaching");
        }
    }

    public static NumeralTranslatorFragment newInstance() {
        NumeralTranslatorFragment fragment = new NumeralTranslatorFragment();
        Bundle args = new Bundle();
        args.putString("phrase", "Number");
        fragment.setArguments(args);
        return fragment;
    }
}
