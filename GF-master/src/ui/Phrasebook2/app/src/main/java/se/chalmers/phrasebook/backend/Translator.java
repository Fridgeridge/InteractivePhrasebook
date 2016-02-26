package se.chalmers.phrasebook.backend;

import android.util.Log;

import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.PGF;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by David on 2016-02-25.
 */
public class Translator {

    private PGF pgf;


    public void translate(InputStream in) {

        String name = "Grammars/Phrasebook.pgf";
        Log.d("d", "Pre");
        pgf = PGF.readPGF(in);
        Expr e = Expr.readExpr("PQuestion (QProp (PropAction (ATired (Daughter YouFamFemale))))");
        Map<String, Concr> langs = pgf.getLanguages();
        Concr eng = langs.get("PhrasebookEng");
        String s = eng.linearize(e);


        System.out.println(s);

        System.out.println("Start cat:" + pgf.getStartCat() + "\n");


    }


}
