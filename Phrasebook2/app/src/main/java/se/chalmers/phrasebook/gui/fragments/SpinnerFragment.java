package se.chalmers.phrasebook.gui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.SyntaxTree;

/**
 * Created by matilda on 14/03/16.
 */
public class SpinnerFragment extends Fragment {

    Model model;
    SyntaxTree currentPhrase;
    ArrayList<String> spinnerItem;
    String label;

    public static SpinnerFragment newInstance(ArrayList spinnerItems, String label) {
        SpinnerFragment spinnerFragment = new SpinnerFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("spinnerItems", spinnerItems);
        args.putString("label", label);
        spinnerFragment.setArguments(args);
        return spinnerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();
        currentPhrase = model.getCurrentPhrase();

        spinnerItem = new ArrayList<>();
        spinnerItem = getArguments().getStringArrayList("spinnerItems");
        label = getArguments().getString("label");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinner, container, false);

        TextView textView = (TextView)view.findViewById(R.id.text_view_spinner);
        Spinner spinner = (Spinner)view.findViewById(R.id.choice_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, spinnerItem);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        textView.setText(label);

        return view;

    }

}
