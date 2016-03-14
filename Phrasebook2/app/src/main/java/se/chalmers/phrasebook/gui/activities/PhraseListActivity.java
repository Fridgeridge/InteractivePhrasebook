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
import se.chalmers.phrasebook.backend.XMLParser;

/**
 * Activity where the user can choose what phrase they want to edit and see translation of.
 */
public class PhraseListActivity extends Activity {

    private Model model;
    private XMLParser parser;
    private ArrayList<String> phrases;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();

        try {
            InputStream is = getAssets().open("Phrases/sentences.xml");
            parser = new XMLParser(is);
        } catch (IOException es) {
            es.printStackTrace();
        }

        setContentView(R.layout.activity_phrase_list);
        initListView();

        context = this;

    }

    /**
     * Initializes the list view by dynamically add phrases from the XML file.
     */
    private void initListView(){
        phrases = parser.getAllSentencesTitles();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, phrases);

        ListView phraseListView = (ListView) findViewById(R.id.listView);
        phraseListView.setAdapter(adapter);

        phraseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                model.setCurrentPhrase((String) parent.getItemAtPosition(position));

                Intent intent = new Intent(context, TranslatorActivity.class);
                startActivity(intent);
            }
        });
    }

}