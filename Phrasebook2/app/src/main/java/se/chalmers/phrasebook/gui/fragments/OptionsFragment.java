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

    private int nbrChoices;

    private int[] containers;


    //Skapa ett fragment med argument! :D Borde ta en array eller nåt med vilken
    // typ av val som ska göras så fragmenten kan skapan
    public static OptionsFragment newInstance(int nbrChoices) {
        OptionsFragment optionsFragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putInt("nbrChoices", nbrChoices);
        optionsFragment.setArguments(args);
        return optionsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();
        nbrChoices = getArguments().getInt("nbrChoices");
        addContainers();


        //Måste ha typ av choices också
        addFragments(nbrChoices);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);

    }

    //Array med int id:s till fragmentcontainers
    //Loopa igenom och lägga till de fragment som ska läggas till
    //Typen av fragmenten skulle också vara nice att ha i nån form av lite eller array


    private void addFragments(int nbrChoices) {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        //En schysst hashmap med en nice sträng och en array? :D
        for(int i = 0; i < nbrChoices; i++){

        }




        switch (nbrChoices) {
            case 1:
                Fragment spinnerFragment = new SpinnerFragment();
                transaction.add(R.id.child_fragment1, spinnerFragment);
                break;
            case 2:
                Fragment spinnerFragment2 = new SpinnerFragment();
                Fragment spinnerFragment3 = new SpinnerFragment();
                transaction.add(R.id.child_fragment1, spinnerFragment2);
                transaction.add(R.id.child_fragment2, spinnerFragment3);
                break;
            case 3:

                Fragment spinnerFragment4 = new SpinnerFragment();
                Fragment spinnerFragment5 = new SpinnerFragment();
                Fragment spinnerFragment6 = new SpinnerFragment();
                transaction.add(R.id.child_fragment1, spinnerFragment4);
                transaction.add(R.id.child_fragment2, spinnerFragment5);
                transaction.add(R.id.child_fragment3, spinnerFragment6);
                break;
        }

        transaction.commit();
    }

    private void addContainers() {

        containers[0] = R.id.child_fragment1;
        containers[1] = R.id.child_fragment2;
        containers[2] = R.id.child_fragment3;

    }

    //What type each fragment should be, preferably in a nice order
    //Spinner fragments need to know what attributes there are to choose from and what it is that you are altering
    //Textfield fragments need to know what you are altering


}
