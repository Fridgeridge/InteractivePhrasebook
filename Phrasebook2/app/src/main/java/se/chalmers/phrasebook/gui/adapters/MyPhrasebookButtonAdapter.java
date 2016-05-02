package se.chalmers.phrasebook.gui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.FragmentCommunicator;

/**
 * Created by David on 2016-05-02.
 */
public class MyPhrasebookButtonAdapter extends PhrasebookButtonAdapter {

    FragmentCommunicator mCallback;

    public MyPhrasebookButtonAdapter(Context context, FragmentCommunicator mCallback, ArrayList<String> phrasebooks) {
        super(context, mCallback, phrasebooks);
        this.mCallback = mCallback;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Button button = (Button) super.getView(position, convertView, parent);
        final Model model = Model.getInstance();
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mCallback.removePhrasebook((String) button.getText());
                return false;
            }
        });

        return button;
    }


}
