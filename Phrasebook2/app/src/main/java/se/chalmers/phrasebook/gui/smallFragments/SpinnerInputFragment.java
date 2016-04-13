package se.chalmers.phrasebook.gui.smallFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.syntax.SyntaxNode;
import se.chalmers.phrasebook.backend.syntax.SyntaxNodeList;
import se.chalmers.phrasebook.gui.fragments.FragmentCommunicator;

/**
 * Created by matilda on 14/03/16.
 */
public class SpinnerInputFragment extends Fragment {

    private Model model;
    private int dataIndex;
    private String label;
    private SyntaxNodeList guiOptions;
    private String currentChoice;
    private Spinner spinner;

    FragmentCommunicator mCallback;

    public static SpinnerInputFragment newInstance(int optionIndex) {
        SpinnerInputFragment spinnerInputFragment = new SpinnerInputFragment();
        Bundle args = new Bundle();
        args.putInt("index", optionIndex);
        spinnerInputFragment.setArguments(args);
        return spinnerInputFragment;
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
                    + " must implement selectedInputListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.getInstance();
        dataIndex = getArguments().getInt("index");

        guiOptions = model.getCurrentPhrase().getOptions().get(dataIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.small_fragment_spinner, container, false);
        TextView textView = (TextView) view.findViewById(R.id.text_view_spinner);
        spinner = (Spinner) view.findViewById(R.id.choice_spinner);

        label = guiOptions.getQuestion();

        ArrayList<String> options = new ArrayList<>();

        for(SyntaxNode s: guiOptions.getChildren())
            options.add(s.getDesc());

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        currentChoice = guiOptions.getSelectedChild().getDesc();


        spinner.setSelection(options.indexOf(currentChoice),false);

        if(label!=null && label.equals("")) {
            ((LinearLayout)textView.getParent()).removeView(textView);
        } else {
            textView.setText(label);
        }



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spinner.getSelectedItem().toString() != currentChoice) {
                    sendMessage(dataIndex, spinner.getSelectedItemPosition());
                    currentChoice = spinner.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

    private void sendMessage(int optionIndex, int childIndex){

        Intent intent = new Intent();
        intent.setAction("gui_update");
        intent.putExtra("optionIndex", optionIndex);
        intent.putExtra("childIndex", childIndex);

        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(intent);

    }


    protected AdapterView getInputComponent(){
        return this.spinner;
    }

}