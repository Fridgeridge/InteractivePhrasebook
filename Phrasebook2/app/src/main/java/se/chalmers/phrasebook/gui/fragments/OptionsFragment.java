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

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.SyntaxTree;

public class OptionsFragment extends Fragment {

    private Model model;

    private int swipePageNbr;
    private ArrayList<ArrayList> options;
    private ArrayList<ArrayList> spinnerData;

    private int[] containers;

    public static OptionsFragment newInstance(int swipePageNbr) {
        OptionsFragment optionsFragment = new OptionsFragment();
        Bundle args = new Bundle();
        args.putInt("swipePageNbr", swipePageNbr);
        optionsFragment.setArguments(args);
        return optionsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();

        spinnerData = new ArrayList<>();

        swipePageNbr = getArguments().getInt("swipePageNbr");
        options = model.getCurrentPhrase().getOptions();
        containers = new int[3];

        addContainers();
        initSpinnerFragmentInfo(swipePageNbr);

        addFragments();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);

    }

    private void addFragments() {

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

        for(int i = 0; i < spinnerData.size(); i++){
            transaction.add(containers[i], SpinnerFragment.newInstance(spinnerData.get(i)));
        }

        transaction.commit();
    }

    private void addContainers() {

        containers[0] = R.id.child_fragment1;
        containers[1] = R.id.child_fragment2;
        containers[2] = R.id.child_fragment3;

    }

    private void initSpinnerFragmentInfo(int swipePageNbr){

        if(swipePageNbr == 1){
            if(options.size() >= 3) {
                for (int i = 0; i < 3; i++) {
                    if (options.get(i) != null && options.size() != 0)
                        spinnerData.add(options.get(i));
                }
            }else{
                for (int i = 0; i < options.size(); i++) {
                    if (options.get(i) != null && options.size() != 0)
                        spinnerData.add(options.get(i));
                }
            }
        }else if(swipePageNbr == 2){
            if(options.size() >= 6) {
                for (int i = 3; i < 6; i++) {
                    if (options.get(i) != null)
                        spinnerData.add(options.get(i));
                }
            }else{
                for (int i = 3; i < options.size(); i++) {
                    if (options.get(i) != null)
                        spinnerData.add(options.get(i));
                }
            }
        }else if(swipePageNbr == 3){
            for(int i = 6; i < options.size(); i++) {
                if(options.get(i) != null)
                spinnerData.add(options.get(i));
            }
        }
    }

}
