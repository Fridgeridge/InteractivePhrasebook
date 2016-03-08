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


    public SyntaxTree buildSyntaxTree(NodeList currentRoot) {
        return new SyntaxTree(constructSentence(currentRoot, new SyntaxNode("root")));
    }

    private SyntaxNode constructSentence(NodeList nl, SyntaxNode parent) {
        if (nl == null || nl.getLength() < 1)
            return parent;
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
                    constructSentence(jumpToChild("child", option), parent);

                }
                if (!syntax.isEmpty()) {
                    SyntaxNode node = new SyntaxNode(syntax);
                    parent.addChild(node);
                    constructSentence(nl.item(i).getChildNodes(), node);
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

