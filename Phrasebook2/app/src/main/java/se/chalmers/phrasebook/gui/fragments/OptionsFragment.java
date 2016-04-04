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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.SyntaxTree;

public class OptionsFragment extends Fragment {

    private Model model;

    private ArrayList<LinkedHashMap> options;
    private ArrayList<LinkedHashMap> advancedOptions;

    private int type;

    private int[] containers;

    public static OptionsFragment newInstance(int type) {
        OptionsFragment optionsFragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putInt("index", type);
        optionsFragment.setArguments(args);
        return optionsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();

        type = getArguments().getInt("index");

        options = model.getCurrentPhrase().getOptions();
        advancedOptions = new ArrayList<>();
        containers = new int[6];

        addContainers();
        addFragments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_options, container, false);

    }

    private void addFragments() {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        if(type == 1) {
            for (int i = 0; i < options.size(); i++) {
                if (options.get(i) != null) {
                    transaction.add(containers[i], SpinnerFragment.newInstance(i));
                }
            }
        }else if(type == 2){
            for (int i = 0; i < advancedOptions.size(); i++) {
                if (advancedOptions.get(i) != null)
                    transaction.add(containers[i], SpinnerFragment.newInstance(i));
            }
        }

        transaction.commit();

    }

    private void addContainers() {

        containers[0] = R.id.child_fragment1;
        containers[1] = R.id.child_fragment2;
        containers[2] = R.id.child_fragment3;
        containers[3] = R.id.child_fragment4;
        containers[4] = R.id.child_fragment5;
        containers[5] = R.id.child_fragment6;

    }

}
