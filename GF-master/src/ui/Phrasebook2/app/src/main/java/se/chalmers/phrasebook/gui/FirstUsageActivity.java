package se.chalmers.phrasebook.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.PGF;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.XMLParser;

public class FirstUsageActivity extends AppCompatActivity {
    private PGF pgf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_usage);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        Context mContext = getApplication().getApplicationContext();
        InputStream in;
        String name = "Grammars/Phrasebook.pgf";
        PGF pgf;
        try {
            Log.d("d", "Pre");
            in = mContext.getAssets().open(name);
            pgf = PGF.readPGF(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Expr e = Expr.readExpr("PQuestion (QProp (PropAction (ATired (Daughter YouFamFemale))))");
        Expr out = pgf.compute(e);
        List<String> ar = pgf.getFunctions();
        String[] a = (String[]) ar.toArray();

        System.out.println("Start cat:" + pgf.getStartCat() + "\n");
        for(String s: a){
            System.out.println(s);
        }



        XMLParser parser = new XMLParser();
        try {
            InputStream is = getAssets().open("Phrases/test.xml");
            Log.d("Awesome",parser.parseDOM(is));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
     //Test class to develop buttons
    public void sendMessage(View view) {
        Intent intent = new Intent(this, HomeScreenActivity.class);
        startActivity(intent);
    }
}
