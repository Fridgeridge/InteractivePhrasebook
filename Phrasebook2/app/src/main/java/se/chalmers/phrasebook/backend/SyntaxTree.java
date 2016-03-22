package se.chalmers.phrasebook.backend;


import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedHashMap;

/**
 * Created by Björn on 2016-02-26.
 */
public class SyntaxTree {
    private SyntaxNode root;
    private String sentenceDescription = "";
    private ArrayList<LinkedHashMap> options = new ArrayList<>();

    public SyntaxTree(SyntaxNode root) {
        this.root = root;
        initializeOptions(this.root);
    }

    public ArrayList<LinkedHashMap> getOptions() {
        return options;
    }

    private void initializeOptions(SyntaxNode currentRoot) {
        options.clear();
        if(currentRoot.isModular()) {
            LinkedHashMap<String, SyntaxNode> selection
                    = new LinkedHashMap<>();

            selection.put(currentRoot.getDesc(), currentRoot);
            for (SyntaxNode n : currentRoot.getChildren()) {
                selection.put(n.getDesc(), n);
            }
            options.add(selection);
        }
        if(currentRoot.getSelectedChild() != null) {
            for(SyntaxNode n: currentRoot.getSelectedChild()) {
                initializeOptions(n);
            }
        }
    }

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

    public void setSelectedChild(SyntaxNode parent, SyntaxNode oldChild, SyntaxNode newChild) {
        try {
            parent.setSelectedChild(oldChild, newChild);
            System.out.println("det funka");
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("det blev fel");
        }
        this.initializeOptions(root);
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