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

/**
 * Created by matilda on 24/03/16.
 */
public class LanguageActivity extends Activity {

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        model = Model.getInstance();

        //   getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4db6ac")));

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayList languages = Langs.getLanguages();
        Collections.sort(languages);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        Button button = (Button) findViewById(R.id.apply_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setOriginLanguage(Langs.getKey(spinner1.getSelectedItem().toString()));
                model.setTargetLanguage(Langs.getKey(spinner2.getSelectedItem().toString()));

                changeActivity();

            }
        });

    }

    private void changeActivity(){
        Intent intent = new Intent(this, TranslatorActivity.class);
        startActivity(intent);
        finish();
    }

}
