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

        for (int i = 0; i < length; i++) {
            if (!(sentence.item(i).getNodeType() == Node.ELEMENT_NODE)) {
                continue;
            }
            System.out.println(constructSentence(sentence, ""));
        }


        return null;
    }

    private String constructSentence(NodeList nl, String s) {
        if (nl == null || nl.getLength() < 1)
            return "";
        int length = nl.getLength();

        for (int i = 0; i < length; i++) {
            if (nl.item(i) != null && nl.item(i).getAttributes() != null) {

                NamedNodeMap attributes = nl.item(i).getAttributes();

                if (attributes.getNamedItem("syntax") != null) {
                    System.out.println(attributes.getNamedItem("syntax").getNodeValue());
                }
                if (attributes.getNamedItem("desc") != null) {
                    System.out.println(attributes.getNamedItem("desc").getNodeValue());
                }
                if (attributes.getNamedItem("child") != null) {
                    String node = attributes.getNamedItem("child").getNodeValue();
                    s = s + constructSentence(jumpToChild(document, node), s);
                }


                s = s + constructSentence(nl.item(i).getChildNodes(), s);
            }
        }
        return s;
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
