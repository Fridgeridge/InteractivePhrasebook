package se.chalmers.phrasebook.gui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;

public class PhraseListActivity extends AppCompatActivity {

    private Model model;
    private ArrayList<String> phrases;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();

        setContentView(R.layout.activity_phrase_list);
        initializeList(model.getCurrentPhrasebook());

        context = this;

    }


    //placeholder function
    protected void initializeList(String phrasebookID) {

        ArrayAdapter<CharSequence> phraseAdapter = ArrayAdapter.createFromResource(this, R.array.phrases,
                android.R.layout.simple_list_item_1);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(phraseAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, TranslatorActivity.class);
                startActivity(intent);
            }
        });
    }

    //correct function
    private void initListView(){
 //       phrases = getPhrases(); //ArrayList with phrases needed

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, phrases);

        ListView phraseListView = (ListView) findViewById(R.id.listView);
        phraseListView.setAdapter(adapter);
    }

}
