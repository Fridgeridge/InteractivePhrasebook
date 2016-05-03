package se.chalmers.phrasebook.backend.syntax;


import java.util.ArrayList;


/**
 * Created by Björn on 2016-02-26.
 */
public class SyntaxTree {

    private String id;
    private SyntaxNode root;
    private boolean advActivated;

    private ArrayList<SyntaxNodeList> options = new ArrayList<>();

    private SyntaxTree advancedTree; //Realy ugly solution but the only one we can think of as

    public SyntaxTree getAdvancedTree() {
        return advancedTree;
    }

    public void setAdvancedTree(SyntaxTree advancedTree) {
        this.advancedTree = advancedTree;
    }
                                    // some of the GF code isnt running currently

    public SyntaxTree(SyntaxNode root) {
        this.root = root;
        advActivated = false;
        initializeOptions(this.root);
    }

    public ArrayList<SyntaxNodeList> getOptions() {
        return options;
    }

    public ArrayList<SyntaxNodeList> getAdvOptions() {
        if(advancedTree != null) {
            return advancedTree.getOptions();
        }
        //empty list
        return new ArrayList<SyntaxNodeList>();
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

    public boolean setSelectedChild(int optionIndex, int childIndex, boolean isAdvanced) {
        boolean status = false;
        if(!isAdvanced) {
            if (options.get(optionIndex).getSelectedChild() instanceof NumeralSyntaxNode) {
                options.get(optionIndex).getSelectedChild().setSelectedChild(childIndex, null);
                status = true;
            } else if (options.get(optionIndex) != null) {
                if (options.get(optionIndex).getChildren().get(childIndex) != null)
                    status = options.get(optionIndex).setSelectedChild(options.get(optionIndex).getChildren().get(childIndex));
            }
        } else {
            advancedTree.setSelectedChild(optionIndex,childIndex,false);
        }

        return status;
    }


    public void setSelectedChild(int optionIndex, SyntaxNodeList snl, int childIndex, boolean isAdvanced) {
        if(!isAdvanced) {
            if (snl != null) {
                SyntaxNodeList nodeList = options.get(optionIndex);
                setRecursiveSelectedChild(nodeList, snl, childIndex);
            } else {
                setSelectedChild(optionIndex, childIndex,false);
            }
        } else {
            advancedTree.setSelectedChild(optionIndex,snl,childIndex,false);
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

    //shall parse with the advoptions, not used as of yet
    public String getAdvSyntax() {
        String syntax = getSyntax();
        if(advancedTree != null) {
            String advSyntax = advancedTree.getSyntax();
            if(!advSyntax.isEmpty()) {
                System.out.println(advSyntax.indexOf("AKnow"));
                String test = advSyntax.substring(0, advSyntax.indexOf("AKnow") + 5) +
                        syntax.substring(1, 9) + "(" +
                        advSyntax.substring(advSyntax.indexOf("AKnow") + 6, advSyntax.length() - 3) +
                        syntax.substring(9, syntax.length()) + ")))";
                return test;
            }
        }
        return syntax;
    }

    public boolean hasAdvOptions() {
        return (advancedTree != null);
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

    public boolean isAdvActivated(){
        return advActivated;
    }

    public void setAdvActivated(boolean activated){

        advActivated = activated;

    }

}