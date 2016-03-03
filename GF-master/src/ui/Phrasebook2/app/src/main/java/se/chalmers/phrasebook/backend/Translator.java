package se.chalmers.phrasebook.backend;

import android.util.Log;

import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.PGF;
import org.grammaticalframework.pgf.PGFError;

import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/**
 * Created by David on 2016-02-25.
 */
public class Translator {

    private PGF pgf;
    private Concr originLanguage, targetLanguage;

    //String name = "Grammars/Phrasebook.pgf";
    public Translator(InputStream grammar){
        pgf = PGF.readPGF(grammar);
    }

    public String translate(Concr lang, String abstractSyntax) throws PGFError {
        Expr e = Expr.readExpr(abstractSyntax);
        return lang.linearize(e);
    }


    public String[] getLanguages(){
        Set<String> strings = pgf.getLanguages().keySet();

        return strings.toArray(new String[strings.size()]);
    }

}
