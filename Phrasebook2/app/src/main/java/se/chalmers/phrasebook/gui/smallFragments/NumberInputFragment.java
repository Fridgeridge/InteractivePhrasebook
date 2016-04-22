package se.chalmers.phrasebook.gui.smallFragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
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

    private int optionIndex;
    private String label;
    private int defaultInt;

    private int currentNumber;

    public static NumberInputFragment newInstance(int optionIndex, String title, int defaultInt) {
        NumberInputFragment numberInputFragment = new NumberInputFragment();
        Bundle args = new Bundle();

        args.putInt("optionIndex", optionIndex);
        args.putString("title", title);
        args.putInt("defaultInt", defaultInt);

        numberInputFragment.setArguments(args);
        return numberInputFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.getInstance();

        optionIndex = getArguments().getInt("optionIndex");
        label = getArguments().getString("title");
        defaultInt = getArguments().getInt("defaultInt");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.small_fragment_number, container, false);
        TextView viewLabel = (TextView) view.findViewById(R.id.textView_number);
        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        final EditText editNumber = (EditText) view.findViewById(R.id.editNumber);

        viewLabel.setText(label);
        seekBar.setProgress(defaultInt);
        editNumber.setText(defaultInt);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                currentNumber = progress;
                editNumber.setText(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sendMessage(optionIndex, currentNumber);

            }
        });

        editNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                currentNumber = Integer.parseInt(editNumber.getText().toString());
                seekBar.setProgress(currentNumber);
                sendMessage(optionIndex, currentNumber);

            }
        });

        return view;
    }

    private void sendMessage(int optionIndex, int childIndex) {

        InputHolderFragment fragment = (InputHolderFragment) getParentFragment();

        //options är ju tom i det här fragmentet, hur ska vi lösa det?
        fragment.updateSyntax(optionIndex, options,childIndex);

    }

    
}
