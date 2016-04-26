package se.chalmers.phrasebook.gui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

import se.chalmers.phrasebook.App;
import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.activities.NavigationActivity;

/**
 * Created by David on 2016-02-17.
 */
public class PhrasebookButtonAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> phrasebookNames;

    private Model model;
    private NavigationActivity na;

    public PhrasebookButtonAdapter(Context context, ArrayList<String> phrasebooks) {
        this.context = context;

        phrasebookNames = new ArrayList<>();
        phrasebookNames = phrasebooks;
        model = Model.getInstance();
        na = new NavigationActivity();

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
        final View clickview = convertView;

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
                sendMessage("Tourism");
            }
        });

        return button;
    }

    private void sendMessage(String phrasebook){

        Intent intent = new Intent();
        intent.setAction("phrasebook_event");
        intent.putExtra("message", phrasebook);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

}
