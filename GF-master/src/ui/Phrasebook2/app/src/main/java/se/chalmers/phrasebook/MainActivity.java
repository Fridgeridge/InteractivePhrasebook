package se.chalmers.phrasebook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.grammaticalframework.pgf.PGF;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends ActionBarActivity{

    private PGF pgf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Context mContext = getApplication().getApplicationContext();
        InputStream in;
        String name = "Grammars/Phrasebook.pgf";
        try {
            Log.d("d", "Pre");
            in = mContext.getAssets().open(name);
            pgf = PGF.readPGF(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, PhraseListActivity.class);
        startActivity(intent);
    }

    }

