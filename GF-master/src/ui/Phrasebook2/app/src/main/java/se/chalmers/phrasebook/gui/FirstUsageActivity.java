package se.chalmers.phrasebook.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import org.grammaticalframework.pgf.PGF;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Langs;
import se.chalmers.phrasebook.backend.Model;

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
            languages = model.getLanguages();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            originSpinner.setAdapter(adapter);
            targetSpinner.setAdapter(adapter);

            Button startButton = (Button) findViewById(R.id.startButton);

            View.OnClickListener startListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    model.setOriginLanguage(Langs.getKey(originSpinner.getSelectedItem().toString()));
                    model.setTargetLanguage(Langs.getKey(targetSpinner.getSelectedItem().toString()));

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

}
