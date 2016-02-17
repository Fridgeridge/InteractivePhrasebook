package se.chalmers.phrasebook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PhraseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phrase_list);

        ArrayAdapter<CharSequence> phraseAdapter = ArrayAdapter.createFromResource(this, R.array.placeholder_phrases,
                android.R.layout.simple_list_item_1);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(phraseAdapter);
    }
}
