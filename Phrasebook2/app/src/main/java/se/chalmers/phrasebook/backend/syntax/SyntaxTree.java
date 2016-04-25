package se.chalmers.phrasebook.backend.syntax;


import java.util.ArrayList;


/**
 * Created by Björn on 2016-02-26.
 */
public class SyntaxTree {

    private String id;
    private SyntaxNode root;
    private ArrayList<SyntaxNodeList> options = new ArrayList<>();

    public SyntaxTree(SyntaxNode root) {
        this.root = root;
        initializeOptions(this.root);
    }

    public ArrayList<SyntaxNodeList> getOptions() {
        return options;
    }

    //creates an ArrayList och LinkedHashMaps, each representing
    //a currently available option to be customized.
    private void initializeOptions(SyntaxNode currentRoot) {
        if (currentRoot == null) return;
        if (currentRoot.isModular()) {
            for (SyntaxNodeList l : currentRoot.getSyntaxNodes()) {
                if (l.getQuestion() != null && !options.contains(l)) {
                    options.add(l);
                }
                initializeOptions(l.getSelectedChild());
            }
        } else if (currentRoot.getSyntaxNodes() != null && currentRoot.getSyntaxNodes().size() > 0) {
            for (SyntaxNodeList n : currentRoot.getSyntaxNodes()) {
                initializeOptions(n.getSelectedChild());
            }
        }
    }

    public String getId() {
        return id;
    }

    public boolean setSelectedChild(int optionIndex, int childIndex) {
        boolean status = false;

        if (options.get(optionIndex).getSelectedChild() instanceof NumeralSyntaxNode) {
            options.get(optionIndex).getSelectedChild().setSelectedChild(childIndex, null);
            status = true;
        } else if (options.get(optionIndex) != null) {
            if (options.get(optionIndex).getChildren().get(childIndex) != null)
                status = options.get(optionIndex).setSelectedChild(options.get(optionIndex).getChildren().get(childIndex));
        }
        return status;
    }


    public void setSelectedChild(int optionIndex, SyntaxNodeList snl, int childIndex) {
        if (snl != null) {
            SyntaxNodeList nodeList = options.get(optionIndex);
            setRecursiveSelectedChild(nodeList, snl, childIndex);
        } else {
            setSelectedChild(optionIndex, childIndex);
        }
    }


    private void setRecursiveSelectedChild(SyntaxNodeList nodeList, SyntaxNodeList optionTarget, int childIndex) {

        if (nodeList.equals(optionTarget)) {
            setSelectedChild(nodeList, nodeList.getChildren().get(childIndex));
        } else {
            for (SyntaxNodeList list : nodeList.getSelectedChild().getSyntaxNodes())
                setRecursiveSelectedChild(list, optionTarget, childIndex);
        }

    }


    public boolean setSelectedChild(SyntaxNodeList l, SyntaxNode s) {
        return l.setSelectedChild(s);
    }

//    /**
//     * Replaces an old selectedChild with a new one.
//     * The method returns true if it succesfully managed to replace a
//     * selected child, otherwise it returns false.
//     *
//     * @param parent   the modular SyntaxNode containing the two children
//     * @param question the question to be answered
//     * @param newChild the child which replaces the old one
//     * @return if the operations was succesful or not
//     */
//    //TODO REALLY UGLY SOLUTION, TRY TO FIX IT WITHOUT 'instanceof' FOR NUMERALSYNTAXNODE
//    public boolean setSelectedChild(SyntaxNode parent, int listIndex, String newChild, String question) {
//        if(parent.getSyntaxNodes().get(0).getChildren().get(0) instanceof NumeralSyntaxNode) {
//            ((NumeralSyntaxNode)parent).setSelectedChild(newChild);
//            options.clear();
//            this.initializeOptions(root);
//            return true;
//        }
//        for(int i = 0; i < parent.getSyntaxNodes().size(); i++) {
//            if(parent.getSyntaxNodes().get(i).getQuestion().equals(question)) {
//                parent.setSelectedChild(i, (SyntaxNode)options.get(listIndex).get(newChild));
//                options.clear();
//                this.initializeOptions(root);
//                return true;
//            }
//        }
//        return false;
//    }

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
                    syntax += parseSentenceSyntax(node.getSyntaxNodes().get(i).getSelectedChild());
                } else {
                    syntax = syntax + "(" + parseSentenceSyntax(node.getSyntaxNodes().get(i).getSelectedChild()) + ")";
                    if (node.getSyntaxNodes().size() > 1) {
                        syntax += " ";
                    }

                }
            }
            return syntax;
        }
    }

    private SyntaxNode getSentenceHead() {
        if (root.getSyntaxNodes().get(0) != null)
            return root.getSyntaxNodes().get(0).getSelectedChild();//TODO Might cause bugs
        return null;
    }

}