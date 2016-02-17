package se.chalmers.phrasebook;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import se.chalmers.phrasebook.adapters.PhrasebookButtonAdapter;

public class HomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        String[] myStringArray = {"Simple phrases","Hospital phrases", "Restaurant phrases","LOOOOOOOOOOOOOOOOOO00000000000000000000000000000000000000000000000OOOOOOOOOOOOOOOONG Phrases"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myStringArray);

        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new PhrasebookButtonAdapter(this,myStringArray));


    }

}
