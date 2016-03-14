package se.chalmers.phrasebook.gui.activities;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.GridView;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.adapters.PhrasebookButtonAdapter;

public class HomeScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Resources res = getResources();
        String[] buttonNames = res.getStringArray(R.array.phrasebooks_default);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new PhrasebookButtonAdapter(this, buttonNames));



    }


}
