package se.chalmers.phrasebook.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.grammaticalframework.pgf.PGF;

import java.io.IOException;
import java.io.InputStream;

import se.chalmers.phrasebook.R;
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

//        Context mContext = getApplication().getApplicationContext();
//        InputStream in;
//        String name = "Grammars/Phrasebook.pgf";
//        try {
//            Log.d("d", "Pre");
//            in = mContext.getAssets().open(name);
//            pgf = PGF.readPGF(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Expr e = Expr.readExpr("PQuestion (QWhatName (Child IMale))");
//        Map<String,Concr> langs = pgf.getLanguages();
//        Concr eng = langs.get("PhrasebookEng");
//        String s = eng.linearize(e);
//
//
//        System.out.println(s);
//
//        System.out.println("Start cat:" + pgf.getStartCat() + "\n");


        XMLParser parser = new XMLParser();
        try {
            InputStream is = getAssets().open("Phrases/test.xml");
            System.out.println(parser.getAllSentencesTitles(parser.acquireDocument(is))[1]);
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
