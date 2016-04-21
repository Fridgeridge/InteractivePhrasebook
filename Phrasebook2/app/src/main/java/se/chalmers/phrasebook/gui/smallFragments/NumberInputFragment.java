package se.chalmers.phrasebook.gui.smallFragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.syntax.SyntaxNode;
import se.chalmers.phrasebook.backend.syntax.SyntaxNodeList;

/**
 * Created by David on 2016-04-07.
 */
public class NumberInputFragment extends Fragment {

    private Model model;
    private int spinnerIndex;
    private SyntaxNodeList options;

    private String label;
    private String currentChoice;
    private Spinner spinner;


    public static NumberInputFragment newInstance(String title) {
        NumberInputFragment numberInputFragment = new NumberInputFragment();
        Bundle args = new Bundle();

        args.putString("title", title);

        numberInputFragment.setArguments(args);
        return numberInputFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.getInstance();

        label = getArguments().getString("title");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.small_fragment_number, container, false);
        TextView viewLabel = (TextView) view.findViewById(R.id.textView_number);

        viewLabel.setText(label);



        return view;
    }

    private void sendMessage(int optionIndex, int childIndex) {

 //       InputHolderFragment fragment = (InputHolderFragment) getParentFragment();

 //       fragment.updateSyntax(optionIndex, options,childIndex);


//        Intent intent = new Intent();
//        intent.setAction("gui_update");
//        intent.putExtra("optionIndex", optionIndex);
//        intent.putExtra("childIndex", childIndex);
//
//        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).sendBroadcast(intent);

    }

    
}
