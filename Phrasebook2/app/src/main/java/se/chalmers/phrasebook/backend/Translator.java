package se.chalmers.phrasebook.backend;

import android.util.Log;

import org.grammaticalframework.pgf.Concr;
import org.grammaticalframework.pgf.Expr;
import org.grammaticalframework.pgf.PGF;
import org.grammaticalframework.pgf.PGFError;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * Created by David on 2016-02-25.
 */
public class Translator {

    private PGF pgf;
    private Concr originLanguage, targetLanguage;

    public Translator(InputStream grammar) throws IOException {
        pgf = PGF.readPGF(grammar);
        grammar.close();
    }

    public String translate(Concr lang, String abstractSyntax) {
        String s = "Error";
        try {
            Expr expr = Expr.readExpr(abstractSyntax);
            s = lang.linearize(expr);
        } catch (PGFError e) {
            Log.e("TranslationError", "Error while parsing XML syntax: " + abstractSyntax + "\n with Error: " + e.toString());
        }
            return s;
    }

    public String translateToOrigin(String abstractSyntax) {
        return translate(originLanguage, abstractSyntax);
    }

    public String translateToTarget(String abstractSyntax) {
        return translate(targetLanguage, abstractSyntax);
    }

    public boolean setOriginLanguage(String langID) {
        try {
            Concr origin = pgf.getLanguages().get(langID);
            originLanguage = origin;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    public boolean setTargetLanguage(String langID) {
        try {
            Concr target = pgf.getLanguages().get((langID));
            targetLanguage = target;
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }


    public String[] getLanguages() {
        Set<String> strings = pgf.getLanguages().keySet();

        return strings.toArray(new String[strings.size()]);
    }

}
