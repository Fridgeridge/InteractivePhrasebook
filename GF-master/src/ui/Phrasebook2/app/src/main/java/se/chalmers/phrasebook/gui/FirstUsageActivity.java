package se.chalmers.phrasebook.gui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import se.chalmers.phrasebook.backend.Langs;
import se.chalmers.phrasebook.backend.Language;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.SyntaxTree;
import se.chalmers.phrasebook.backend.XMLParser;

public class FirstUsageActivity extends AppCompatActivity {
    private PGF pgf;

    private List<String> languages;

    private Model model;
    private Langs langs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            model = Model.getInstance();

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

                    //Sätter bara language strängen nu, vet inte hur man ska hämta nyckeln
                    model.setOrginLanguage(originSpinner.getSelectedItem().toString());
                    model.setTargetLanguage(targetSpinner.getSelectedItem().toString());

                    startApplication();

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

    //Starts the Home activity
    public void startApplication() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }


    //Generate strings for spinners
    private void initLanguages(){

        for(Langs l: Langs.values()){
            languages.add(l.getLang());
        }

    }
}
