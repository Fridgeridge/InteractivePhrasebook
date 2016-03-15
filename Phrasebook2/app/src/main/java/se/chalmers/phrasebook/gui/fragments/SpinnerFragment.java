package se.chalmers.phrasebook.gui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.SyntaxTree;

/**
 * Created by matilda on 14/03/16.
 */
public class SpinnerFragment extends Fragment {

    Model model;
    SyntaxTree currentPhrase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();
        currentPhrase = model.getCurrentPhrase();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinner, container, false);

        TextView textView = (TextView)view.findViewById(R.id.text_view_spinner);
        Spinner spinner = (Spinner)view.findViewById(R.id.choice_spinner);

        //Hämta namnen på barnen som ska utgöra valen från currentPhrase
        //Lägg in dom i en ArrayList
        //Lägg in listan i spinnern

        //På nåt sätt borde man få fram vad det är för val som görs i just det här fragmentet och hämta motsvarande
        //string från xml res strings och sätta till textview


        return view;

    }

}
