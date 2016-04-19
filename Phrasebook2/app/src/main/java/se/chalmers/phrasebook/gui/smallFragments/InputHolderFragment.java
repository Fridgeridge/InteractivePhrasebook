package se.chalmers.phrasebook.gui.smallFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.syntax.SyntaxNodeList;
import se.chalmers.phrasebook.gui.FragmentCommunicator;

/**
 * Created by David on 2016-04-13.
 */


public class InputHolderFragment extends Fragment {
    private Model model;

    private int optionIndex;
    private SyntaxNodeList guiOptions;
    private FragmentManager fragmentManager;
    private ArrayList<String> fragmentTags;

    private FragmentCommunicator mCallback;

    public static Fragment newInstance(int optionIndex) {
        InputHolderFragment inputHolderFragment = new InputHolderFragment();

        Bundle args = new Bundle();
        args.putInt("index", optionIndex);
        inputHolderFragment.setArguments(args);

        return inputHolderFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (FragmentCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.getInstance();
        optionIndex = getArguments().getInt("index");
        fragmentManager = getChildFragmentManager();
        guiOptions = model.getCurrentPhrase().getOptions().get(optionIndex);
        fragmentTags = new ArrayList<String>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.small_fragment_input_holder, container, false);
        TextView textView = (TextView) view.findViewById(R.id.holderOptionText);

        textView.setText(guiOptions.getQuestion());
        addListInputFragment(guiOptions);

        return view;
    }

    private void addListInputFragment(SyntaxNodeList l) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        String s = "Input: " + l.toString();
        transaction.add(R.id.input_holder, SpinnerInputFragment.newInstance(optionIndex, null, l), s);

        fragmentTags.add(s);
        transaction.commit();
    }

    private void redrawInputGUI() {
        clearInputs();
        addInputFragments(guiOptions);
    }

    private void addInputFragments(SyntaxNodeList snl) {
        if (snl != null) {
            addListInputFragment(snl);
            if (snl.getSelectedChild().isModular()) {
                ArrayList<SyntaxNodeList> modularLists = snl.getSelectedChild().getModularSyntaxNodes();
                for (SyntaxNodeList nodeList : modularLists)
                    addInputFragments(nodeList);
            }
        }
    }

    private void clearInputs() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (String tag : fragmentTags) {
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            transaction.remove(fragment);
        }
        fragmentTags.clear();
        transaction.commit();
    }


    public void updateSyntax(int optionIndex, SyntaxNodeList l, int childIndex) {
        if (this.optionIndex == optionIndex) {
            mCallback.updateSyntax(optionIndex,l,childIndex);

            this.redrawInputGUI();
        }

    }


}
