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
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public NodeList getSentenceList(Document document, String sentenceTitle) {
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

    private Node constructSentence(NodeList nl, SyntaxTree tree, Node parent) {
        if (nl == null || nl.getLength() < 1)
            return null;
        int length = nl.getLength();
        String syntax = "", desc = "", question="", option ;
        for (int i = 0; i < length; i++) {
            if (nl.item(i) != null && (nl.item(i).getNodeType() == Node.ELEMENT_NODE) && nl.item(i).getAttributes() != null) {
                NamedNodeMap attributes = nl.item(i).getAttributes();

                if (attributes.getNamedItem("syntax") != null) {
                    syntax = attributes.getNamedItem("syntax").getNodeValue();

                }

                if (attributes.getNamedItem("desc") != null) {
                    desc = attributes.getNamedItem("desc").getNodeValue();
                }


                if (attributes.getNamedItem("question") != null) {
                    question = attributes.getNamedItem("childdesc").getNodeValue();
                }


                if (attributes.getNamedItem("child") != null) {
                    option = attributes.getNamedItem("child").getNodeValue();

//                    NodeList list = jumpToChild(document, option);
//                    tree.addChild(parent, constructSentence(list, tree, parent));
//                }
//
//                if (!syntax.isEmpty()) {
//                    SyntaxTree.Node child = new SyntaxTree.Node(syntax);
//
//                    if (!question.isEmpty()) parent.setChildDescription(question);
//                    if (!desc.isEmpty()) child.setDataDescription(desc);
//
//                    tree.addChild(parent, child);//Adds the current node to the parent
//                    tree.addChild(child,constructSentence(nl.item(i).getChildNodes(), tree, child));//recursively adds grandchild to child, until nodelist is empty or null
                }

            }
        }
        return parent;
    }


    public NodeList jumpToChild(Document document, String child) {
        NodeList result = null;
        NodeList nl = document.getElementsByTagName(child);

        for (int i = 0; i < nl.getLength(); i++) {
            String s = nl.item(i).getFirstChild().getNodeValue();
            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
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
        NodeList nl = getSentenceList(document, "QWhatName");
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
