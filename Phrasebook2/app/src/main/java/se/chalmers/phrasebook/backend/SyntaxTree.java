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
        if(currentRoot.isModular()) {
            LinkedHashMap<String, SyntaxNode> selection
                    = new LinkedHashMap<>();
            for(SyntaxNodeList l : currentRoot.getSyntaxNodes()) {
                selection.put(l.getQuestion(), currentRoot);
                for(SyntaxNode n : l.getChildren()) {
                    System.out.println(n.getDesc() + " : " + n.getData());
                    selection.put(n.getDesc(), n);
                }

                if(!options.contains(selection)) {
                    options.add((LinkedHashMap<String, SyntaxNode>) selection.clone());
                    selection.clear();
                }
                initializeOptions(l.getSelectedChild());
            }
        }
        else if (currentRoot.getSyntaxNodes() != null && currentRoot.getSyntaxNodes().size() > 0) {
            for (SyntaxNodeList n : currentRoot.getSyntaxNodes()) {
                initializeOptions(n.getSelectedChild());
            }
        }
    }

    /**
     * Replaces an old selectedChild with a new one.
     * The method returns true if it succesfully managed to replace a
     * selected child, otherwise it returns false.
     *
     * @param parent   the modular SyntaxNode containing the two children
     * @param question the question to be answered
     * @param newChild the child which replaces the old one
     * @return if the operations was succesful or not
     */
    //TODO REALLY UGLY SOLUTION, TRY TO FIX IT WITHOUT 'instanceof' FOR NUMERALSYNTAXNODE
    public boolean setSelectedChild(SyntaxNode parent, int listIndex, String newChild, String question) {
        if(parent.getSyntaxNodes().get(0).getChildren().get(0) instanceof NumeralSyntaxNode) {
            ((NumeralSyntaxNode)parent).setSelectedChild(newChild);
            options.clear();
            this.initializeOptions(root);
            return true;
        }
        for(int i = 0; i < parent.getSyntaxNodes().size(); i++) {
            if(parent.getSyntaxNodes().get(i).getQuestion().equals(question)) {
                parent.setSelectedChild(i, (SyntaxNode)options.get(listIndex).get(newChild));
                options.clear();
                this.initializeOptions(root);
                return true;
            }
        }
        return false;
    }

    /**
     * Parses the selected children into a text syntax usable by the grammar to
     * generate a translation. Builds recursivly.
     *
     * @return The syntax usable by the GF-grammar to generate a translation
     */
    public String getSyntax() {
        return parseSentenceSyntax(getSentenceHead());
    }

    // Builds recursively from root node to parse syntax
    //the getSyntax() method acts as a wrapper
    private String parseSentenceSyntax(SyntaxNode node) {

        if (node.getSyntaxNodes().size() < 1) {
            return node.getData();
        } else {
            String syntax = node.getData();
            for (int i = 0; i < node.getSyntaxNodes().size(); i++) {
                if (node.getSyntaxNodes().get(i).getSelectedChild().getData().isEmpty()) {
                    syntax = syntax + parseSentenceSyntax(node.getSyntaxNodes().get(i).getSelectedChild());
                } else {
                    syntax = syntax + "(" + parseSentenceSyntax(node.getSyntaxNodes().get(i).getSelectedChild()) + ")";
                    if (node.getSyntaxNodes().size() > 1) {
                        syntax = syntax + " ";
                    }
                }
            }
            System.out.println(syntax);
            return syntax;
        }
    }

    private SyntaxNode getSentenceHead() {
        if(root.getSyntaxNodes().get(0)!=null)
            return root.getSyntaxNodes().get(0).getSelectedChild();//TODO Might cause bugs
        return null;
    }
}