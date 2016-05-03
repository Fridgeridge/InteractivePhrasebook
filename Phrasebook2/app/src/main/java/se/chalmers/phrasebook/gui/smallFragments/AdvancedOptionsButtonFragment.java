package se.chalmers.phrasebook.gui.smallFragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;


/**
 * Created by matilda on 02/05/16.
 */
public class AdvancedOptionsButtonFragment extends Fragment{

    private boolean active;
    private Model model;

    public static Fragment newInstance(boolean active) {
        AdvancedOptionsButtonFragment advancedOptionsButtonFragment = new AdvancedOptionsButtonFragment();


        Bundle args = new Bundle();
        args.putBoolean("active", active);
        advancedOptionsButtonFragment.setArguments(args);

        return advancedOptionsButtonFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();
        this.active = getArguments().getBoolean("active");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.advanced_options_button, container, false);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        checkBox.setText("Activate advanced options");

        if(active == true){
            checkBox.setChecked(true);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    model.getCurrentPhrase().setAdvActivated(true);
                    //uppdatera GUI
                } else {
                    model.getCurrentPhrase().setAdvActivated(false);
                    System.out.println("Unchecked!");
                    //uppdatera GUI
                }
            }
        });

        return view;
    }

}
