package se.chalmers.phrasebook.gui;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import se.chalmers.phrasebook.R;

public class PhraseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_list);
        initializeList("hue");
    }

    //placeholder function
    protected void initializeList(String phrasebookID){

        Resources res = getResources();
        String[] names = res.getStringArray(R.array.placeholder_phrases1);
        ArrayAdapter<CharSequence> phraseAdapter = ArrayAdapter.createFromResource(this, R.array.placeholder_phrases1,
                android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(phraseAdapter);
    }
}
