package se.chalmers.phrasebook.backend;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by David on 2016-02-19.
 */
public class XMLParser {

    private DocumentBuilder documentBuilder;
    private Document document;

    public XMLParser(InputStream is) {
        try {
            documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(is);
            is.close();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public NodeList getSentence(String sentenceTitle) {
        NodeList result = null;
        NodeList nl = document.getElementsByTagName("sentence");

        for (int i = 0; i < nl.getLength(); i++) {
            NamedNodeMap attr = nl.item(i).getAttributes();
            String s = attr.getNamedItem("id").getNodeValue();
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE && sentenceTitle.equals(s)) {
                result = nl.item(i).getChildNodes();
            }
        }
        return result;
    }

    public HashMap<String, String> getSentencesData() {
        String[] result;
        HashMap<String, String> sentenceMap = new HashMap<String, String>();

        NodeList sentences = document.getElementsByTagName("sentence");
        int nbrOfSentences = sentences.getLength();
        result = new String[nbrOfSentences];

        for (int i = 0; i < nbrOfSentences; i++) {
            String desc = sentences.item(i).getAttributes().getNamedItem("desc").getNodeValue();
            String id = sentences.item(i).getAttributes().getNamedItem("id").getNodeValue();

            if (desc != null && id != null)
                sentenceMap.put(id, desc);
        }
        return sentenceMap;
    }

    //Wrapper which constructs a syntax tree from a set of nodes extracted from XML.
    public SyntaxTree buildSyntaxTree2(Node root) {
        return new SyntaxTree(constructSentence2(root));
    }

    //recursivly takes the data from the node and transers it to a SyntaxNode, finally
    //adding it as a child to the current syntax root. When all children are added, the root
    //is returned to the wrapper which uses this SyntaxNode to create a SyntaxTree.
    private SyntaxNode constructSentence2(Node current) {

        //These may be subject to change if we alter the XML-file structure
        SyntaxNode currentRoot = new SyntaxNode(current.getNodeValue());
        currentRoot.setDescription(current.getNodeName());


        if (current.hasChildNodes()) {
            NodeList children = current.getChildNodes();

            for (int i = 0; i < children.getLength(); i++) {
                if (children.item(i) != null && (children.item(i).getNodeType()
                        == Node.ELEMENT_NODE) && children.item(i).getAttributes() != null) {
                    String syntax = "", desc = "", question = "", option;
                    NamedNodeMap attributes = children.item(i).getAttributes();

                    if (attributes.getNamedItem("syntax") != null) {
                        syntax = attributes.getNamedItem("syntax").getNodeValue();
                    }

                    if (attributes.getNamedItem("desc") != null) {
                        desc = attributes.getNamedItem("desc").getNodeValue();
                    }

                    if (attributes.getNamedItem("question") != null) {
                        question = attributes.getNamedItem("question").getNodeValue();
                    }

                    if (attributes.getNamedItem("child") != null) {
                        option = attributes.getNamedItem("child").getNodeValue();

                        //Här har parent lagts till
                        //      currentRoot.addChild(constructSentence2(children.item(i)), currentRoot);
                    }
                }
            }
        }
        return currentRoot;
    }

    public SyntaxTree buildSyntaxTree(NodeList currentRoot) {
        SyntaxTree l = new SyntaxTree(constructSyntaxNodeList(currentRoot, new SyntaxNode("Root"), new SyntaxNodeList(), null, 1));
        SyntaxTree s = new SyntaxTree(constructSentence(currentRoot, new SyntaxNode("root")));
        return s;
    }

    private SyntaxNode constructSentence(NodeList nl, SyntaxNode parent) {
        if (nl == null || nl.getLength() < 1) {
            return parent;
        }
        int length = nl.getLength();
        for (int i = 0; i < length; i++) {
            if (nl.item(i) != null && (nl.item(i).getNodeType() == Node.ELEMENT_NODE) && nl.item(i).getAttributes() != null) {
                String syntax = "", desc = "", question = "", option = "";
                NamedNodeMap attributes = nl.item(i).getAttributes();

                if (attributes.getNamedItem("syntax") != null) {
                    syntax = attributes.getNamedItem("syntax").getNodeValue();
                }

                if (attributes.getNamedItem("desc") != null) {
                    desc = attributes.getNamedItem("desc").getNodeValue();
                }


                if (attributes.getNamedItem("question") != null) {
                    question = attributes.getNamedItem("question").getNodeValue();
                }

                if (nl.item(i).getNodeName().equals("option") && attributes.getNamedItem("option") != null) {
                    parent.setNmbrOfSelectedChildren(parent.getNmbrOfSelectedChildren() + 1);
                    parent.addQuestion(question = attributes.getNamedItem("option").getNodeValue());


                    SyntaxNodeList list = new SyntaxNodeList();
                    list.setQuestion(question);


                    constructSentence(nl.item(i).getChildNodes(), parent);
                }

                if (attributes.getNamedItem("child") != null) {
                    option = attributes.getNamedItem("child").getNodeValue();

                    SyntaxNode nextSequence = new SyntaxNode("");
                    constructSentence(nl.item(i).getChildNodes(), nextSequence);
                    constructChildSequence(jumpToChild("child", option), parent, nextSequence);
                    System.out.println(nextSequence);
                }
                if (!syntax.isEmpty()) {
                    SyntaxNode node;
                    if (syntax.equals("NNumeral")) {
                        node = new NumeralSyntaxNode();
                    } else {
                        node = new SyntaxNode(syntax);
                        node.setDesc(desc);
                    }
                    parent.addChild(node);

                    constructSentence(nl.item(i).getChildNodes(), node);//Do not return
                }


            }
        }
        return parent;
    }


    private SyntaxNode constructSyntaxNodeList(NodeList nl, SyntaxNode parent, SyntaxNodeList list, SyntaxNode nextSequence, int nbrOfArgs) {
        if (nl == null || nl.getLength() < 1) {
            if (nextSequence != null) {
                list.add(nextSequence);
                parent.syntaxNodes.add(list);
            }
            return null;
        }
        int length = nl.getLength();

        //CurrentArgs counts the number of arguments for the current NodeList
        int currentArgs = 0;

        //If the parent node, or previous "recursion", calls for multiple args
        if (nbrOfArgs > 1) {
            currentArgs = nbrOfArgs;////Update currentArgs
            nbrOfArgs = 0;//Reset nbrOfArgs, important due to the other recursive calls which will happen before nbrOfArgs is actually used.
        }

        int args = 0;

        for (int i = 0; i < length; i++) {
            if (nl.item(i) != null && (nl.item(i).getNodeType() == Node.ELEMENT_NODE) && nl.item(i).getAttributes() != null) {
                String syntax = "", desc = "", option = "", question = "";

                NamedNodeMap attributes = nl.item(i).getAttributes();
                if (attributes.getNamedItem("syntax") != null) {
                    syntax = attributes.getNamedItem("syntax").getNodeValue();
                }

                if (attributes.getNamedItem("desc") != null) {
                    desc = attributes.getNamedItem("desc").getNodeValue();
                }

                if (attributes.getNamedItem("args") != null) {
                    args = Integer.parseInt(attributes.getNamedItem("args").getNodeValue());
                    nbrOfArgs = args;
                }

                if (attributes.getNamedItem("option") != null) {
                    question = attributes.getNamedItem("option").getNodeValue();
                    list.setQuestion(question);
                    constructSyntaxNodeList(nl.item(i).getChildNodes(), parent, list, nextSequence, nbrOfArgs);
                }

                if (attributes.getNamedItem("child") != null) {
                    option = attributes.getNamedItem("child").getNodeValue();

                    SyntaxNode mNextSequence = new SyntaxNode("");//This is a "holder" node to bind the child function calls nodes, contains no useful information in its syntax.
                    SyntaxNodeList mList = new SyntaxNodeList();
                    constructSyntaxNodeList(nl.item(i).getChildNodes(), mNextSequence, mList, nextSequence, nbrOfArgs);

                    constructSyntaxNodeList(jumpToChild("child", option), parent, list, mNextSequence, nbrOfArgs);
                }
                if (!syntax.isEmpty()) {
                    SyntaxNode node = new SyntaxNode(syntax);
                    node.setDesc(desc);

                    list.add(node);

                    SyntaxNodeList mList = new SyntaxNodeList();

                    constructSyntaxNodeList(nl.item(i).getChildNodes(), node, mList, nextSequence, nbrOfArgs);
                }

                //Add the list to the current parent node list
                if (!parent.syntaxNodes.contains(list)) {
                    parent.syntaxNodes.add(list);
                }
                //Check if current node is multiple arg nodes i.e. add another list to its syntaxNodes.
                if (currentArgs > 1 && parent.syntaxNodes.size() < currentArgs) {
                    list = new SyntaxNodeList();
                }

            }
        }
        return parent;

    }

    private SyntaxNode constructChildSequence(NodeList nl, SyntaxNode parent, SyntaxNode nextSequence) {
        if (nl == null || nl.getLength() < 1) {
            parent.addChild(nextSequence);
            return parent;
        }
        int length = nl.getLength();
        for (int i = 0; i < length; i++) {
            if (nl.item(i) != null && (nl.item(i).getNodeType() == Node.ELEMENT_NODE) && nl.item(i).getAttributes() != null) {
                String syntax = "", desc = "", question = "", option = "";
                NamedNodeMap attributes = nl.item(i).getAttributes();

                if (attributes.getNamedItem("syntax") != null) {
                    syntax = attributes.getNamedItem("syntax").getNodeValue();
                }

                if (attributes.getNamedItem("desc") != null) {
                    desc = attributes.getNamedItem("desc").getNodeValue();
                }


                if (attributes.getNamedItem("question") != null) {
                    question = attributes.getNamedItem("question").getNodeValue();
                }

                if (nl.item(i).getNodeName().equals("option") && attributes.getNamedItem("option") != null) {
                    parent.setNmbrOfSelectedChildren(parent.getNmbrOfSelectedChildren() + 1);
                    parent.addQuestion(option = attributes.getNamedItem("option").getNodeValue());
                    SyntaxNode node = constructChildSequence(nl.item(i).getChildNodes(), parent, nextSequence);
                }

                if (attributes.getNamedItem("child") != null) {
                    option = attributes.getNamedItem("child").getNodeValue();
                    SyntaxNode node = constructChildSequence(jumpToChild("child", option), parent, nextSequence);
                }
                if (!syntax.isEmpty()) {
                    SyntaxNode node = new SyntaxNode(syntax);
                    parent.addChild(node);
                    node.setDesc(desc);

                    constructChildSequence(nl.item(i).getChildNodes(), node, nextSequence);//Do not return
                }


            }
        }
        return parent;
    }

    public NodeList jumpToChild(String tag, String id) {
        NodeList result = null;
        NodeList nl = document.getElementsByTagName(tag);

        for (int i = 0; i < nl.getLength(); i++) {
            String s = nl.item(i).getFirstChild().getNodeValue();
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE && nl.item(i).getAttributes().getNamedItem("id").getNodeValue().equals(id)) {
                result = nl.item(i).getChildNodes();
            }
        }
        return result;
    }
}