package se.chalmers.phrasebook.gui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Langs;
import se.chalmers.phrasebook.backend.Model;

/**
 * Created by matilda on 05/04/16.
 */
public class ChangeLanguageFragment extends Fragment {

    private Model model;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();
        context = getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_language, container, false);

        final Spinner origin = (Spinner) view.findViewById(R.id.origin_spinner1);
        final Spinner target = (Spinner) view.findViewById(R.id.target_spinner1);

        ArrayList al = Langs.getLanguages();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, al);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        origin.setAdapter(adapter);
        target.setAdapter(adapter);

        Button apply = (Button) view.findViewById(R.id.apply_button);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Frågan är om vi ens vill ha en knapp här...
                model.setOriginLanguage(Langs.getKey(origin.getSelectedItem().toString()));
                model.setTargetLanguage(Langs.getKey(target.getSelectedItem().toString()));
            }
        });


        return view;
    }

}
