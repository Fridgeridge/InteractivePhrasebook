package se.chalmers.phrasebook.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.grammaticalframework.pgf.PGF;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.SyntaxTree;
import se.chalmers.phrasebook.backend.XMLParser;

public class FirstUsageActivity extends AppCompatActivity {
    private PGF pgf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_usage);

        Spinner originSpinner = (Spinner) findViewById(R.id.origin_spinner);
        Spinner targetSpinner = (Spinner) findViewById(R.id.target_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        originSpinner.setAdapter(adapter);
        targetSpinner.setAdapter(adapter);


        XMLParser parser = new XMLParser();
        try {
            InputStream is = getAssets().open("Phrases/test.xml");
            Document doc = parser.acquireDocument(is);
            SyntaxTree tree = parser.parseToSentence(doc,parser.jumpToChild(doc,"sentence"));
        } catch (IOException es) {
            es.printStackTrace();
        }


    }

    //Test class to develop buttons
    public void sendMessage(View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
