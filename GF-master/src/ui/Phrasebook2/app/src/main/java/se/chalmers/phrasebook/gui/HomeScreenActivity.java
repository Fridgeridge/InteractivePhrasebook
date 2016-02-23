package se.chalmers.phrasebook.gui;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.adapters.PhrasebookButtonAdapter;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Resources res = getResources();
        String[] buttonNames = res.getStringArray(R.array.button_names);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new PhrasebookButtonAdapter(this, buttonNames));

    }


}
