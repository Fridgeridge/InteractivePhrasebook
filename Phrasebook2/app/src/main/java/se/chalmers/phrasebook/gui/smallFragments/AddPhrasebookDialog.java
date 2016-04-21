package se.chalmers.phrasebook.gui.smallFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;

/**
 * Created by matilda on 08/04/16.
 */
public class AddPhrasebookDialog extends android.support.v4.app.DialogFragment {

    private ArrayList<String> phrasebooks;
    private Model model;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        model = Model.getInstance();

        View view = inflater.inflate(R.layout.dialog_add_phrasebook, container);
        getDialog().setTitle("Add Phrasebook");

        final EditText textField = (EditText) view.findViewById(R.id.editText_phrasebook_name);
        Button cancelButton = (Button) view.findViewById(R.id.button);
        Button addButton = (Button) view.findViewById(R.id.button2);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.addPhrasebook(textField.getText().toString())){
                    getDialog().dismiss();
                }else{
                    //Skriv nåt coolt meddelande
                    throw new IllegalArgumentException();
                }

            }
        });

        return view;
    }

}
