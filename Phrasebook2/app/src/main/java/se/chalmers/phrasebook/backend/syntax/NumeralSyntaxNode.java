package se.chalmers.phrasebook.backend.syntax;

import java.io.IOException;

import se.chalmers.phrasebook.backend.syntax.SyntaxNode;

/**
 * Created by Björn on 2016-04-04.
 */
public class NumeralSyntaxNode extends SyntaxNode {
    
    private int number = 1;

    public NumeralSyntaxNode() {
        super("NNumeral");
    }

    @Override
    public boolean isModular() {
        return true;
    }

    public String getData() {
        try {
            return nmbrToSyntax(number);
        }catch(IOException e) {
            //Returns the syntax for "1" in case of erroneous input.
            return "(NNumeral(num (pot2as3 (pot1as2 (pot0as1 pot01)))))";
        }
    }

    public String getDesc() {
        return Integer.toString(number);
    }

    public void setDesc(String number) {
        this.number = Integer.parseInt(number);
    }

    public void setSelectedChild(String updated) {
        setDesc(updated);
    }

    private String nmbrToSyntax(int nmbr) throws IOException {
        String syntax = "";
        if(nmbr < 1000000 && nmbr > 0) {
            if (nmbr <=999) {
                syntax = "(NNumeral(num(pot2as3 " + subs1000(nmbr) + ")))";
            } else if(nmbr % 1000 == 0) {
                syntax = "(NNumeral(num(pot3 " + subs1000(nmbr/1000) + ")))";
            } else if(nmbr > 1000 && nmbr%1000 != 0) {
                syntax = "(NNumeral(num(pot3plus " + subs1000(nmbr/1000) + " " +
                        subs1000(nmbr%1000) + ")))";
            }
        } else {
            throw new IOException("Input must be between 1 and 999999");
        }
        return syntax;
    }

    private String subs1000(int nmbr) {
        String syntax = "";
        if(nmbr < 100) {
            syntax = "(pot1as2 " + subs100(nmbr) + ")";
        } else if(nmbr % 100 == 0) {
            syntax = "(pot2 " + subs100(nmbr/100) + ")";
        } else if(nmbr > 100 && nmbr%100 != 0) {
            syntax = "(pot2plus " + subs10(nmbr/100) + " " + subs100(nmbr%100) + ")";
        }
        return syntax;
    }

    private String subs100(int nmbr) {
        String syntax = "";
        if(nmbr < 10) {
            syntax = "(pot0as1 " + subs10(nmbr) + ")";
        } else if(nmbr == 10 || nmbr == 11) {
            syntax = "(pot0as1 " + "pot" + nmbr + ")";
        } else if(nmbr >= 12 && nmbr <= 19) {
            syntax = "(pot1to19 n" + nmbr%10 + ")";
        } else if(nmbr >= 20 && nmbr%10 == 0) {
            syntax = "(pot1 " + subs10(nmbr/10) + ")";
        } else if(nmbr%10 != 0) {
            syntax = "(pot1plus n" + nmbr/10 + subs10(nmbr%10) + ")";
        }
        return syntax;
    }

    private String subs10(int nmbr) {
        String syntax = "";
        if (nmbr == 1) {
            syntax = "pot01";
        } else if (nmbr >= 2 && nmbr <= 9) {
            syntax = "(pot0 n" + nmbr + ")";
        }
        return syntax;
    }
}
