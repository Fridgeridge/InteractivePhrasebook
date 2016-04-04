package se.chalmers.phrasebook.gui.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.smallFragments.SpinnerFragment;
import se.chalmers.phrasebook.gui.smallFragments.SwipeFragment;
import se.chalmers.phrasebook.gui.smallFragments.TranslationFragment;

/**
 * Created by matilda on 04/04/16.
 */
public class TranslatorFragment extends Fragment {

    private String phrase;

    public TranslatorFragment() {
        // Required empty public constructor
    }

    public static TranslatorFragment newInstance(String phrase) {
        TranslatorFragment fragment = new TranslatorFragment();
        Bundle args = new Bundle();
        args.putString("phrase", phrase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //phrase = getArguments().getString("phrase");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_translator, container, false);

        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        
        fm.add(R.id.containerfor_translation, new TranslationFragment());
        fm.add(R.id.containerfor_options, new SwipeFragment());

        fm.commit();


        return view;
    }


}
