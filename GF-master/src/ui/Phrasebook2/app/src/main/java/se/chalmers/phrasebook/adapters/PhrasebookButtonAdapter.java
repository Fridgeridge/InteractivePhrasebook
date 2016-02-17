package se.chalmers.phrasebook.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import se.chalmers.phrasebook.R;

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
            button.findViewById(R.id.phrasebookButton);
            //button.setLayoutParams(new GridView.LayoutParams(100, 55));
            //button.setPadding(10, 10, 10, 10);
        }else {
            button = (Button) convertView;
        }
        button.setText(phrasebookNames[position]);
        button.setBackgroundColor(context.getResources().getColor(R.color.main_color));
        button.setId(position);

        button.setLayoutParams((new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)));

        return button;
    }
}
