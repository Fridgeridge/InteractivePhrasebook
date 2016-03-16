package se.chalmers.phrasebook.gui.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.TestSentence;
import se.chalmers.phrasebook.backend.XMLParser;

/**
 * Activity where the user can choose what phrase they want to edit and see translation of.
 */
public class PhraseListActivity extends Activity {

    private Model model;
    private ArrayList<String> phrases;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();
        phrases = new ArrayList<String>();


        phrases.addAll(model.getSentences().values());

        setContentView(R.layout.activity_phrase_list);
        initListView();

        model.setTestSentence(new TestSentence(5));

        context = this;

    }

    /**
     * Initializes the list view by dynamically add phrases from the XML file.
     */
    private void initListView() {

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, phrases);

        final ListView phraseListView = (ListView) findViewById(R.id.listView);
        phraseListView.setAdapter(adapter);

        phraseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                model.setCurrentPhrase((String)phraseListView.getItemAtPosition(position));

                Intent intent = new Intent(context, TranslatorActivity.class);
                startActivity(intent);
            }
        });
    }

}
