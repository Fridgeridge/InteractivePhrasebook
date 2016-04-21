package se.chalmers.phrasebook.gui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    private Spinner origin, target;

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

        origin = (Spinner) view.findViewById(R.id.origin_spinner1);
        target = (Spinner) view.findViewById(R.id.target_spinner1);

        ArrayList<String> al = Langs.getLanguages();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, al);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        origin.setAdapter(adapter);
        target.setAdapter(adapter);

        int currentSelectedOrigin = al.indexOf(model.getOriginLang().getLang());
        int currentSelectedTarget = al.indexOf(model.getTargetLang().getLang());

        origin.setSelection(currentSelectedOrigin);
        origin.setSelection(currentSelectedTarget);

        return view;
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.equals(origin)) {
                model.setOriginLanguage(Langs.getKey(parent.getSelectedItem().toString()));
            }else if (parent.equals(target)) {
                model.setTargetLanguage(Langs.getKey(target.getSelectedItem().toString()));
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

}
