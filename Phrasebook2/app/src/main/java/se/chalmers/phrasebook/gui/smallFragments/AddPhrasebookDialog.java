package se.chalmers.phrasebook.gui.smallFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.activities.NavigationActivity;
import se.chalmers.phrasebook.gui.fragments.MyPhrasebooksFragment;

/**
 * Created by matilda on 08/04/16.
 */
public class AddPhrasebookDialog extends android.support.v4.app.DialogFragment {

    private ArrayList<String> phrasebooks;
    private Model model;
    

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
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
                if(textField.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"Please enter a name",
                            Toast.LENGTH_SHORT).show();
                }else {
                    if (model.addPhrasebook(textField.getText().toString(), true)) {
                        getDialog().dismiss();
                        ((NavigationActivity)getActivity()).switchContent(new MyPhrasebooksFragment(), "");
                        getActivity().onBackPressed();
                    } else {
                        Toast.makeText(getActivity(),"The name is taken",
                                Toast.LENGTH_SHORT).show();
                    }
                }

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
