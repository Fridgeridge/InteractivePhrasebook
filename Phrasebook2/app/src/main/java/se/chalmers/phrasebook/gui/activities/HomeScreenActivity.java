package se.chalmers.phrasebook.gui.activities;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toolbar;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.adapters.PhrasebookButtonAdapter;

public class HomeScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

     //   getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4db6ac")));

        Resources res = getResources();
        String[] buttonNames = res.getStringArray(R.array.my_phrasebooks);
        GridView gridView = (GridView) findViewById(R.id.standard_gridView);
        gridView.setAdapter(new PhrasebookButtonAdapter(this, buttonNames));

        GridView gridView1 = (GridView) findViewById(R.id.custom_gridView);
        gridView1.setAdapter(new PhrasebookButtonAdapter(this, buttonNames));

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
