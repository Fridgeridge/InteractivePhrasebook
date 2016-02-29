package se.chalmers.phrasebook.backend;

import org.grammaticalframework.pgf.Expr;

/**
 * Created by David on 2016-02-19.
 */
public class GFParser {


    public static Expr parseToAST(SyntaxSentence sentence) throws Exception {
        if (sentence == null) {
            throw new IllegalArgumentException("Sentence cannot be parsed");
        }
        return Expr.readExpr("S");
    }


}
