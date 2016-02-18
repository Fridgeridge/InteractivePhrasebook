package se.chalmers.phrasebook.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import static se.chalmers.phrasebook.R.drawable.grid_phrasebook_button;

/**
 * Created by David on 2016-02-17.
 */
public class PhrasebookButtonAdapter extends BaseAdapter {

    private Context context;
    private String[] phrasebookNames;

    public PhrasebookButtonAdapter(Context context, String[] names){
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
        if(convertView == null) {
            button = new Button(context);
        }else {
            button = (Button) convertView;
        }
        button.setBackgroundResource(grid_phrasebook_button);
        button.setText(phrasebookNames[position]);
        button.setId(position);

        return button;
    }
}
