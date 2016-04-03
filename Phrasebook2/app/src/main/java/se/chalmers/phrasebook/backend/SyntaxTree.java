package se.chalmers.phrasebook.backend;


import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Björn on 2016-02-26.
 */
public class SyntaxTree {
    private SyntaxNode root;
    private ArrayList<LinkedHashMap> options = new ArrayList<>();

    public SyntaxTree(SyntaxNode root) {
        this.root = root;
        initializeOptions(this.root);
    }

    public ArrayList<LinkedHashMap> getOptions() {
        return options;
    }

    //creates an ArrayList och LinkedHashMaps, each representing
    //a currently available option to be customized.
    private void initializeOptions(SyntaxNode currentRoot) {
        if (currentRoot.isModular()) {
            LinkedHashMap<String, SyntaxNode> selection
                    = new LinkedHashMap<>();
            for (String s : currentRoot.getQuestions()) {
                selection.put(s, currentRoot);
                for (SyntaxNode n : currentRoot.getChildren()) {
                    selection.put(n.getDesc(), n);
                }

                if(!options.contains(selection)) {
                    options.add((LinkedHashMap<String, SyntaxNode>) selection.clone());
                    selection.clear();
                }
                initializeOptions(currentRoot.getSelectedChildren().
                        get(currentRoot.getQuestions().indexOf(s)));
            }
        }
        else if (currentRoot.getSelectedChildren() != null) {
            for (SyntaxNode n : currentRoot.getSelectedChildren()) {
                initializeOptions(n);
            }
        }
    }

    private void initializeOptions2(SyntaxNode currentRoot) {
        if (currentRoot.isModular()) {
            LinkedHashMap<String, SyntaxNode> selection
                    = new LinkedHashMap<>();
            for (String s : currentRoot.getQuestionToChildren().keySet()) {
                selection.put(s, currentRoot);
                for (SyntaxNode n : currentRoot.getQuestionToChildren().get(s)) {
                    selection.put(n.getDesc(), n);
                }

                if(!options.contains(selection)) {
                    options.add((LinkedHashMap<String, SyntaxNode>) selection.clone());
                    selection.clear();
                }
                initializeOptions(currentRoot.getSelectedChildren().
                        get(currentRoot.getQuestions().indexOf(s)));
            }
        }
        else if (currentRoot.getSelectedChildren() != null) {
            for (SyntaxNode n : currentRoot.getSelectedChildren()) {
                initializeOptions2(n);
            }
        }
    }

    /**
     * Replaces an old selectedChild with a new one.
     * The method returns true if it succesfully managed to replace a
     * selected child, otherwise it returns false.
     *
     * @param parent   the modular SyntaxNode containing the two children
     * @param newChild the child which replaces the old one
     * @return if the operations was succesful or not
     */
    public boolean setSelectedChild(SyntaxNode parent, String question, SyntaxNode newChild) {
        if (!parent.setSelectedChild(question, newChild)) {
            return false;
        }
        options.clear();
        this.initializeOptions(root);
        return true;
    }

    /**
     * Parses the selected children into a text syntax usable by the grammar to
     * generate a translation. Bulilds recursivly.
     *
     * @return The syntax usable by the GF-grammar to generate a translation
     */
    public String getSyntax() {
        return parseSentenceSyntax(getSentenceHead());
    }

    // Builds recursively from root node to parse syntax
    //the getSyntax() method acts as a wrapper
    private String parseSentenceSyntax(SyntaxNode node) {
        if (!node.hasChildren()) {
            return node.getData();
        } else {
            String syntax = node.getData();
            for (int i = 0; i < node.getSelectedChildren().size(); i++) {
                if (node.getSelectedChildren().get(i).getData().isEmpty()) {
                    syntax = syntax + parseSentenceSyntax(node.getSelectedChildren().get(i));
                } else {
                    syntax = syntax + "(" + parseSentenceSyntax(node.getSelectedChildren().get(i)) + ")";
                    if (node.getSelectedChildren().size() > 1) {
                        syntax = syntax + " ";
                    }
                }
            }
            System.out.println(syntax);
            return syntax;
        }
    }

    private SyntaxNode getSentenceHead() {
        if (root != null) {
            return root.getSelectedChildren().get(0);
        } else {
            return null;
        }
    }

    //Can propoably be moved to a helper-class
    //Does not work for all numbers, something krasimir wrote doesnt really add up
    //in the "pot2plus" category.
    private String nmbrToSyntax(int nmbr) {
        String syntax = "";
        if(nmbr < 1000000) {
            if (nmbr <=999) {
                syntax = "(pot2as3 " + subs1000(nmbr) + ")";
            } else if(nmbr % 1000 == 0) {
                syntax = "(pot3 " + subs1000(nmbr/1000) + ")";
            } else if(nmbr > 1000 && nmbr%1000 != 0) {
                syntax = "(pot3plus " + subs1000(nmbr/1000) + " " +
                        subs1000(nmbr%1000) + ")";
            }
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
            syntax = "(pot2plus n" + nmbr/100 + " " + subs100(nmbr%100) + ")";
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
        if(nmbr == 1) {
            syntax = "pot01";
        } else if (nmbr >= 2 && nmbr <= 9) {
            syntax = "(pot0 n" + nmbr + ")";
        }
        return syntax;
    }
}