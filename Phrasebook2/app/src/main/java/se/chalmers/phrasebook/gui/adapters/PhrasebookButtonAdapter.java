package se.chalmers.phrasebook.gui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.FragmentCommunicator;

/**
 * Created by David on 2016-02-17.
 */
public class PhrasebookButtonAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> phrasebookNames;

    private Model model;
    private FragmentCommunicator mCallback;

    public PhrasebookButtonAdapter(Context context,FragmentCommunicator mCallback, ArrayList<String> phrasebooks) {
        this.context = context;
        phrasebookNames = phrasebooks;
        this.mCallback = mCallback;
        model = Model.getInstance();
    }



    @Override
    public int getCount() {
        return phrasebookNames.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Button button;

        if (convertView == null) {
            button = new Button(context);
        } else {
            button = (Button) convertView;
        }

        button.setMinimumHeight(250);
        button.setHeight(400);
        button.setWidth(100);
        button.setBackgroundResource(R.drawable.grid_phrasebook_button);
        button.setTextColor(Color.BLACK);
        button.setText(phrasebookNames.get(position));
        button.setId(position);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setCurrentPhrasebook(model.getPhrasebookByTitle((String)button.getText()));
                sendMessage("Default");
            }
        });

        return button;
    }

    private void sendMessage(String phrasebook){
        mCallback.setPhraseListFragment(phrasebook);
    }

}
