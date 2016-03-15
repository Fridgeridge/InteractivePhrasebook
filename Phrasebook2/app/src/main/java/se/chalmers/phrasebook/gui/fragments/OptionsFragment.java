package se.chalmers.phrasebook.gui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.SyntaxTree;

public class OptionsFragment extends Fragment {

    private Model model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();

        addFragments(model.getCurrentPhrase());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);

    }

    private void addFragments(SyntaxTree currentPhrase){

        Fragment spinnerFragment = new SpinnerFragment();
        Fragment textFieldFragment = new TextFieldFragment();
        Fragment spinnerFrag = new SpinnerFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.child_fragment1, spinnerFragment);
        transaction.add(R.id.child_fragment2, spinnerFrag);
        transaction.commit();
    }

}
