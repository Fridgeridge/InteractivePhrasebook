package se.chalmers.phrasebook.backend;

import android.util.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by David on 2016-02-19.
 */
public class XMLParser {


    private XmlPullParser parser;
    private PhraseBook phraseBook;
    private DocumentBuilder documentBuilder;
    private Document document;

    public XMLParser(InputStream is) {
        parser = Xml.newPullParser();
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


    public NodeList getSentenceList(String sentenceTitle) {
        NodeList result = null;
        NodeList nl = document.getElementsByTagName("sentence");

        for (int i = 0; i < nl.getLength(); i++) {
            String s = nl.item(i).getFirstChild().getNodeValue();
            s = trimNodeValue(s);
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE && sentenceTitle.equals(s)) {
                result = nl.item(i).getChildNodes();
            }
        }
        return result;
    }

    public String[] getAllSentencesTitles(Document document) {
        String[] result;
        NodeList sentences = document.getElementsByTagName("sentence");
        int nbrOfSentences = sentences.getLength();
        result = new String[nbrOfSentences];

        for (int i = 0; i < nbrOfSentences; i++) {
            result[i] = sentences.item(i).getAttributes().item(1).getNodeValue();
        }

        return result;
    }

    public Document acquireDocument() {
        return this.document;
    }

    private String trimNodeValue(String s) {
        s = s.replaceAll("[\\s]", "");//Trim the whitespace characters
        return s;
    }


    public SyntaxTree parseToSentence(Document document, NodeList sentence) {
        SyntaxTree syntaxTree;
        int length = sentence.getLength();

        return null;
    }

    //wrapper
    public SyntaxTree constructSentence2(NodeList currentRoot) {
        return new SyntaxTree(constructSentence(currentRoot, new SyntaxNode("root")));
    }

    private SyntaxNode constructSyntaxTreeSentence(Node currentRoot, SyntaxNode treeRoot) {
        if (!currentRoot.hasChildNodes()) {
            return null;
        }
        String syntax = "", desc = "", option = "";
        NodeList nodeList = currentRoot.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                NamedNodeMap attributes = nodeList.item(i).getAttributes();

                if (attributes.getNamedItem("syntax") != null) {
                    syntax = attributes.getNamedItem("syntax").getNodeValue();
                }

                if (attributes.getNamedItem("desc") != null) {
                    desc = attributes.getNamedItem("desc").getNodeValue();
                }

                if (attributes.getNamedItem("child") != null) {
                    option = attributes.getNamedItem("child").getNodeValue();
                    SyntaxNode child = new SyntaxNode(option);
                    child.setDesc(desc);
                    List<SyntaxNode> children = treeRoot.getChildren();

                    Node childNode = jumpToChild("Child", option).item(1);//FIXME Hardcoded shit
                    children.add(constructSyntaxTreeSentence(currentNode, child));
                    if (!treeRoot.hasChildren()) {
                        treeRoot.setSelectedChild(child);
                    }
                    treeRoot.setChildren(children);
                }
            }
        }
        SyntaxNode node = new SyntaxNode(syntax);
        if (!desc.isEmpty())
            node.setDescription(desc);
        treeRoot.addChild(node);
        node.addChild(constructSyntaxTreeSentence(currentRoot.getNextSibling(), node));
        return treeRoot;
    }


    private SyntaxNode constructSentence(NodeList nl, SyntaxNode parent) {
        if (nl == null || nl.getLength() < 1)
            return null;
        int length = nl.getLength();
        for (int i = 0; i < length; i++) {
            if (nl.item(i) != null && (nl.item(i).getNodeType() == Node.ELEMENT_NODE) && nl.item(i).getAttributes() != null) {
                String syntax = "", desc = "", question = "", option;
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

                if (attributes.getNamedItem("child") != null) {
                    option = attributes.getNamedItem("child").getNodeValue();
                    parent.addChild(constructSentence(jumpToChild("child", option), parent));
                } else if (syntax != null || !syntax.isEmpty()) {
                    System.out.println(syntax);
                    SyntaxNode node = new SyntaxNode(syntax);
                    parent.addChild(constructSentence(nl.item(i).getChildNodes(), node));
                }
            }
//            return parent;


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

    public String parseDOM(InputStream is) {
        String result = "";
        try {
            Document xmlDom = documentBuilder.parse(is);
            getAllSentencesTitles(xmlDom);
            result = getStringContent(xmlDom);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private String getStringContent(Document document) {
        NodeList nl = getSentenceList("QWhatName");
        String s = "";
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(nl.item(i).getNodeName() + " at level " + i + " and " + nl.item(i).getTextContent());
                //System.out.println(nl.item(i).getTextContent());
            }
            s += nl.item(i).getTextContent();
        }
        return s;
    }

}

