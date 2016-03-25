package se.chalmers.phrasebook.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.Collections;
import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Langs;
import se.chalmers.phrasebook.backend.Model;


public class FirstUsageActivity extends Activity {

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();

        getActionBar().hide();

        setContentView(R.layout.activity_first_usage);

        final Spinner originSpinner = (Spinner) findViewById(R.id.origin_spinner);
        final Spinner targetSpinner = (Spinner) findViewById(R.id.target_spinner);

        ArrayList al = Langs.getLanguages();
        Collections.sort(al);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, al);
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

    }

    //Starts the Home activity
    public void startApplication() {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }


}
