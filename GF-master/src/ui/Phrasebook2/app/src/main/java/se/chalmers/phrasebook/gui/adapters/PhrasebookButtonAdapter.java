package se.chalmers.phrasebook.gui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.PhraseListActivity;

/**
 * Created by David on 2016-02-17.
 */
public class PhrasebookButtonAdapter extends BaseAdapter {

    private Context context;
    private String[] phrasebookNames;

    private Model model;

    public PhrasebookButtonAdapter(Context context, String[] names) {
        this.context = context;

        //Ska hämtas nånstans ifrån istället för en statisk lista i xml
        phrasebookNames = names;

        model = Model.getInstance();
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

        //Kan det bli knas?
        final Button button;

        if (convertView == null) {
            button = new Button(context);
        } else {
            button = (Button) convertView;
        }
        final View clickview = convertView;

        button.setMinimumHeight(150);
        button.setBackgroundColor(context.getResources().getColor(R.color.primary_color));
        button.setTextColor(Color.WHITE);
        button.setText(phrasebookNames[position]);
        button.setId(position);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                model.setCurrentPhrasebook((String)button.getText());

                Intent intent = new Intent(context, PhraseListActivity.class);
                context.startActivity(intent);
            }
        });

        return button;
    }

}
