package se.chalmers.phrasebook.backend;



/**
 * Created by Björn on 2016-02-26.
 */
public class SyntaxTree {
    private SyntaxNode root;
    private String sentenceDiscription = "";

    public SyntaxTree(String rootData) {
        root = new SyntaxNode(rootData);
    }

    public SyntaxTree(SyntaxNode root) {
        this.root = root;
    }

    public int numberOfModularNodes() {
        return nmbrModNode(0, root);
    }

    public int nmbrModNode(int count, SyntaxNode currentRoot) {
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

    //TODO Fix method
    public SyntaxNode findNode(SyntaxNode node, String syntax) {
        if (node == null) {
            return null;
        }
        if (node.getData().equals(syntax)) {
            return node;
        } else if (node.getChildren().isEmpty()) {
            return null;//Unsure
        } else {
            for (SyntaxNode n : node.getChildren()) {
                return findNode(n, syntax);
            }
        }

        return node;
    }
/*
    public void addChild(SyntaxNode node, SyntaxNode child) {
        if (node.getChildren().isEmpty())
            root.setSelectedChild(child);

        child.setParent(node);
        node.getChildren().add(child);
    }
*/
    public boolean removeChild(SyntaxNode node, SyntaxNode child) {
        boolean status = node.getChildren().remove(child);
        if (status && node.getChildren().isEmpty()) {
            node.setSelectedChild(null);
        }

        return status;
    }
/*
    public boolean setSelectedChild(SyntaxNode node, SyntaxNode child) {
        boolean status = node.getChildren().contains(child);
        if (status)
            node.setSelectedChild(child);
        return status;
    }*/

    public SyntaxNode getSentenceHead() {
        if (root != null) {
            return root.getSelectedChild()[1];
        }else {
            return null;
        }
    }

    public String getSentenceDiscription() {
        return sentenceDiscription;
    }

    public void setSentenceDiscription(String newDiscription) {
        sentenceDiscription = newDiscription;
    }

}