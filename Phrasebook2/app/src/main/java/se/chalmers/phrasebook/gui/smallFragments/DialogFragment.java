package se.chalmers.phrasebook.gui.smallFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import se.chalmers.phrasebook.App;
import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;

/**
 * Created by matilda on 07/04/16.
 */
public class DialogFragment extends android.support.v4.app.DialogFragment {

    private ArrayList<String> phrasebooks;
    private Model model;

    public DialogFragment() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        model = Model.getInstance();

        View view = inflater.inflate(R.layout.dialog_fragment, container);
        getDialog().setTitle("Choose Phrasebook");

        phrasebooks = model.getMyPhrasebookTitles();

        ArrayAdapter adapter = new ArrayAdapter<String>(App.get(), R.layout.phrase_list_item, phrasebooks);
        final ListView phraseListView = (ListView) view.findViewById(R.id.phrasebookList);
        phraseListView.setAdapter(adapter);

        phraseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String name = model.translateToOrigin();

                model.getPhrasebookByTitle((String) phraseListView.getItemAtPosition(position)).addPhrase(name, model.getCurrentPhrase());

                getDialog().dismiss();

                Toast.makeText(getActivity(), "The phrase is added to " + (String)phraseListView.getItemAtPosition(position),
                        Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

}
