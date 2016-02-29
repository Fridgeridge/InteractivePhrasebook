package se.chalmers.phrasebook.gui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.PhraseListActivity;

/**
 * Created by David on 2016-02-17.
 */
public class PhrasebookButtonAdapter extends BaseAdapter {

    private Context context;
    private String[] phrasebookNames;

    public PhrasebookButtonAdapter(Context context, String[] names) {
        this.context = context;
        phrasebookNames = names;
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
        Button button;

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
                Intent intent = new Intent(context, PhraseListActivity.class);
                context.startActivity(intent);
            }
        });

        return button;
    }
}
