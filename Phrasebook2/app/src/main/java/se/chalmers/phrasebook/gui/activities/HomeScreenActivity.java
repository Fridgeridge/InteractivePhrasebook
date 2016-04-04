package se.chalmers.phrasebook.gui.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.GridView;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.adapters.PhrasebookButtonAdapter;

public class HomeScreenActivity extends NavigationActivity {

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
