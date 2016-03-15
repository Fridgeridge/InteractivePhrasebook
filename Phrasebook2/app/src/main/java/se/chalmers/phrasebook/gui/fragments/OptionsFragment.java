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


    //Tanken är att skicka in en instans av phrase klassen vi pratade om och hämta all gui data därifrån på nåt vänster
    //Då kan vi nog lägga till de fragmenten som ska vara där med rätt data.
    private void addFragments(String currentPhrase){

        Fragment spinnerFragment = new SpinnerFragment();
    //    Fragment textFieldFragment = new TextFieldFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.child_fragments, spinnerFragment);
    //    transaction.add(R.id.child_fragments, textFieldFragment);
        transaction.commit();

    }

}
