package se.chalmers.phrasebook.gui.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.FragmentCommunicator;

/**
 * Created by David on 2016-05-02.
 */
public class MyPhrasebookButtonAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<String> phrasebookNames;

    private Model model;
    private FragmentCommunicator mCallback;

    public MyPhrasebookButtonAdapter(Context context, FragmentCommunicator mCallback, ArrayList<String> phrasebooks) {
        this.context = context;
        phrasebookNames = phrasebooks;
        this.mCallback = mCallback;
        model = Model.getInstance();    }

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
                String phrasebook = (String)button.getText();
                model.setCurrentPhrasebook(model.getPhrasebookByTitle(phrasebook));
                mCallback.setPhraseListFragment(phrasebook);
            }
        });


      /*  button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(!button.getText().equals("Favorites")) {
                    mCallback.removePhrasebook((String) button.getText());
                    return false;
                }
                return false;
            }
        });*/

        button.setOnTouchListener(new View.OnTouchListener() {
            long then = 0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    then = (Long) System.currentTimeMillis();
                    while(System.currentTimeMillis() - then < 2000) {

                    }
                }
                else if(event.getAction() == MotionEvent.ACTION_UP){
                    if(((Long) System.currentTimeMillis() - then) > 2000 && !button.getText().equals("Favorites")){
                        mCallback.removePhrasebook((String) button.getText());
                        return false;
                    }
                }
                return false;

            }

        });

        return button;
    }

}
