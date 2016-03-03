package se.chalmers.phrasebook.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.grammaticalframework.pgf.PGF;
import org.w3c.dom.Document;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Language;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.SyntaxTree;
import se.chalmers.phrasebook.backend.XMLParser;

public class FirstUsageActivity extends AppCompatActivity {
    private PGF pgf;

    private List<String> languages;

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new Model();

        setContentView(R.layout.activity_first_usage);

        final Spinner originSpinner = (Spinner) findViewById(R.id.origin_spinner);
        final Spinner targetSpinner = (Spinner) findViewById(R.id.target_spinner);

        languages = new ArrayList<String>();
        initLanguages();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        originSpinner.setAdapter(adapter);
        targetSpinner.setAdapter(adapter);

        Button startButton = (Button) findViewById(R.id.startButton);

        View.OnClickListener startListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //model.setOrginLanguage();
                //model.setTargetLanguage();

                System.out.println("Origin language: " + originSpinner.getSelectedItem().toString() +
                        "Target language: " + targetSpinner.getSelectedItem().toString());

            }
        };

        startButton.setOnClickListener(startListener);




//        try {
//            InputStream is = getAssets().open("Phrases/test.xml");
//            XMLParser parser = new XMLParser(is);
//            Document doc = parser.acquireDocument();
//            SyntaxTree tree = parser.parseToSentence(doc,parser.jumpToChild(doc,"sentence"));
//        } catch (IOException es) {
//            es.printStackTrace();
//        }


    }

    //Test class to develop buttons
    public void sendMessage(View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }


    //Generate strings for spinners
    private void initLanguages(){
       // List temp = model.getLanguages();
        List<String> strings = new ArrayList<>(5);

        for(int i = 0; i < 5; i++){
            //languages.add(temp.get(i).getClass().getName());
            languages.add(i, "Hej" + i);
        }

    }
}
