package se.chalmers.phrasebook.gui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    ArrayList<String> data;
    String label;

    public static SpinnerFragment newInstance(ArrayList<String> options) {
        SpinnerFragment spinnerFragment = new SpinnerFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("data", options);
        spinnerFragment.setArguments(args);
        return spinnerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();
        currentPhrase = model.getCurrentPhrase();

        data = new ArrayList<>();
        data = getArguments().getStringArrayList("data");

        label = data.get(0);
        data.remove(0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinner, container, false);

        TextView textView = (TextView)view.findViewById(R.id.text_view_spinner);
        Spinner spinner = (Spinner)view.findViewById(R.id.choice_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        textView.setText(label);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Uppdatera översättingarna, egentligen bara text som behöver sättas till textFields, behöver nog inte
                //bygga om hela fragmentet
                //Funderar på om man ska bygga om OptionsFragment för att kunna fånga upp om det ska till fler småfragment
                //Man kanske skulle kunna kolla om det valda objektet ska generera ett till fragment så man slipper
                //Bygga om i onödan
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;

    }

}
