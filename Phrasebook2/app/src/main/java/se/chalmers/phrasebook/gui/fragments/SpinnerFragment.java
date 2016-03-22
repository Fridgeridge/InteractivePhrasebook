package se.chalmers.phrasebook.gui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.SyntaxNode;
import se.chalmers.phrasebook.backend.SyntaxTree;
import se.chalmers.phrasebook.gui.adapters.SwipeAdapter;

/**
 * Created by matilda on 14/03/16.
 */
public class SpinnerFragment extends Fragment {

    private Model model;
    private int dataIndex;
    private String label;
    private ArrayList<String> guiOptions;
    private String currentChoice;
    private Spinner spinner;

    OnChangeListener mCallback;

    public static SpinnerFragment newInstance(int mapIndex) {
        SpinnerFragment spinnerFragment = new SpinnerFragment();
        Bundle args = new Bundle();
        args.putInt("index", mapIndex);
        spinnerFragment.setArguments(args);
        return spinnerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();

        dataIndex = getArguments().getInt("index");

        guiOptions = model.getNodeOptions(dataIndex);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinner, container, false);

        TextView textView = (TextView) view.findViewById(R.id.text_view_spinner);
        spinner = (Spinner) view.findViewById(R.id.choice_spinner);

        label = guiOptions.get(0);
        guiOptions.remove(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, guiOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        currentChoice = spinner.getSelectedItem().toString();

        textView.setText(label);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItem().toString() != currentChoice) {
                    mCallback.onOptionSelected(dataIndex, label, currentChoice, spinner.getSelectedItem().toString());
                    currentChoice = spinner.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

    public interface OnChangeListener {
        public void onOptionSelected(int dataIndex, String label, String currentChoice, String newChoice);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
             mCallback = (OnChangeListener) activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnChangeListener");
        }
    }
}