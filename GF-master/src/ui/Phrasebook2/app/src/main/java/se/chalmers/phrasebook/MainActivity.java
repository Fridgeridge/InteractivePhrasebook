package se.chalmers.phrasebook;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.ExprProb;
import org.grammaticalframework.pgf.MorphoAnalysis;
import org.grammaticalframework.pgf.PGF;
import org.grammaticalframework.pgf.ParseError;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

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

    @Override
    public void onClick(View v) {
        EditText et = (EditText) findViewById(R.id.editText);
        if(et != null && et.getText()!=null && et.getText().length() > 0 ){
            String s = et.getText().toString();
            Log.d("G", s);
            Map<String,Concr> map = pgf.getLanguages();
            Log.d("G",map.toString());
            Concr concr = map.get("PhrasebookEng");
            Log.d("G", concr.getName());

            String output="";
            try {
                Iterator<ExprProb> iter = concr.parse("Chunk", s).iterator(); // try parse as chunk
                Expr expr = iter.next().getExpr();
                output = concr.linearize(expr);
            } catch (ParseError e) {                               	  // if this fails
                List<MorphoAnalysis> morphos = concr.lookupMorpho(s) ;  // lookup morphological analyses

                morphos.addAll(concr.lookupMorpho(s.toLowerCase())) ;  // including the analyses of the lower-cased word

                for (MorphoAnalysis ana : morphos) {
                    if (concr.hasLinearization(ana.getLemma())) {    // check that the word has linearization in target
                        output = concr.linearize(Expr.readExpr(ana.getLemma())) ;
                        break ;                                           // if yes, don't search any more
                    }
                }
            }

            Log.d("G",output);

        }
    }
}
