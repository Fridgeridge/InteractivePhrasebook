package se.chalmers.phrasebook.gui.adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.activities.NavigationActivity;
import se.chalmers.phrasebook.gui.activities.PhraseListActivity;
import se.chalmers.phrasebook.gui.fragments.DefaultPhrasebooksFragment;
import se.chalmers.phrasebook.gui.fragments.PhraseListFragment;

/**
 * Created by David on 2016-02-17.
 */
public class PhrasebookButtonAdapter extends BaseAdapter {

    private Context context;
    private String[] phrasebookNames;

    private Model model;
    private NavigationActivity na;

    public PhrasebookButtonAdapter(Context context, String[] names) {
        this.context = context;

        //Ska hämtas nånstans ifrån istället för en statisk lista i xml
        phrasebookNames = names;

        model = Model.getInstance();
        na = new NavigationActivity();

    }


    @Override
    public int getCount() {
        return phrasebookNames.length;
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
        final View clickview = convertView;

        button.setMinimumHeight(250);
//        button.setHeight(100);
        button.setWidth(100);
        button.setBackgroundResource(R.drawable.grid_phrasebook_button);
        button.setTextColor(Color.BLACK);
        button.setText(phrasebookNames[position]);
        button.setId(position);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model.setCurrentPhrasebook((String) button.getText());

                sendMessage((String) button.getText());

            }
        });

        return button;
    }

    private void sendMessage(String phrasebook){

        System.out.println("sending");

        Intent intent = new Intent();
        intent.setAction("phrasebook_event");
        // add data
        intent.putExtra("message", "data");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}
