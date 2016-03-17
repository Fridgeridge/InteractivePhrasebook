package se.chalmers.phrasebook.backend;


import java.util.ArrayList;

/**
 * Created by Björn on 2016-02-26.
 */
public class SyntaxTree {
    private SyntaxNode root;
    private String sentenceDescription = "";
    private ArrayList<ArrayList> options = new ArrayList<>();

    public SyntaxTree(SyntaxNode root) {
        this.root = root;
        initializeOptions(this.root);
    }

    public ArrayList<ArrayList> getOptions() {
        return options;
    }

    private void initializeOptions(SyntaxNode currentRoot) {
        if(currentRoot.isModular()) {

                if (currentRoot.getParent().isModular()) {
                    if (currentRoot.isSelected()) {
                        ArrayList<String> selection = new ArrayList<>();
                        selection.add(currentRoot.getDesc());
                        for (SyntaxNode n : currentRoot.getChildren()) {
                            selection.add(n.getDesc());
                        }
                        options.add(selection);
                    }
                } else {
                    ArrayList<String> selection = new ArrayList<>();
                    selection.add(currentRoot.getDesc());
                    for (SyntaxNode n : currentRoot.getChildren()) {
                        selection.add(n.getDesc());
                    }
                    options.add(selection);
                }

        }
        for(SyntaxNode n: currentRoot.getChildren()) {
            initializeOptions(n);
        }
    }

/*        if(currentRoot.isModular()) {
            ArrayList<String> selection = new ArrayList<>();
            selection.add(currentRoot.getDesc());
            for(SyntaxNode n: currentRoot.getChildren()) {
                selection.add(n.getDesc());
            }
            options.add(selection);
        }
        for(SyntaxNode n: currentRoot.getChildren()) {
            initializeOptions(n);
        }
    }*/
    /**
     * Checks all the trees nodes to find modular nodes.
     *
     * @return the number of modular nodes
     *
     **/
    public int numberOfModularNodes() {
        return nmbrModNode(0, root);
    }

    private int nmbrModNode(int count, SyntaxNode currentRoot) {
        if(currentRoot.isModular()) {
            count++;
        }
        for(SyntaxNode n: currentRoot.getChildren()) {
            count = nmbrModNode(count, n);
        }
        return count;
    }

    /**
     * Build recursively from root node to parse syntax
     *
     * @param node The current node
     * @return A String representing the syntax of the sentence
     *
     **/
    public String parseSentenceSyntax(SyntaxNode node) {
        if(!node.hasChildren()) {
            return node.getData();
        } else {
            String syntax = node.getData() + "(";
            for(int i = 0; i < node.getSelectedChild().length; i++) {
                syntax = syntax + parseSentenceSyntax(node.getSelectedChild()[i]);
                if(node.getSelectedChild().length > 1) {
                    syntax = syntax + " ";
                }
            }
            return syntax + ")";
        }
    }

    public SyntaxNode getSentenceHead() {
        if (root != null) {
            return root.getSelectedChild()[0];
        }else {
            return null;
        }
    }

    public String getSyntax(){
        return parseSentenceSyntax(getSentenceHead());
    }

    public String getSentenceDescription() {
        return sentenceDescription;
    }

    public void setSentenceDescription(String newDiscription) {
        sentenceDescription = newDiscription;
    }

}